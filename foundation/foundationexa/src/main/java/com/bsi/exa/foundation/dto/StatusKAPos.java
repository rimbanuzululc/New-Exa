/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.dto;

import io.starlight.db.Key;
import io.starlight.db.Table;

/**
 *
 * @author hp
 */
@Table("somasi_statuskapos")
public class StatusKAPos {
    
    @Key
    protected String code;
    
    protected String description;
    protected Integer kesimpulanId;
    protected String kesimpulan;

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

    public Integer getKesimpulanId() {
        return kesimpulanId;
    }

    public void setKesimpulanId(Integer kesimpulanId) {
        this.kesimpulanId = kesimpulanId;
    }

    public String getKesimpulan() {
        return kesimpulan;
    }

    public void setKesimpulan(String kesimpulan) {
        this.kesimpulan = kesimpulan;
    }
    
}
