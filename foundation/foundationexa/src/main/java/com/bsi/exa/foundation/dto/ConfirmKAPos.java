/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.dto;

import io.starlight.db.AutoKey;
import io.starlight.db.Computed;
import io.starlight.db.Table;

/**
 *
 * @author aldy.kurniawan
 */

@Table("somasi_confirmkapos")
public class ConfirmKAPos {
    
    @AutoKey
    protected Integer idConfirmKapos;
    protected Integer idAgentPos;
    protected Integer konsumenId;
    protected String userIdKapos;
    protected String status;
    protected String noOtpDebitur;
    
    @Computed
    protected String noDebitur;
    
    @Computed 
    protected String noAggrement;
    
    @Computed
    protected String namaDebitur;

    public Integer getIdConfirmKapos() {
        return idConfirmKapos;
    }

    public void setIdConfirmKapos(Integer idConfirmKapos) {
        this.idConfirmKapos = idConfirmKapos;
    }

    public Integer getIdAgentPos() {
        return idAgentPos;
    }

    public void setIdAgentPos(Integer idAgentPos) {
        this.idAgentPos = idAgentPos;
    }

    public Integer getKonsumenId() {
        return konsumenId;
    }

    public void setKonsumenId(Integer konsumenId) {
        this.konsumenId = konsumenId;
    }

    public String getUserIdKapos() {
        return userIdKapos;
    }

    public void setUserIdKapos(String userIdKapos) {
        this.userIdKapos = userIdKapos;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNoOtpDebitur() {
        return noOtpDebitur;
    }

    public void setNoOtpDebitur(String noOtpDebitur) {
        this.noOtpDebitur = noOtpDebitur;
    }

    public String getNoDebitur() {
        return noDebitur;
    }

    public void setNoDebitur(String noDebitur) {
        this.noDebitur = noDebitur;
    }

    public String getNoAggrement() {
        return noAggrement;
    }

    public void setNoAggrement(String noAggrement) {
        this.noAggrement = noAggrement;
    }

    public String getNamaDebitur() {
        return namaDebitur;
    }

    public void setNamaDebitur(String namaDebitur) {
        this.namaDebitur = namaDebitur;
    }
    
    
    
}
