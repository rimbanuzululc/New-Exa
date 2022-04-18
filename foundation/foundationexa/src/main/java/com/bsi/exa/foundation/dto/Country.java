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
@Table("somasi_country")
public class Country {
    
    protected Integer countryid;
    protected String code;
    protected String name;
    protected Boolean isactive;
    protected Boolean allowotherstate;
    protected Integer countryorder;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

    public Boolean getAllowotherstate() {
        return allowotherstate;
    }

    public void setAllowotherstate(Boolean allowotherstate) {
        this.allowotherstate = allowotherstate;
    }

    public Integer getCountryorder() {
        return countryorder;
    }

    public void setCountryorder(Integer countryorder) {
        this.countryorder = countryorder;
    }
    
}
