/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.starlight.db.AutoKey;
import io.starlight.db.Key;
import io.starlight.db.Table;
import java.util.Date;

/**
 *
 * @author hp
 */
@Table("somasi_otp")
public class OTP {
    
    @AutoKey
    protected Integer otpId;
    
    protected String noDebitur;
    
    @Key
    protected Integer idAgentpos;
    protected String otp;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    protected Date created;

    public String getNoDebitur() {
        return noDebitur;
    }

    public void setNoDebitur(String noDebitur) {
        this.noDebitur = noDebitur;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Integer getIdAgentpos() {
        return idAgentpos;
    }

    public void setIdAgentpos(Integer idAgentpos) {
        this.idAgentpos = idAgentpos;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Integer getOtpId() {
        return otpId;
    }

    public void setOtpId(Integer otpId) {
        this.otpId = otpId;
    }
    
}
