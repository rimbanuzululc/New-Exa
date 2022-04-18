package io.starlight;

import io.vertx.core.Future;

/**
 *
 * @author denny
 */
public interface LoggerService {

    Future debug(String logStr);
    Future debug(String logStr, Throwable e);
}
