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
@Table("somasi_assign")
public class Assign {
    
    @AutoKey
    protected Integer idAssign;
    
    protected Integer idKonsumen;
    protected Integer idAgentpos;
    protected String ref_No;
    protected String cardNo;
    
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    protected Date assignDate;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    protected Date modifyDate;
    
    protected Boolean sp;
    protected Boolean spt;
    protected Boolean print;

    public Integer getIdKonsumen() {
        return idKonsumen;
    }

    public void setIdKonsumen(Integer idKonsumen) {
        this.idKonsumen = idKonsumen;
    }

    public Integer getIdAgentpos() {
        return idAgentpos;
    }

    public void setIdAgentpos(Integer idAgentpos) {
        this.idAgentpos = idAgentpos;
    }

    public String getRef_No() {
        return ref_No;
    }

    public void setRef_No(String ref_No) {
        this.ref_No = ref_No;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Date getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(Date assignDate) {
        this.assignDate = assignDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Boolean getSp() {
        return sp;
    }

    public void setSp(Boolean sp) {
        this.sp = sp;
    }

    public Boolean getSpt() {
        return spt;
    }

    public void setSpt(Boolean spt) {
        this.spt = spt;
    }

    public Boolean getPrint() {
        return print;
    }

    public void setPrint(Boolean print) {
        this.print = print;
    }

}
