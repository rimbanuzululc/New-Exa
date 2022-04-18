package io.starlight;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

/**
 *
 * @author denny
 */
public interface ConfigService {

    Future<JsonObject> getAllSetting();
    Future<JsonObject> getSetting(String name);
    Future<String> getString(String name);
    Future<Integer> getInt(String name);
    Future<Boolean> getBool(String name);
    
    Future<Boolean> setSetting(String name, JsonObject obj);
    Future<Boolean> setInt(String name, int value);
    Future<Boolean> setString(String name, String value);
    Future<Boolean> setBool(String name, Boolean value);
}
