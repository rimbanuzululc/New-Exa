package io.starlight.http;

import io.starlight.ResponseTimeout;
import io.vertx.core.http.HttpMethod;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Note that {@link ResponseTimeout} is ignored when this method is applied
 * @author denny
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequestMapping {
    
    String value();
    HttpMethod method() default HttpMethod.GET;
    ResponseType produce() default ResponseType.JSON;
    /**
     * Response timeout in milliseconds
     * @return milliseconds (default is 35000)
     */
    long timeout() default 35000;
}
