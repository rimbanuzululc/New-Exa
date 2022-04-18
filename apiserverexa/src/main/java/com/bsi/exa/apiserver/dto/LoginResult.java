package com.bsi.exa.apiserver.dto;

public class LoginResult {

    protected String accessToken;
    protected Object user;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Object getUser() {
        return user;
    }

    public void setUser(Object user) {
        this.user = user;
    }

    
}
