package com.bsi.exa.foundation;

/**
 *
 * @author denny
 */
public class CodedException extends Exception {

    protected int code;
    
    public CodedException(int code, String msg) {
             
        super(msg);
        
        this.code = code;
    }
    
    public CodedException(Errors err) {
        
        super(err.getErrorMsg());
        this.code = err.getErrorCode();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
