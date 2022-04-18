package io.starlight;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeoutException;

import org.reflections.Reflections;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author denny
 */
public class ComponentManager {
	
	private static final Logger logger = LoggerFactory.getLogger(ComponentManager.class);

    // -----------
    
    static class ComponentRegistry {
        
        protected Object obj;
        protected String name;
        protected boolean service;
        protected boolean inited;
        protected Vertx vertx;

        public ComponentRegistry(Object obj, String name, boolean service, boolean inited, Vertx vertx) {
            
            this.obj = obj;
            this.name = name;
            this.service = service;
            this.inited = inited;
            this.vertx = vertx;
        }

        public Object getObj() {
            return obj;
        }

        public void setObj(Object obj) {
            this.obj = obj;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isService() {
            return service;
        }

        public void setService(boolean service) {
            this.service = service;
        }

        public boolean isInited() {
            return inited;
        }

        public void setInited(boolean inited) {
            this.inited = inited;
        }

		public Vertx getVertx() {
			return vertx;
		}

		public void setVertx(Vertx vertx) {
			this.vertx = vertx;
		}
        
        
    }
        
    protected static boolean inited = false;
    protected static ConcurrentLinkedQueue<ComponentRegistry> componentList = new ConcurrentLinkedQueue();
    protected static ConcurrentLinkedQueue<PluginHandler> pluginList = new ConcurrentLinkedQueue();
    protected static ConcurrentHashMap<String, Object> proxyList = new ConcurrentHashMap();
    
    public static void addComponent(Object obj, String name, Vertx vertx) {
        
        componentList.add(new ComponentRegistry(obj, name, false, false, vertx));
    }
    
    public static void initVerticle(StarlightVerticle verticle, Vertx vertx) {
    
        List<String> scanList = new ArrayList<>();
        ComponentScan[] componentScanList =  verticle.getClass().getAnnotationsByType(ComponentScan.class);
        
        for (ComponentScan comp : componentScanList) {

            scanList.add(comp.value());
        }

        if (scanList.isEmpty()) {

            if (verticle.getClass().getPackage() != null)
                scanList.add(verticle.getClass().getPackage().getName());
        }
        
        for (String pkgName : scanList) {

            scanDir(pkgName, vertx);
        }

        applyPlugin(verticle);
        
        List<Object> initList = new ArrayList<>();
        
        // wire @Config & @AutoWired
        for (ComponentRegistry reg : componentList) {

            if (!reg.isInited()) {
             
                applyConfigAndWire(reg.getObj(), vertx);
                reg.setInited(true);
                
                initList.add(reg.getObj());
            }
        }

        applyConfigAndWire(verticle, vertx);        
                
        // call init
        for (Object obj : initList) {

            callInit(obj);
        }
        
        callInit(verticle);
    } 
    
    protected static void applyPlugin(StarlightVerticle verticle) {
        
        // starlightpplugin
        
        Reflections reflections = new Reflections("io.starlight");
                
        Set<Class<?>> classSet = reflections.getTypesAnnotatedWith(Plugin.class);
                
        for (Class cls : classSet) {
                    
            boolean create = true;

            for (PluginHandler handler : pluginList) {

                if (handler.getClass().equals(cls)) {

                    create = false;
                    break;
                }
            }

            if (create) {

                try {

                    Constructor<?> ctor = cls.getConstructor();

                    if (ctor != null) {

                        Object obj = ctor.newInstance();

                        if (obj instanceof PluginHandler) 
                            pluginList.add((PluginHandler) obj);
                    }
                }
                catch (Exception e) {

                }
            }
        }
                
        // scan for other plugin
        Annotation[] annotList = verticle.getClass().getAnnotations();
        
        for (Annotation annot : annotList) {

            String dirName = annot.annotationType().getPackage().getName();
            
            if (!"io.startlight".equals(dirName)) {

                reflections = new Reflections(dirName);
                classSet = reflections.getTypesAnnotatedWith(Plugin.class);
        
                for (Class cls : classSet) {
                    
                    boolean create = true;
                    
                    for (PluginHandler handler : pluginList) {
                        
                        if (handler.getClass().equals(cls)) {
                            
                            create = false;
                            break;
                        }
                    }
                    
                    if (create) {
                        
                        try {
                            
                            Constructor<?> ctor = cls.getConstructor();

                            if (ctor != null) {

                                Object obj = ctor.newInstance();
                                
                                if (obj instanceof PluginHandler) 
                                    pluginList.add((PluginHandler) obj);
                            }
                        }
                        catch (Exception e) {
                            
                        }
                    }
                }
            }
        }
        
        // apply plugin list
        for (PluginHandler handler : pluginList) {
            
            handler.initVerticle(verticle);
        }
    }
    
