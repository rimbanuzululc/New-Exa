package io.starlight.test.componentalt;

import io.starlight.Component;
import io.vertx.core.Future;
import io.starlight.test.component.TestComponent;

/**
 *
 * @author denny
 */
@Component("Alt")
public class TestComponentAlt implements TestComponent {

    @Override
    public Future<Integer> testInt(int a) {
        
        return Future.succeededFuture(a * 3);
    }

    @Override
    public Future<Boolean> testBoolean(boolean a) {
        
        return Future.succeededFuture(a);
    }

    @Override
    public Future<String> testString(String a, String b) {
        
        return Future.succeededFuture(a + "-" + b + " --> Alt");
    }
}
