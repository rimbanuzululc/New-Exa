package io.starlight.test.config;

import io.starlight.ConfigService;
import io.starlight.Service;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

/**
 *
 * @author denny
 */
@Service
public class ConfigServiceImpl implements ConfigService {

    @Override
    public Future<JsonObject> getSetting(String name) {
        
        JsonObject ret = new JsonObject();
        ret.put("dataInt", 1);
        ret.put("dataString", "aabb");
        ret.put("dataBool", true);
        
        return Future.succeededFuture(ret);
    }

    @Override
    public Future<String> getString(String name) {
                
        return Future.succeededFuture("aabb");
    }

    @Override
    public Future<Integer> getInt(String name) {
        
        return Future.succeededFuture(123);
    }

    @Override
    public Future<Boolean> getBool(String name) {
        
        return Future.succeededFuture(true);
    }

    @Override
    public Future<Boolean> set(String name, JsonObject obj) {
        
        return Future.succeededFuture(true);
    }

    @Override
    public Future<Boolean> set(String name, int value) {
        
        return Future.succeededFuture(true);
    }

    @Override
    public Future<Boolean> set(String name, String value) {
        
        return Future.succeededFuture(true);
    }

    @Override
    public Future<JsonObject> getAllSetting() {
        
        JsonObject ret = new JsonObject();
        ret.put("configInt", 1);
        ret.put("configString", "aabb");
        ret.put("configBool", true);
        
        return Future.succeededFuture(ret);
    }
    
}
