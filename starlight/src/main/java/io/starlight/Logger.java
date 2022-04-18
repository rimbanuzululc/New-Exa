package io.starlight;

import io.vertx.core.logging.LoggerFactory;

/**
 *
 * @author denny
 */
public class Logger {
	private final io.vertx.core.logging.Logger l;

    protected Logger(String loggerName) {
    
        this.l = LoggerFactory.getLogger(loggerName);
    }
    
    protected Logger(Class<?> cls) {
        
        this.l = LoggerFactory.getLogger(cls);
    }
    
    public static Logger getLogger(Class<?> type) {
        
        return new Logger(type.getName());
    }
    
    public void debug(String logStr) {
        
        debug(logStr, null);
    }
    
    public void info(String logStr) {
        
        l.info(logStr);
    }
    
    public void warn(String logStr, Throwable e) {
        
    	if (e == null) {
    		l.warn(logStr);
    	} else {
    		l.warn(logStr, e);
    	}
    }
    
    public void warn(String logStr) {        
    		l.warn(logStr);
    }
    
    public void error(String logStr, Throwable e) {
        
    	if (e == null) {
    		l.error(logStr);
    	} else {
    		l.error(logStr, e);
    	}
    }
    
    public void debug(String logStr, Throwable e) {
    	if (e == null) {
    		l.debug(logStr);
    	} else {
    		l.debug(logStr, e);
    	}
    }
}