    protected static boolean isCreated(Class<?> componentClass) {
        
        boolean result = false;
        
        for (ComponentRegistry reg : componentList) {
            
            if (reg.getObj().getClass().equals(componentClass)) {
                
                result = true;
                break;
            }
        }
        
        return result;
    }
    
    protected static void scanDir(String dir, Vertx vertx) {
    
        Reflections reflections = new Reflections(dir);
        
        // component
        Set<Class<?>> classSet = reflections.getTypesAnnotatedWith(Component.class);
                
        for (Class cls : classSet) {
            
            if (!isCreated(cls)) {
             
                try {
                
                    Constructor<?> ctor = cls.getConstructor();

                    if (ctor != null) {

                        Object obj = ctor.newInstance();

                        String name = null;
                        Component compAnot = (Component) cls.getAnnotation(Component.class);

                        if (compAnot != null)
                            name = compAnot.value();

                        componentList.add(new ComponentRegistry(obj, name, false, false, vertx));
                    }

                }
                catch (Exception e) {

                    logger.debug("Error initialize component : " + cls.getCanonicalName());
                    e.printStackTrace(System.out);
                }
            }            
        }
        
        // service
        classSet = reflections.getTypesAnnotatedWith(Service.class);
        
        for (Class cls : classSet) {
            
            if (!isCreated(cls)) {                                
                
                try {

                    Constructor<?> ctor = cls.getConstructor();

                    if (ctor != null) {

                        Object obj = ctor.newInstance();

                        String name = null;
                        Service svcAnot = (Service) cls.getAnnotation(Service.class);
                        
                        if (svcAnot != null)
                            name = svcAnot.value();

                        componentList.add(new ComponentRegistry(obj, name, true, false, vertx));

                        // register remote proxy
                        Class<?>[] ifaceList = obj.getClass().getInterfaces();
                        
                        for (Class<?> iface : ifaceList) {

                            if (isServiceInterface(iface)) {

                                logger.info("Service method response timeout is set to "+svcAnot.timeout()+"ms");
                                register(iface, name, obj, svcAnot.timeout(), vertx);
                            }
                        }
                    }

                }
                catch (Exception e) {

                    logger.error("Error initialize service : " + cls.getCanonicalName(), e);
                }
            }
        }
    }
    
    public static Object getComponent(Class<?> type, long timeout) {
        
        return getComponent(type, "", timeout);
    }
    
    public static Object getComponent(Class<?> type, String name, long timeout) {

        Object result = null;
        boolean hasCandidate = false;
        boolean found = false;
        
        for (ComponentRegistry reg : componentList) {

            if (!reg.isService() && type.isInstance(reg.getObj())) {

                hasCandidate = true;

                if (name.equals(reg.getName())) {

                    result = reg.getObj();
                    found = true;
                    break;
                }                        
            }
        }

        if (!found && hasCandidate) {

            for (ComponentRegistry reg : componentList) {

                if (!reg.isService() && type.isInstance(reg.getObj())) {

                    result = reg.getObj();
                }
            }
        }
        
        if (!found) {
            // mungkin maksudnya service

            if (isServiceInterface(type)) {
                result = getServiceClient(type, name, timeout);
            }
        }
        
        return result;
    }
    
