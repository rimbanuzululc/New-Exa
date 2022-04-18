package io.starlight;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 *
 * @author denny
 */
public class ClientInvoker implements InvocationHandler {
	
	private final Logger logger = LoggerFactory.getLogger(ClientInvoker.class);

    protected Class<?> targetClass;
    protected String targetName;
    protected String name;
    protected long timeout;
    
    public ClientInvoker(Class<?> targetClass, String name, long timeout) {
        this.targetClass = targetClass;
        targetName = targetClass.getCanonicalName();
        this.name = name;
        this.timeout = timeout <= 0 ? DeliveryOptions.DEFAULT_TIMEOUT : timeout;
        
        if (logger.isDebugEnabled()) {
        	logger.debug("ClientInvoker created for "+targetName+". Name="+name+", timeout="+this.timeout);
        }
    }
    
    protected Object mapJson(Object obj) {
        
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
        else if (obj instanceof Date) {
            
            result = Json.encode(obj);
        }
        else if (obj != null)
            result = JsonObject.mapFrom(obj);
        
        return result;
    }
            
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        
        @SuppressWarnings("rawtypes")
		Future future = Future.future();
        
        JsonArray jsArg = new JsonArray();

        if (args != null) {
         
            for (int i = 0; i < args.length; i++) {
            
                if (args[i] == null)
                    jsArg.addNull();
                else
                    jsArg.add(mapJson(args[i]));
            }
        }
        
        if (method.getReturnType().equals(Void.TYPE)) {
        	
        	if (logger.isDebugEnabled()) {
        		logger.debug("-->Send only -> "+targetName + "|" + name +  "." + method.getName());
        	}
        	/*
        	 * SEND and forget
        	 */
            Vertx
                .currentContext()
                .owner()
                .eventBus()
                .send(targetName + "|" + name +  "." + method.getName(), jsArg);
            
            future.complete();
        } else {
        	if (logger.isDebugEnabled()) {
        		logger.debug("-->Send and recive -> "+targetName + "|" + name +  "." + method.getName());
        	}
        	/*
        	 * SEND and expect return with 30000ms default timeout
        	 */
	        Vertx
	            .currentContext()
	            .owner()
	            .eventBus()
	            .send(targetName + "|" + name +  "." + method.getName(), 
	                    jsArg,
	                    new DeliveryOptions().setSendTimeout(timeout <= 0 ? DeliveryOptions.DEFAULT_TIMEOUT : timeout),
	                    result -> {
	
	                        try {
	                            
	                            if (result.succeeded()) {
	
	                                JsonObject jsObj = (JsonObject) result.result().body();
	                                Object retObj = jsObj.getValue("result");
	
	                                if (retObj != null) {
	
	                                    if (retObj instanceof JsonObject) {
	
	                                        Class<?> cls = null;
	
	                                        if (method.getGenericReturnType() instanceof ParameterizedType)
	                                            cls = (Class<?>) ((ParameterizedType) method.getGenericReturnType()).getActualTypeArguments()[0];                                    
	
	                                        if (cls != null) {
	
	                                            if (cls != JsonObject.class)
	                                                retObj = ((JsonObject) retObj).mapTo(cls);                                        
	                                        }
	                                        else
	                                            retObj = null;
	                                    }
	                                    else if (retObj instanceof JsonArray) {
	
	                                        Class<?> cls = null;
	
	                                        if (method.getGenericReturnType() instanceof ParameterizedType) {
	
	                                            Type retType = ((ParameterizedType) method.getGenericReturnType()).getActualTypeArguments()[0];
	
	                                            if (retType instanceof ParameterizedType)                                        
	                                                cls = (Class<?>) ((ParameterizedType) retType).getActualTypeArguments()[0];
	                                        }
	
	                                        if (cls != null) {
	
	                                            List retList = new ArrayList();
	
	                                            for (int i = 0; i < ((JsonArray) retObj).size(); i++) {
	
	                                                if ((cls == Integer.class) || (cls == int.class))
	                                                    retList.add(((JsonArray)retObj).getInteger(i));
	                                                else if ((cls == Boolean.class) || (cls == boolean.class))
	                                                    retList.add(((JsonArray)retObj).getBoolean(i));
	                                                else if ((cls == Double.class) || (cls == double.class))
	                                                    retList.add(((JsonArray)retObj).getDouble(i));
	                                                else if ((cls == Float.class) || (cls == float.class))
	                                                    retList.add(((JsonArray)retObj).getFloat(i));
	                                                else if ((cls == Long.class) || (cls == long.class))
	                                                    retList.add(((JsonArray)retObj).getLong(i));
	                                                else if (cls == String.class)
	                                                    retList.add(((JsonArray)retObj).getString(i));
	                                                else
	                                                    retList.add(((JsonArray)retObj).getJsonObject(i).mapTo(cls));
	                                            }
	
	                                            retObj = retList;
	                                        }
	                                        else
	                                            retObj = null;
	                                    }                                
	                                }
	
	                                future.complete(retObj);
	                            }                                
	                            else
	
	                                future.fail(result.cause());
	                        }
	                        catch (Exception e) {
	                            logger.error(e.getMessage(), e);
	                            future.fail(e);
	                        }
	                    });
        }
        return future;
    }    
}
