/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.dto;

/**
 *
 * @author hp
 */
public class ReportProductivity {
    
    protected Integer berhasil;
    protected Integer pending;
    protected Integer failed;

    public Integer getBerhasil() {
        return berhasil;
    }

    public void setBerhasil(Integer berhasil) {
        this.berhasil = berhasil;
    }

    public Integer getPending() {
        return pending;
    }

    public void setPending(Integer pending) {
        this.pending = pending;
    }

    public Integer getFailed() {
        return failed;
    }

    public void setFailed(Integer failed) {
        this.failed = failed;
    }
    
}
