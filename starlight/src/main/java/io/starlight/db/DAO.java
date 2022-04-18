package io.starlight.db;

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
public @interface DAO {

    String name() default "";
    
    String config() default "";
    String user() default "";
    String pass() default "";
    String url() default "";
    String driver() default "";
}
