/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.starlight.db.AutoKey;
import io.starlight.db.Computed;
import io.starlight.db.Table;
import java.util.Date;
import java.util.List;

/**
 *
 * @author hp
 */
@Table("somasi_agentpos")
public class AgentPos {

    @AutoKey
    protected Integer idAgentpos;
    
    protected String username;
    protected String nama;
    protected String password;
//    protected Integer idMappingArea;
    protected Integer districtId;
    protected Integer subDistrictId;
    protected Integer cityId;
    protected Integer stateId;
    protected Integer countryId;
    protected String zipcode;
    protected Boolean isActive;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Jakarta")
    protected Date created;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Jakarta")
    protected Date expiredPassword;
    
    @Computed
    protected Boolean changePassword;
    
    @Computed
    protected List<Integer>  listMappingAreaKaPos;
    
    @Computed
    protected String countryName;
    
    @Computed
    protected String stateName;
    
    @Computed
    protected String cityName;
    
    @Computed
    protected String districtName;
    
    @Computed
    protected String subDistrictName;
    
    public Integer getIdAgentpos() {
        return idAgentpos;
    }

    public void setIdAgentpos(Integer idAgentpos) {
        this.idAgentpos = idAgentpos;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public Integer getIdMappingArea() {
//        return idMappingArea;
//    }
//
//    public void setIdMappingArea(Integer idMappingArea) {
//        this.idMappingArea = idMappingArea;
//    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Boolean getChangePassword() {
        return changePassword;
    }

    public void setChangePassword(Boolean changePassword) {
        this.changePassword = changePassword;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public List<Integer> getListMappingAreaKaPos() {
        return listMappingAreaKaPos;
    }

    public void setListMappingAreaKaPos(List<Integer> listMappingAreaKaPos) {
        this.listMappingAreaKaPos = listMappingAreaKaPos;
    }

    public Integer getSubDistrictId() {
        return subDistrictId;
    }

    public void setSubDistrictId(Integer subDistrictId) {
        this.subDistrictId = subDistrictId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Date getExpiredPassword() {
        return expiredPassword;
    }

    public void setExpiredPassword(Date expiredPassword) {
        this.expiredPassword = expiredPassword;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getSubDistrictName() {
        return subDistrictName;
    }

    public void setSubDistrictName(String subDistrictName) {
        this.subDistrictName = subDistrictName;
    }
    
    
}
