package io.starlight.http;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author denny
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EnableWebServer {

    String port() default "9090";
    Class<?>[] preHandler() default {};
    Class<?>[] postHandler() default {};
}
