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
@Table("somasi_state")
public class State {
    
    protected Integer stateId;
    protected Integer countryid;
    protected String code;
    protected String name;
    protected Boolean isactive;
    protected Integer stateorder;
    

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCountryid() {
        return countryid;
    }

    public void setCountryid(Integer countryid) {
        this.countryid = countryid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

    public Integer getStateorder() {
        return stateorder;
    }

    public void setStateorder(Integer stateorder) {
        this.stateorder = stateorder;
    }
    
}
