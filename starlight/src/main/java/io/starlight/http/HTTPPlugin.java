package io.starlight.http;

import io.starlight.AutoWired;
import io.starlight.ComponentManager;
import io.starlight.ComponentScan;
import io.starlight.Logger;
import io.starlight.Plugin;
import io.starlight.PluginHandler;
import io.starlight.ResponseTimeout;
import io.starlight.StarlightVerticle;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.StaticHandler;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import org.reflections.Reflections;

/**
 *
 * @author denny
 */
@Plugin
public class HTTPPlugin implements PluginHandler {

    protected Logger logger = Logger.getLogger(HTTPPlugin.class);
    
    private StarlightVerticle verticle;
    
    @Override
    public void initVerticle(StarlightVerticle verticle) {
        this.verticle = verticle;
        EnableWebServer annot = verticle.getClass().getAnnotation(EnableWebServer.class);
        
        if (annot != null) {
            String portStr = annot.port();
            int port = 8888;
            
            if (portStr.startsWith("${") && portStr.endsWith("}")) {
                                
                try {
                    port = ComponentManager.getSetting(portStr.substring(2, portStr.length() - 1), Integer.class);
                }
                catch (Exception e){
                    port = 8888;
                }
            }
            else {
                
                try {
                    
                    port = Integer.parseInt(portStr);
                }
                catch (Exception e) {
                    port = 8888;
                }
            }
                
            List<String> scanList = new ArrayList<>();
            ComponentScan[] componentScanList =  verticle.getClass().getAnnotationsByType(ComponentScan.class);

            for (ComponentScan comp : componentScanList) {

                scanList.add(comp.value());
            }

            if (scanList.isEmpty()) {

                if (verticle.getClass().getPackage() != null)
                    scanList.add(verticle.getClass().getPackage().getName());
            }

            List<Object> controllerList = new ArrayList<>();
            
            for (String pkgName : scanList) {

                scanDir(controllerList, pkgName, this.verticle.getVertx());
            }
            
            HttpServerOptions opt = new HttpServerOptions();
            opt.setCompressionSupported(true);
            
            HttpServer server = Vertx.currentContext().owner().createHttpServer(opt);
            Router router = Router.router(Vertx.currentContext().owner());
            
            if (Vertx.currentContext().config().getString("uploadDirectory") != null) 
            	router.route().handler(BodyHandler.create().setUploadsDirectory(
            			Vertx.currentContext().config().getString("uploadDirectory")));
            else
            	router.route().handler(BodyHandler.create());
            router.route().handler(CorsHandler.create("*"));

            // prehandler
            Class<?>[] handlerList = annot.preHandler();
            
            for (Class<?> handlerClass : handlerList) {
                
                try {
                    
                    Constructor<?> ctor = handlerClass.getConstructor();

                    if (ctor != null) {

                        Object obj = ctor.newInstance();

                        if (obj instanceof Handler) {
                         
                            router.route().handler((Handler<RoutingContext>) obj);
                            ComponentManager.addComponent(obj, "", this.verticle.getVertx());
                        }                            
                    }
                }
                catch (Exception e) {
                    
                    logger.error("Unable to create pre handler http", e);
                }
            }
            
            for (Object obj : controllerList) {

                addController(router, obj);
            }

            router.route("/").handler(rc -> { rc.reroute("/index.html");});
            router.route("/*").handler(StaticHandler.create().setCachingEnabled(false));
            
            server
                    .requestHandler(router::accept)
                    .listen(port);
            
            List<Field> fieldList = ComponentManager.getAllFields(verticle.getClass());
        
            for (Field field : fieldList) {

                AutoWired anot = field.getAnnotation(AutoWired.class);

                if (anot != null) {
                    
                    if (field.getType().isAssignableFrom(HttpServer.class)) {
                    
                        field.setAccessible(true);
                        
                        try {
                            field.set(verticle, server);
                        }
                        catch (Exception e){
                            
                        }
                    }
                    else if (field.getType().isAssignableFrom(Router.class)) {
                    
                        field.setAccessible(true);
                        
                        try {
                            field.set(verticle, router);
                        }
                        catch (Exception e) {
                            
                        }
                    }
                }
            }
            logger.info("HTTP listening on port "+port);
                    
        }
    }
    
    protected void scanDir(List<Object> controllerList, String dir, Vertx vertx) {
    
        Reflections reflections = new Reflections(dir);
        
        // component
        Set<Class<?>> classSet = reflections.getTypesAnnotatedWith(RestController.class);
                
        for (Class cls : classSet) {
            
            try {

                Constructor<?> ctor = cls.getConstructor();

                if (ctor != null) {

                    Object obj = ctor.newInstance();
                    
                    if (obj != null) {
                        
                        controllerList.add(obj);
                        
                        ComponentManager.addComponent(obj, "", vertx);
                    }
                }
            }
            catch (Exception e) {

                logger.error("Error initialize controller : " + cls.getCanonicalName(), e);
            }
        }     
    }
    
