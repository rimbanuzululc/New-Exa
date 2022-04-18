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
@Table("somasi_zipcode")
public class Zipcode {
    
    @AutoKey
    protected Integer zipcodeid;
    
    protected String zipcode;
    protected String zipdescription;
    protected String kelurahancode;
    protected String kecamatancode;
    protected String citycode;
    protected String provinsicode;

    public Integer getZipcodeid() {
        return zipcodeid;
    }

    public void setZipcodeid(Integer zipcodeid) {
        this.zipcodeid = zipcodeid;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getZipdescription() {
        return zipdescription;
    }

    public void setZipdescription(String zipdescription) {
        this.zipdescription = zipdescription;
    }

    public String getKelurahancode() {
        return kelurahancode;
    }

    public void setKelurahancode(String kelurahancode) {
        this.kelurahancode = kelurahancode;
    }

    public String getKecamatancode() {
        return kecamatancode;
    }

    public void setKecamatancode(String kecamatancode) {
        this.kecamatancode = kecamatancode;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getProvinsicode() {
        return provinsicode;
    }

    public void setProvinsicode(String provinsicode) {
        this.provinsicode = provinsicode;
    }

    
    
}
