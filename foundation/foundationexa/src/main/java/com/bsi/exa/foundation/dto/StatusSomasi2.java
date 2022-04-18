/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.dto;

import io.starlight.db.Table;

/**
 *
 * @author hp
 */
@Table("somasi_status2")
public class StatusSomasi2 {
    
    protected Integer idStatus2;
    protected String code;
    protected String description;

    public Integer getIdStatus2() {
        return idStatus2;
    }

    public void setIdStatus2(Integer idStatus2) {
        this.idStatus2 = idStatus2;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
