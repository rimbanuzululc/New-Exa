package com.bsi.exa.foundation;

/**
 *
 * @author denny
 */
public enum Errors {

    // 10000 - 10999 login error
    
    LOGIN_USER_NOT_FOUND    (10001, "User Not Found"),
    LOGIN_WRONG_NOPHONE    (10002, "Wrong No Handphone"),
    LOGIN_WRONG_PASSWORD    (10005, "Wrong Password"),
    INVALID_TOKEN           (10003, "Invalid Token"),
    ALREADY_TAKEN_ZIPCODE   (10006, "Zipcode Already Taken"),
    
    PHONE_EMAIL_REGISTERED  (10010, "Phone or email has been registered"),
    INVALID_UNIQUE_CODE     (10011, "Invalid unique code"),
    
    // Order
    INVALID_VOUCHER         (20011, "Invalid voucher code"),
    
    // Not defined
    
    COMMON                  (99999, "Common Error")
    ;
    
    protected int errorCode = 0;
    protected String errorMsg = "";
    
    Errors(int errorCode, String errorMsg) {
        
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
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

    
}
