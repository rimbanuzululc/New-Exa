package io.starlight.test.component;

import io.starlight.Component;
import io.starlight.Config;
import io.starlight.Init;
import io.vertx.core.Future;

/**
 *
 * @author denny
 */
@Component
public class TestComponentImpl implements TestComponent {

    @Config("config.int")
    protected int dataInt;
    
    @Config("config.string")
    protected String dataString;

    @Config("config.bool")
    protected Boolean dataBoolean;

    @Init
    public void init() {
        
        System.err.println("dataInt : " + dataInt);
        System.err.println("dataString : " + dataString);
        System.err.println("dataBoolean : " + dataBoolean);
    }
    
    @Override
    public Future<Integer> testInt(int a) {
        
        return Future.succeededFuture(a * 2);
    }

    @Override
    public Future<Boolean> testBoolean(boolean a) {
        
        return Future.succeededFuture(!a);
    }

    @Override
    public Future<String> testString(String a, String b) {
        
        return Future.succeededFuture(a + "-" + b);
    }
    
    
}
