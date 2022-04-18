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
@Target(ElementType.FIELD)
public @interface AutoWired {

    String value() default "";
    
    /**
     * Timeout in milliseconds
     * @return timeout (default is 30000)
     */
    long timeout() default 30000;
}
