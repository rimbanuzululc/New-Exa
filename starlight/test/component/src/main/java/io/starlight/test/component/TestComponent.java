package io.starlight.test.component;

import io.vertx.core.Future;

/**
 *
 * @author denny
 */
public interface TestComponent {

    Future<Integer> testInt(int a);
    Future<Boolean> testBoolean(boolean a);
    Future<String> testString(String a, String b);
}
