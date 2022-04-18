package io.starlight;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(METHOD)
public @interface ResponseTimeout {
	/**
	 * Response timeout in milliseconds
	 * @return milliseconds (default is 20000)
	 */
	long value() default 20000l;
}
