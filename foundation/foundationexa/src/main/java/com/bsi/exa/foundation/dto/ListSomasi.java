/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.starlight.db.Computed;
import java.util.Date;

/**
 *
 * @author hp
 */
public class ListSomasi {
    
    protected Integer konsumenId;
    protected String namaDebitur;
    protected String alamat;
    protected String kota;
    protected String kecamatan;
    protected String kelurahan;
    protected Integer zipcode;
    protected String noTelp;
    protected String noAggrement;
    protected Boolean sp;
    protected Boolean spt;
    protected String nomorPolisi;
    
    @Computed
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Jakarta")
    protected Date assign_date;

    public String getNamaDebitur() {
        return namaDebitur;
    }

    public void setNamaDebitur(String namaDebitur) {
        this.namaDebitur = namaDebitur;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getNoAggrement() {
        return noAggrement;
    }

    public void setNoAggrement(String noAggrement) {
        this.noAggrement = noAggrement;
    }

    public String getNomorPolisi() {
        return nomorPolisi;
    }

    public void setNomorPolisi(String nomorPolisi) {
        this.nomorPolisi = nomorPolisi;
    }

    public Date getAssign_date() {
        return assign_date;
    }

    public void setAssign_date(Date assign_date) {
        this.assign_date = assign_date;
    }

    public Boolean getSp() {
        return sp;
    }

    public void setSp(Boolean sp) {
        this.sp = sp;
    }

    public Boolean getSpt() {
        return spt;
    }

    public void setSpt(Boolean spt) {
        this.spt = spt;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getKelurahan() {
        return kelurahan;
    }

    public void setKelurahan(String kelurahan) {
        this.kelurahan = kelurahan;
    }

    public Integer getZipcode() {
        return zipcode;
    }

    public void setZipcode(Integer zipcode) {
        this.zipcode = zipcode;
    }

    public Integer getKonsumenId() {
        return konsumenId;
    }

    public void setKonsumenId(Integer konsumenId) {
        this.konsumenId = konsumenId;
    }
    
}