    protected static void applyConfigAndWire(Object target, Vertx vertx) {

        List<Field> fieldList = getAllFields(target.getClass());
        
        for (Field field : fieldList) {
            Config configAnot = field.getAnnotation(Config.class);
            
            if (configAnot != null) {
                                
                try {
                    
                    Object val = getSetting(configAnot.value(), field.getType());
                    
                    if (val != null) {
                        
                        field.setAccessible(true);
                        field.set(target, val);
                    }                        
                }
                catch (Exception e) {
                 
                    System.err.println("Error set field from config " + field.getName() + " : " + configAnot.value());
                    e.printStackTrace(System.out);
                }
            }
            else {

                AutoWired wireAnot = field.getAnnotation(AutoWired.class);
            
                if (wireAnot != null) {
                    if (logger.isDebugEnabled()) {
                    	logger.debug("Process AutoWired of field: "+field.getName());
                    }

                    Object comp = getComponent(field.getType(), wireAnot.value(), wireAnot.timeout());
                    
                    if (comp != null) {
                        
                        try {
                            field.setAccessible(true);
                            field.set(target, comp);
                        }
                        catch (Exception e) {

                            System.err.println("Error set autowired " + field.getName());
                            e.printStackTrace(System.out);
                        }
                    } else {
                    	logger.error("Component for "+field.getName()+" is not found!");
                    }
                }
            }
        }
    } 
        
    protected static void callInit(Object target) {
        
        List<Method> listMethod = getAllMethods(target.getClass());
        
        for (Method method : listMethod) {
            
            Init initAnot = method.getAnnotation(Init.class);
            
            if ((initAnot != null) && (method.getParameterCount() == 0)) {
                
                try {
                    
                    method.invoke(target);
                }
                catch (Exception e) {
                    
                    System.err.println("Error call init " + method.getName());
                    e.printStackTrace(System.out);
                }
            }
        }
    }
    
    public static <T> T getSetting(String name, Class<T> expectedClass) {
    
        return getSetting(name, 
                            Vertx.currentContext().config(),
                            expectedClass);
    }
    
    protected static <T> T getSetting(String name, JsonObject setting, Class<T> expectedClass) {
        
        Object result = null;
        
        int pos = name.indexOf(".");
        String currName = name;
        String nextName = "";
                
        if (pos > 0) {
            
            currName = name.substring(0, pos);
            nextName = name.substring(pos + 1);
        }
        
        try {
            
            if ("".equals(nextName)) {
                
                if ((expectedClass == Integer.class) || (expectedClass == int.class))
                    result = setting.getInteger(currName);
                else if (expectedClass == String.class)
                    result = setting.getString(currName);
                else if ((expectedClass == Boolean.class) || (expectedClass == boolean.class))
                    result = setting.getBoolean(currName);
                else if (expectedClass == JsonObject.class)
                    result = setting.getJsonObject(currName);
                else if (expectedClass == JsonArray.class)
                    result = setting.getJsonArray(currName);
            }
            else {
                
                JsonObject nextSetting = setting.getJsonObject(currName);
                
                if (nextSetting != null)
                    result = getSetting(nextName, 
                                        nextSetting, 
                                        expectedClass);
            }
        }
        catch (Exception e) {
            
            result = null;
        }
        
        return (T) result;
    }
    
    public static List<Field> getAllFields(Class<?> type) {
        
        List<Field> result = new ArrayList<>();
        
        result.addAll(Arrays.asList(type.getDeclaredFields()));

        if (type.getSuperclass() != null) {
            
            List<Field> parentFields = getAllFields(type.getSuperclass());
            result.addAll(parentFields);
        }
        
        return result;
    }
    
