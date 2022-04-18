package com.bsi.exa.apiserver.dto;

import com.bsi.exa.foundation.CodedException;
import com.bsi.exa.foundation.Errors;

/**
 *
 * @author denny
 */
public class APIResult {

    protected int errorCode = 0;
    protected String errorMsg = "";
    protected Object result;

    public APIResult() {
    }
    
    public APIResult(Errors error) {
        
        error(error);
    }
    
    public APIResult(int errorCode, String errorMsg) {
        
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
    
    public APIResult(Object result) {
        
        this.result = result;
    }

    public void error(Errors error) {
        
        error(error, null);
    }
    
    public void error(Throwable thro) {
        
        System.out.println("error handling controller : " + thro);
        
        Errors ret = Errors.COMMON;
        String msg = thro.getMessage();
        
        if (msg == null)
            msg = "" + thro;
                    
        for (Errors err : Errors.values()) {

            if (msg.contains(err.getErrorMsg())) {

                ret = err;
                break;
            }
        }

        if (ret == Errors.COMMON) {
         
            int pos = msg.indexOf(":");

            if (pos > 0)
                msg = msg.substring(pos + 1);
        }
        else
            msg = ret.getErrorMsg();
        
        error(ret.getErrorCode(), msg);
    }
    
    public void error(CodedException excp) {
        
        error(excp.getCode(), excp.getMessage());
    }
    
    public void error(int errorCode, String errorMsg) {
        
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
    
    public void error(Errors error, String additionalMsg) {
        
        this.errorCode = error.getErrorCode();
        this.errorMsg = error.getErrorMsg()+ (additionalMsg == null ? "" : " : " + additionalMsg);
    }
    
    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
    
    
}
