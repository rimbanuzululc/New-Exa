/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.starlight.db.AutoKey;
import io.starlight.db.Table;
import java.util.Date;

/**
 *
 * @author hp
 */
@Table("somasi_imageKonsumenFinance")
public class ImageKonsumenFinance {
    
    @AutoKey
    protected Integer idKonsumenFinance;
    
    protected String imageName;
    protected String imagePath;
    protected String imageFile;
    protected String imageLongitude;
    protected Integer konsumenid;
    protected Integer idAgentPos;
    protected String type;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Jakarta")
    protected Date created;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Jakarta")
    protected Date modifiedDate;

    public Integer getIdKonsumenFinance() {
        return idKonsumenFinance;
    }

    public void setIdKonsumenFinance(Integer idKonsumenFinance) {
        this.idKonsumenFinance = idKonsumenFinance;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImageLongitude() {
        return imageLongitude;
    }

    public void setImageLongitude(String imageLongitude) {
        this.imageLongitude = imageLongitude;
    }

    public Integer getKonsumenid() {
        return konsumenid;
    }

    public void setKonsumenid(Integer konsumenid) {
        this.konsumenid = konsumenid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Integer getIdAgentPos() {
        return idAgentPos;
    }

    public void setIdAgentPos(Integer idAgentPos) {
        this.idAgentPos = idAgentPos;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
    
}