    protected static List<Method> getAllMethods(Class<?> type) {
        
        List<Method> result = new ArrayList<>();
        
        result.addAll(Arrays.asList(type.getDeclaredMethods()));

        if (type.getSuperclass() != null) {
            
            List<Method> parentMethods = getAllMethods(type.getSuperclass());
            result.addAll(parentMethods);
        }
        
        return result;
    }
        
    protected static boolean isServiceInterface(Class<?> type) {
        
        boolean result = true;
        
        List<Method> methodList = getAllMethods(type);
        
        for (Method method : methodList) {
            
            if (!method.getReturnType().isAssignableFrom(Future.class)
            		&& !Void.TYPE.equals(method.getReturnType())) {
                result = false;
                logger.warn(type.getName()+" is NOT a service interface");
                break;
            }
        }
       
        return result;
    }
    
    protected static void register(Class<?> type, String name, Object target, long timeout, Vertx vertx) {

        if (name == null)
            name = "";

        String typeName = type.getCanonicalName();
        Method[] methods = type.getMethods();
     
        for (Method method : methods) {
        	ResponseTimeout rt = method.getAnnotation(ResponseTimeout.class);
        	if (rt != null) {
        		if (logger.isDebugEnabled()) {
        			logger.debug(typeName+"."+method.getName()+" will use @ResponseTimeout specified timeout of "+rt.value()+"ms");
        		}
        	}
            registerMethod(typeName, name, target, method, rt == null ? timeout : rt.value(), vertx);
        }        
    }
    
    protected static Object buildObject(Object jsObj, Class<?> simpleClass, Class<?> genericClass) {
        
        Object result = jsObj;
        
        if (jsObj instanceof JsonObject) {

            result = ((JsonObject) jsObj).mapTo(simpleClass);
        }
        else if (jsObj instanceof JsonArray) {
            
            result = null;
            
            try {
                
                if (genericClass != null) {
                 
                    result = new ArrayList<>();

                    for (int i = 0; i < ((JsonArray) jsObj).size(); i++) {

                        if ((genericClass == Integer.class) || (genericClass == int.class))
                            ((List) result).add(((JsonArray)jsObj).getInteger(i));
                        else if ((genericClass == Boolean.class) || (genericClass == boolean.class))
                            ((List) result).add(((JsonArray)jsObj).getBoolean(i));
                        else if ((genericClass == Double.class) || (genericClass == double.class))
                            ((List) result).add(((JsonArray)jsObj).getDouble(i));
                        else if ((genericClass == Float.class) || (genericClass == float.class))
                            ((List) result).add(((JsonArray)jsObj).getFloat(i));
                        else if ((genericClass == Long.class) || (genericClass == long.class))
                            ((List) result).add(((JsonArray)jsObj).getLong(i));
                        else if (genericClass == String.class)
                            ((List) result).add(((JsonArray)jsObj).getString(i));
                        else
                            ((List) result).add(((JsonArray)jsObj).getJsonObject(i).mapTo(genericClass));
                    }
                }
            }
            catch (Exception e) {
                
                logger.error("error : ", e);
            }
        }
        
        return result;
    }
    
    protected static Object mapJson(Object obj) {
        
        Object result = null;
        
        if (
                (obj instanceof Integer)
                || (obj instanceof Boolean)
                || (obj instanceof String)
                || (obj instanceof Double)
                || (obj instanceof Float)
                || (obj instanceof Enum)
                || (obj instanceof Long)
                || (obj instanceof JsonObject)
            )
            result = obj;
        else if (obj instanceof List) {

            result = new JsonArray();
                    
            for (Object subObj : ((List) obj)) {
                
                ((JsonArray) result).add(mapJson(subObj));
            }
        }
        else if (obj != null)
            result = JsonObject.mapFrom(obj);
        
        return result;
    }
    
