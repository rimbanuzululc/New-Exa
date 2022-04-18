package io.starlight;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.UUID;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import com.hazelcast.config.Config;
import com.hazelcast.config.XmlConfigBuilder;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;

/**
 *
 * @author denny
 */
public class Launcher {

    public static void main(String[] args) {
    
        String mainVerticle = getMainVerticle();
        
        if (mainVerticle == null) {
            if (args.length > 0) {
            	if (args.length > 1 && "run".equals(args[0])) {
            		mainVerticle = args[1];
            	} else {
            		mainVerticle = args[0];
            	}
            }
        }
        
        if (mainVerticle != null)
            new Launcher().start(mainVerticle);
        else {
            
            System.out.println("Main verticle not found");
        }
    }
    
    protected static String getMainVerticle() {
        
        String result = null;
        
        try {        
        
            Enumeration<URL> resources = Launcher.class.getClassLoader().getResources("META-INF/MANIFEST.MF");
        
            while (resources.hasMoreElements()) {
            
                InputStream stream = resources.nextElement().openStream();
                Manifest manifest = new Manifest(stream);
                Attributes attributes = manifest.getMainAttributes();
        
                result = attributes.getValue("Main-Verticle");
            
                stream.close();
            }
        } 
        catch (IOException e) {
            
            throw new IllegalStateException(e.getMessage());
        }
        
        return result;
    }
    
    private final Logger logger = LoggerFactory.getLogger(Launcher.class);
    
    protected Config hazelcastConfig(String name) {
		String instanceName = System.getProperty("hazelcast.instance.name", UUID.randomUUID().toString()); 
    	try {
			logger.info("Try getting hazelcast config "+name+" from current runtime directory");
			return new XmlConfigBuilder("./"+name).build().setInstanceName(
					instanceName);
		} catch (IOException e) {
			logger.info("Try getting hazelcast config "+name+" from classpath");
			try {
				return new XmlConfigBuilder(this.getClass().getResourceAsStream("/"+name))
						.build().setInstanceName(instanceName);
			} catch (Exception e1) {
				throw new RuntimeException("Failed loading hazelcast config", e1);
			}
		}
    }
    
    
    protected void start(String mainVerticle) {
        
        VertxOptions options = new VertxOptions();
        Config hzConfig = hazelcastConfig("cluster.xml");
        HazelcastInstance hzInstance = Hazelcast.getOrCreateHazelcastInstance(hzConfig);
        HazelcastContextHolder.setCurrentInstance(hzInstance);
        SocketAddress socketAddr = hzInstance.getLocalEndpoint().getSocketAddress();
		ClusterManager mgr = new HazelcastClusterManager(hzInstance);
		options.setClusterManager(mgr);
		String clusterHost = System.getProperty("cluster.host");
		if (clusterHost == null && socketAddr instanceof InetSocketAddress) {
			clusterHost = ((InetSocketAddress)socketAddr).getAddress().getHostAddress();
		}
		logger.info("Using Vert.x cluster host = "+clusterHost);
		options.setClusterHost(clusterHost);
		
		/*MicrometerMetricsOptions moptions = new MicrometerMetricsOptions()
				.setPrometheusOptions(new VertxPrometheusOptions()
				.setStartEmbeddedServer(true)
				.setEmbeddedServerOptions(new HttpServerOptions().setPort(8081))
				.setEnabled(true))
				.setEnabled(true);
		options.setMetricsOptions(moptions);*/
        
        Vertx.clusteredVertx(options, 
                vertxRet -> {
                	// initial config from cluster
                	Map<Object, Object> mconfig = hzInstance.getMap("moduleConfig");
                	boolean moduleConfigFound = false;
                	if (mconfig.get("jsonstr") != null) {
                		JsonObject json = new JsonObject((String)mconfig.get("jsonstr"));
                		json = json.getJsonObject(mainVerticle.split("\\.")[mainVerticle.split("\\.").length-1]);
                		if (json != null && !json.isEmpty()) {
                			Vertx.currentContext().config().mergeIn(json);
                			moduleConfigFound = true;
                			logger.info("Get Config from Hazelcast Instance");
                		}
                	}
                	
	                // initial config from config.json if exist
                	if (!moduleConfigFound) {
	                    File configFile = new File((new File("")).getAbsolutePath() + "/config.json");
	                    
	                    if (configFile.exists()) {
	                        
	                        Buffer buf = Vertx.currentContext().owner().fileSystem().readFileBlocking(configFile.getAbsolutePath());
	                        String jsonStr = buf.toString();
	                                                
	                        JsonObject config = new JsonObject(jsonStr);
	                        
	                        Vertx.currentContext().config().mergeIn(config);
	                        logger.info("Get Config from config.json file");
	                    }
                	}
                    
                    ConfigService config = ComponentManager.getServiceClient(ConfigService.class);
        
                    // get initial config
                    config.getAllSetting().setHandler(
                        ret -> {

                            if (ret.succeeded())
                                Vertx.currentContext().config().mergeIn(ret.result());
                            
                            deployMain(mainVerticle);
                        });
                });        
    }
    
    protected void deployMain(String mainVerticle) {
        
        DeploymentOptions option = new DeploymentOptions();
        option.setConfig(Vertx.currentContext().config());
        
        Vertx.currentContext().owner().deployVerticle(mainVerticle, option);
    }    
}
