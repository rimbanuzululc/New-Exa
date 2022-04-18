package io.starlight;

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
public @interface Service {
    
    String value() default "";
    
    /**
     * Request timeout in milliseconds
     * @return default is 29000 milliseconds
     */
    long timeout() default 29000;
}
