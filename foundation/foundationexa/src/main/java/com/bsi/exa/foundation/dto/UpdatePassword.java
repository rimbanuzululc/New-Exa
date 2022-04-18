/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.dto;

/**
 *
 * @author hp
 */
public class UpdatePassword {
    
    protected Boolean newPassword;
    
    protected Boolean accepted;

    public Boolean getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(Boolean newPassword) {
        this.newPassword = newPassword;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }
    
}
