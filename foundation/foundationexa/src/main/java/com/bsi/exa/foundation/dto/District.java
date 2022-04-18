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
@Table("somasi_district")
public class District {
    
    protected Integer districtId;
    protected Integer cityId;
    protected String code;
    protected String name;
    protected Boolean isactive;
    protected Integer districtorder;

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
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

    public Integer getDistrictorder() {
        return districtorder;
    }

    public void setDistrictorder(Integer districtorder) {
        this.districtorder = districtorder;
    }
}
