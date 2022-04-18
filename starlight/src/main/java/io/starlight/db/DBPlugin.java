package io.starlight.db;

import io.starlight.ComponentManager;
import io.starlight.ComponentScan;
import io.starlight.Plugin;
import io.starlight.PluginHandler;
import io.starlight.StarlightVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.reflections.Reflections;

/**
 *
 * @author denny
 */
@Plugin
public class DBPlugin implements PluginHandler {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private Vertx vertx;

    @Override
    public void initVerticle(StarlightVerticle verticle) {
    	this.vertx = verticle.getVertx();
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

            scanDir(pkgName);
        }
    }
    
    protected void scanDir(String dir) {
    
        Reflections reflections = new Reflections(dir);
        
        // dao
        Set<Class<?>> classSet = reflections.getTypesAnnotatedWith(DAO.class);
                
        for (Class cls : classSet) {
            
            DAO anot = (DAO) cls.getAnnotation(DAO.class);

            try {

                Constructor<?> ctor = cls.getConstructor();

                if (ctor != null) {

                    Object obj = ctor.newInstance();

                    if (obj instanceof CommonDAO) {
                     
                        JsonObject config = ComponentManager.getSetting(anot.config(), JsonObject.class);

                        if (config == null) {

                            config = new JsonObject();
                            config
                                .put("url", anot.url())
                                .put("driver_class", anot.driver())
                                .put("user", anot.user())
                                .put("password", anot.pass());
                        }
                        logger.info("Use database config : "+config.getString("url"));
                        ((CommonDAO) obj).init(config);

                        ComponentManager.addComponent(obj, anot.name(), vertx);
                    }                    
                }
            }
            catch (Exception e) {

                System.out.println("Error initialize dao : " + cls.getCanonicalName());
                e.printStackTrace(System.out);
            }
        }
    }
}
