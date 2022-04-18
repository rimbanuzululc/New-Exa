/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.starlight.db.AutoKey;
import io.starlight.db.Table;
import java.util.Date;

/**
 *
 * @author hp
 */
@Table("somasi_historystatus")
public class HistoryStatus {
    
    @AutoKey
    protected Integer idStatusKonsumen;
    
    protected Integer idStatus1;
    protected Integer idStatus2;
    protected Integer konsumenId;
    protected Integer idAgentPos;
    protected String statusByPhone;
    
    protected String penerimaSomasi;
    protected String hubunganPenerima;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Jakarta")
    protected Date created;

    public Integer getIdStatusKonsumen() {
        return idStatusKonsumen;
    }

    public void setIdStatusKonsumen(Integer idStatusKonsumen) {
        this.idStatusKonsumen = idStatusKonsumen;
    }

    public Integer getIdStatus1() {
        return idStatus1;
    }

    public void setIdStatus1(Integer idStatus1) {
        this.idStatus1 = idStatus1;
    }

    public Integer getIdStatus2() {
        return idStatus2;
    }

    public void setIdStatus2(Integer idStatus2) {
        this.idStatus2 = idStatus2;
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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getStatusByPhone() {
        return statusByPhone;
    }

    public void setStatusByPhone(String statusByPhone) {
        this.statusByPhone = statusByPhone;
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