    protected static void registerMethod(String typeName, String name, Object target, Method method, long timeout, Vertx vertx) {
        
        String channelName = typeName + "|" + name + "." + method.getName();
        logger.info("register : " + channelName +" , timeout = "+timeout);

        Vertx
                .currentContext()
                .owner()
                .eventBus()
                .consumer(channelName, message -> {

                	if (logger.isDebugEnabled()) {
                		logger.debug("calling channel: " + channelName);
                	}

                    JsonArray args = (JsonArray) message.body();

                    try {
                        ArrayList<Object> jsList = new ArrayList();
                        Object[] argsParam = new Object[method.getParameterCount()];

                        for (int i = 0; i < args.size(); i ++) {
                            
                            Class cls = null;
                            
                            if (method.getGenericParameterTypes()[i] instanceof ParameterizedType) 
                                cls = (Class) ((ParameterizedType) method.getGenericParameterTypes()[i]).getActualTypeArguments()[0];
                            
                            argsParam[i] = buildObject(args.getValue(i), 
                                                        method.getParameterTypes()[i], 
                                                        cls);
                        }

                        if (logger.isDebugEnabled()) {
	                        try {
                            	logger.debug("param : " + Json.encode(target));
	                        }
	                        catch (Exception x1) {
                            	logger.debug("param : error " + x1);
	                        }
                        }
                        
                        Object ret = method.invoke(target, argsParam);
                        if (ret instanceof Future<?>) {
	                        Future<?> f = ((Future<Object>) ret)
	                                .setHandler(result -> {
                                            
                                            if (logger.isDebugEnabled()) {
                                                logger.debug("reply channel: " + channelName);
                                            }
	                                    
	                                    if (result.succeeded()) {
	
	                                        JsonObject jsObj = new JsonObject();
	
	                                        if (result.result() != null) {
	                                                
	                                            jsObj.put("result", mapJson(result.result()));                                            
	                                        }
	                                        else
	                                            jsObj.putNull("result");
	                                        
	                                        message.reply(jsObj);
	                                    }                                        
	                                    else {
	//                                        result.cause().printStackTrace(System.out);
	                                        message.fail(-1, "" + result.cause());
	                                        logger.error("Service results an exception", result.cause() );
	                                    }
	                                }); 
	                        
		                    	vertx.setTimer(timeout, t -> {
		                    				TimeoutException te = new TimeoutException("Timeout after "+timeout+" milliseconds");
		                    				if (f.tryFail(te)) {
                                                                    message.fail(-1, "" + te.getMessage());
                                                                    logger.warn("Timeout is forced for "+channelName);
		                    				}
		                    			});
	                    
		                    } else if (logger.isDebugEnabled()) {
		                    	logger.debug("Assume a void method. No message" );
		                    }
	                    } catch (Exception e) {
	
	                        message.fail(-1, String.valueOf(e));
	                        logger.error("Service unexpected exception", e);
	                    }            
                });
    }
    
    public static <T> T getServiceClient(Class<T> type) {
    
        return getServiceClient(type, "", 0);
    }
    
    public static <T> T getServiceClient(Class<T> type, String name) {
    	return getServiceClient(type, name, 0);
    }
    
    public static <T> T getServiceClient(Class<T> type, String name, long timeout) {
        if (name == null)
            name = "";
        
        T result = (T) proxyList.get(type.getCanonicalName() + "|" + name);

        if (result == null) {

            ArrayList<Class> interfaceList = new ArrayList<>(Arrays.asList(type.getInterfaces()));

            if (type.isInterface())
                interfaceList.add(type);

            result = (T) Proxy.newProxyInstance(type.getClassLoader(), 
                                interfaceList.toArray(new Class[interfaceList.size()]), 
                                new ClientInvoker(type, name, timeout));
         
            proxyList.put(type.getCanonicalName() + "|" + name, result);                
        }

        if (logger.isDebugEnabled()) {
        	logger.debug("--> getServiceClient of type = "+String.valueOf(type)+", name="+name);
        }
        return result;
    }
}
