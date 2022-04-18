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
public class SubmitSomasi {
    
    protected String code;
    protected String noHp;
    protected String longitude;
    protected Integer konsumenId;
    protected Integer idAgentPos;
    protected String type;
    protected String penerimaSomasi;
    protected String hubunganPenerima;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Integer getKonsumenId() {
        return konsumenId;
    }

    public void setKonsumenId(Integer konsumenId) {
        this.konsumenId = konsumenId;
    }

    public Integer getIdAgentPos() {
        return idAgentPos;
    }

    public void setIdAgentPos(Integer idAgentPos) {
        this.idAgentPos = idAgentPos;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPenerimaSomasi() {
        return penerimaSomasi;
    }

    public void setPenerimaSomasi(String penerimaSomasi) {
        this.penerimaSomasi = penerimaSomasi;
    }

    public String getHubunganPenerima() {
        return hubunganPenerima;
    }

    public void setHubunganPenerima(String hubunganPenerima) {
        this.hubunganPenerima = hubunganPenerima;
    }
    
}
