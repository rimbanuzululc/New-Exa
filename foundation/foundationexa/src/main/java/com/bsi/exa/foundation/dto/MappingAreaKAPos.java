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
@Table("somasi_mappingareakapos")
public class MappingAreaKAPos {
    
    @AutoKey
    protected Integer idMappingArea;
    
    protected String userId;
    protected String kota;
    protected Integer cityId;

    public Integer getIdMappingArea() {
        return idMappingArea;
    }

    public void setIdMappingArea(Integer idMappingArea) {
        this.idMappingArea = idMappingArea;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }
    
    
}
