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
@Table("somasi_historydownload")
public class HistoryDownload {
    
    @AutoKey
    protected Integer id;
    
    protected String downloadby;
    protected String nosuratsomasi;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    protected Date downloadTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDownloadby() {
        return downloadby;
    }

    public void setDownloadby(String downloadby) {
        this.downloadby = downloadby;
    }

    public String getNosuratsomasi() {
        return nosuratsomasi;
    }

    public void setNosuratsomasi(String nosuratsomasi) {
        this.nosuratsomasi = nosuratsomasi;
    }

    public Date getDownloadTime() {
        return downloadTime;
    }

    public void setDownloadTime(Date downloadTime) {
        this.downloadTime = downloadTime;
    }
    
}