    public void addController(Router router, Object obj) {
        
        if (obj != null) {
                            
            RestController objMapping = obj.getClass().getAnnotation(RestController.class);

            Method[] methodList = obj.getClass().getMethods();

            for (Method method : methodList) {
            	
                RequestMapping methodMapping = method.getAnnotation(RequestMapping.class);
                ResponseTimeout respTimeout = method.getAnnotation(ResponseTimeout.class);
                if (methodMapping != null) {
                    addRoute(router, obj, method, objMapping, methodMapping, respTimeout == null ? 0 : respTimeout.value());
                }
            }
        }
    }
    
    protected void addRoute(Router router, Object target, Method method, RestController objMapping, RequestMapping methodMapping, long timeout) {
        
        String path = methodMapping.value();
        
        if (objMapping != null) {
            
            String objPath = objMapping.value();
            
            if (objPath.endsWith("/"))
                objPath = objPath.substring(0, objPath.length() - 1);
            
            path = objPath + path; 
        }
        
        final String fullPath = path;
        
        long applicableTimeout = timeout <= 0 ? methodMapping.timeout() : timeout;
        
        logger.info("adding route : " + fullPath +" with response timeout "+applicableTimeout+"ms");
        
        router.route(methodMapping.method(), fullPath).handler(rc -> {
            
                logger.debug("masuk : " + fullPath);
            
                try {
                    
                    Object[] paramCall = new Object[method.getParameterCount()];

                    for (int i = 0; i< method.getParameterCount(); i++) {

                        Parameter param = method.getParameters()[i];

                        if (param.getType().isAssignableFrom(RoutingContext.class))
                            paramCall[i] = rc;
                        else {

                            RequestBody bodyAnnot = param.getAnnotation(RequestBody.class);

                            if (bodyAnnot != null) {

                                if (param.getType() == String.class)
                                    paramCall[i] = rc.getBodyAsString();
                                else {

                                    try {

                                        paramCall[i] = Json.decodeValue(rc.getBodyAsString(), param.getType());
                                    }
                                    catch (Exception e) {
                                        
                                        throw new Exception("Unable to decode request body : " + e.getMessage());
                                    }
                                }        
                            }
                            else {

                                PathParam pathAnnot = param.getAnnotation(PathParam.class);

                                if (pathAnnot != null) {

                                    if ((param.getType() == int.class) || (param.getType() == Integer.class))
                                        paramCall[i] = Integer.parseInt(rc.pathParam(pathAnnot.value()));
                                    else
                                        paramCall[i] = rc.pathParam(pathAnnot.value());
                                }
                                else {

                                    QueryParam queryAnnot = param.getAnnotation(QueryParam.class);

                                    if (queryAnnot != null) {

                                        if ((param.getType() == int.class) || (param.getType() == Integer.class))
                                            paramCall[i] = rc.queryParam(queryAnnot.value()).size() > 0 ?  Integer.parseInt(rc.queryParam(queryAnnot.value()).get(0)) : 0;
                                        else
                                            paramCall[i] = rc.queryParam(queryAnnot.value()).size() > 0 ? rc.queryParam(queryAnnot.value()).get(0) : null;
                                    }
                                }
                            }
                        }                    
                    }
                            
                    Object ret = method.invoke(target, paramCall);

                    if (ret instanceof Future) {                            
                        ((Future<?>) ret).setHandler(futureRet -> {

                                if (futureRet.succeeded()) 
                                    writeResponse(rc, methodMapping, futureRet.result());
                                else
                                    writeErrorResponse(rc, "" + futureRet.cause());
                            });
                    	this.verticle.getVertx().setTimer(applicableTimeout, t -> {
                    		TimeoutException te = new TimeoutException("Timeout after "+applicableTimeout+"ms");
                    		if (((Future<?>) ret).tryFail(te)) {
                				logger.warn("Timeout is forced for "+methodMapping.value());
                    		}
                    	});
                    }
                    else 
                        writeResponse(rc, methodMapping, ret);
                }
                catch (Exception e) {

//                    System.out.println("Error routing to " + fullPath);
//                    e.printStackTrace(System.out);
                	logger.error("Error routing to "+fullPath, e);
                    
                    writeErrorResponse(rc, e.getMessage());
                }
            });
    }
    
    protected void writeResponse(RoutingContext rc, RequestMapping methodMapping, Object obj) {

        if (!rc.response().headers().contains("Content-Type"))
            rc.response().headers().add("Content-Type", methodMapping.produce().getMimeType());
        
        if (!rc.response().headers().contains("Cache-Control"))
        	rc.response().headers().add("Cache-Control", "no-cache");
        
        if (obj != null) {
            
            if (obj instanceof File) {

                rc.response()
                        .setChunked(true)
                        .sendFile(((File) obj).getAbsolutePath());      
            }
            else {

                switch (methodMapping.produce()) {

                    case HTML :
                    case TEXT :
                                rc.response().end(obj.toString());
                                break;
                    case JSON :                                
                                rc.response().end(Json.encode(obj));
                                break;
                    case RAW :
                                if (obj instanceof Buffer)
                                    rc.response().end((Buffer) obj);
                                break;
                }
            }
        }
        else
            rc.response().end();
    }
    
    protected void writeErrorResponse(RoutingContext rc, String errorMsg) {

        try {
	        rc.response().setStatusCode(500);
	        rc.response().headers().add("Content-Type", "text/plain");
	        rc.response().end("error : " + errorMsg);
	    } catch (Exception e) {
            logger.error("Write response error : ", e);
        }
    }
}
