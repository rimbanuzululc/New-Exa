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
@Table("somasi_mappingareadminpos")
public class MappingAreaAdminPos {
    
    @AutoKey
    protected Integer idMappingAreaAdmin;
    
    protected String userId;
    protected String kecamatan;
    protected Integer districtId;

    public Integer getIdMappingAreaAdmin() {
        return idMappingAreaAdmin;
    }

    public void setIdMappingAreaAdmin(Integer idMappingAreaAdmin) {
        this.idMappingAreaAdmin = idMappingAreaAdmin;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }
    
}
