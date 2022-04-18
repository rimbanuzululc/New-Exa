/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.dto;

import io.starlight.db.AutoKey;
import io.starlight.db.Table;

/**
 *
 * @author hp
 */
@Table("somasi_confirmagentpos")
public class ConfirmAgentPos {
    
    protected Integer idStatus1;
    protected String code;
    protected String description;
    protected int value;

    public Integer getIdStatus1() {
        return idStatus1;
    }

    public void setIdStatus1(Integer idStatus1) {
        this.idStatus1 = idStatus1;
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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
