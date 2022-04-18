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

/**
 *
 * @author hp
 */
@Table("somasi_konsumen")
public class KonsumenAggrement {
    
    @AutoKey
    protected Integer konsumenId;
    
    protected String noAggrement;
    protected String namaDebitur;
    protected String alamatKtp;
    protected String provinsiKtp;
    protected String kotaKtp;
    protected String kecamatanKtp;
    protected String kelurahanKtp;
    protected Integer zipcodeKtp;
    protected String alamatTinggal;
    protected String provinsiTinggal;
    protected String kotaTinggal;
    protected String kecamatanTinggal;
    protected String kelurahanTinggal;
    protected Integer zipcodeTinggal;
    protected String noTelp;
    protected String jenisPerjanjian;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Jakarta")
    protected Date tglPerjanjian;
    
    protected String merkKendaraan;
    protected String tahunKendaraan;
    protected String kondisiKendaraan;
    protected String nomorPolisi;
    protected String nomorRangka;
    protected String nomorMesin;
    protected String warna;
    protected String nomorBpkb;
    protected String nomorStnk;
    protected String sertifikatJaminan;
    protected String atasNamaBpkb;
    protected String alamatBpkb;
    protected Integer installment;
    protected Integer sisaOutstanding;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Jakarta")
    protected Date tglMulaiMenunggak;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Jakarta")
    protected Date tglSp1;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Jakarta")
    protected Date tglSp2;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Jakarta")
    protected Date sendSomasi1Date;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Jakarta")
    protected Date sendSomasi2Date;
    
    protected String kronologi;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    protected Date upload_date;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    protected Date last_modify;
    
    protected String statusSomasi;
    
    protected String confirmationLongitude;
    
    protected String statusByPhone;
    
    protected String type;
    
    protected Boolean isCompleted;
    
    @Computed
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    protected Date assignDate;
    
    @Computed
    protected String agentName;
    
    @Computed
    protected String penerimaSomasi;
    
    @Computed
    protected String hubunganPenerima;
    
    @Computed
    protected String statusagent;

    public Integer getKonsumenId() {
        return konsumenId;
    }

    public void setKonsumenId(Integer konsumenId) {
        this.konsumenId = konsumenId;
    }

    public String getNoAggrement() {
        return noAggrement;
    }

    public void setNoAggrement(String noAggrement) {
        this.noAggrement = noAggrement;
    }

    public String getNamaDebitur() {
        return namaDebitur;
    }

    public void setNamaDebitur(String namaDebitur) {
        this.namaDebitur = namaDebitur;
    }

    public String getAlamatKtp() {
        return alamatKtp;
    }

    public void setAlamatKtp(String alamatKtp) {
        this.alamatKtp = alamatKtp;
    }

    public String getProvinsiKtp() {
        return provinsiKtp;
    }

    public void setProvinsiKtp(String provinsiKtp) {
        this.provinsiKtp = provinsiKtp;
    }

    public String getKotaKtp() {
        return kotaKtp;
    }

    public void setKotaKtp(String kotaKtp) {
        this.kotaKtp = kotaKtp;
    }

    public String getKecamatanKtp() {
        return kecamatanKtp;
    }

    public void setKecamatanKtp(String kecamatanKtp) {
        this.kecamatanKtp = kecamatanKtp;
    }

    public String getKelurahanKtp() {
        return kelurahanKtp;
    }

    public void setKelurahanKtp(String kelurahanKtp) {
        this.kelurahanKtp = kelurahanKtp;
    }

    public Integer getZipcodeKtp() {
        return zipcodeKtp;
    }

    public void setZipcodeKtp(Integer zipcodeKtp) {
        this.zipcodeKtp = zipcodeKtp;
    }

    public String getAlamatTinggal() {
        return alamatTinggal;
    }

    public void setAlamatTinggal(String alamatTinggal) {
        this.alamatTinggal = alamatTinggal;
    }

    public String getProvinsiTinggal() {
        return provinsiTinggal;
    }

    public void setProvinsiTinggal(String provinsiTinggal) {
        this.provinsiTinggal = provinsiTinggal;
    }

    public String getKotaTinggal() {
        return kotaTinggal;
    }

    public void setKotaTinggal(String kotaTinggal) {
        this.kotaTinggal = kotaTinggal;
    }

    public String getKecamatanTinggal() {
        return kecamatanTinggal;
    }

    public void setKecamatanTinggal(String kecamatanTinggal) {
        this.kecamatanTinggal = kecamatanTinggal;
    }

    public String getKelurahanTinggal() {
        return kelurahanTinggal;
    }

    public void setKelurahanTinggal(String kelurahanTinggal) {
        this.kelurahanTinggal = kelurahanTinggal;
    }

    public Integer getZipcodeTinggal() {
        return zipcodeTinggal;
    }

    public void setZipcodeTinggal(Integer zipcodeTinggal) {
        this.zipcodeTinggal = zipcodeTinggal;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getJenisPerjanjian() {
        return jenisPerjanjian;
    }

    public void setJenisPerjanjian(String jenisPerjanjian) {
        this.jenisPerjanjian = jenisPerjanjian;
    }

    public Date getTglPerjanjian() {
        return tglPerjanjian;
    }

    public void setTglPerjanjian(Date tglPerjanjian) {
        this.tglPerjanjian = tglPerjanjian;
    }

    public String getMerkKendaraan() {
        return merkKendaraan;
    }

    public void setMerkKendaraan(String merkKendaraan) {
        this.merkKendaraan = merkKendaraan;
    }

    public String getTahunKendaraan() {
        return tahunKendaraan;
    }

    public void setTahunKendaraan(String tahunKendaraan) {
        this.tahunKendaraan = tahunKendaraan;
    }

    public String getKondisiKendaraan() {
        return kondisiKendaraan;
    }

    public void setKondisiKendaraan(String kondisiKendaraan) {
        this.kondisiKendaraan = kondisiKendaraan;
    }

    public String getNomorPolisi() {
        return nomorPolisi;
    }

    public void setNomorPolisi(String nomorPolisi) {
        this.nomorPolisi = nomorPolisi;
    }

    public String getNomorRangka() {
        return nomorRangka;
    }

    public void setNomorRangka(String nomorRangka) {
        this.nomorRangka = nomorRangka;
    }

    public String getNomorMesin() {
        return nomorMesin;
    }

    public void setNomorMesin(String nomorMesin) {
        this.nomorMesin = nomorMesin;
    }

    public String getWarna() {
        return warna;
    }

    public void setWarna(String warna) {
        this.warna = warna;
    }

    public String getNomorBpkb() {
        return nomorBpkb;
    }

    public void setNomorBpkb(String nomorBpkb) {
        this.nomorBpkb = nomorBpkb;
    }

    public String getNomorStnk() {
        return nomorStnk;
    }

    public void setNomorStnk(String nomorStnk) {
        this.nomorStnk = nomorStnk;
    }

    public String getSertifikatJaminan() {
        return sertifikatJaminan;
    }

    public void setSertifikatJaminan(String sertifikatJaminan) {
        this.sertifikatJaminan = sertifikatJaminan;
    }

    public String getAtasNamaBpkb() {
        return atasNamaBpkb;
    }

    public void setAtasNamaBpkb(String atasNamaBpkb) {
        this.atasNamaBpkb = atasNamaBpkb;
    }

    public String getAlamatBpkb() {
        return alamatBpkb;
    }

    public void setAlamatBpkb(String alamatBpkb) {
        this.alamatBpkb = alamatBpkb;
    }

    public Integer getInstallment() {
        return installment;
    }

    public void setInstallment(Integer installment) {
        this.installment = installment;
    }

    public Integer getSisaOutstanding() {
        return sisaOutstanding;
    }

    public void setSisaOutstanding(Integer sisaOutstanding) {
        this.sisaOutstanding = sisaOutstanding;
    }

    public Date getTglMulaiMenunggak() {
        return tglMulaiMenunggak;
    }

    public void setTglMulaiMenunggak(Date tglMulaiMenunggak) {
        this.tglMulaiMenunggak = tglMulaiMenunggak;
    }

    public Date getTglSp1() {
        return tglSp1;
    }

    public void setTglSp1(Date tglSp1) {
        this.tglSp1 = tglSp1;
    }

    public Date getTglSp2() {
        return tglSp2;
    }

    public void setTglSp2(Date tglSp2) {
        this.tglSp2 = tglSp2;
    }

    public String getKronologi() {
        return kronologi;
    }

    public void setKronologi(String kronologi) {
        this.kronologi = kronologi;
    }

    public Date getUpload_date() {
        return upload_date;
    }

    public void setUpload_date(Date upload_date) {
        this.upload_date = upload_date;
    }

    public Date getLast_modify() {
        return last_modify;
    }

    public void setLast_modify(Date last_modify) {
        this.last_modify = last_modify;
    }

    public Date getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(Date assignDate) {
        this.assignDate = assignDate;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getConfirmationLongitude() {
        return confirmationLongitude;
    }

    public void setConfirmationLongitude(String confirmationLongitude) {
        this.confirmationLongitude = confirmationLongitude;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatusSomasi() {
        return statusSomasi;
    }

    public void setStatusSomasi(String statusSomasi) {
        this.statusSomasi = statusSomasi;
    }

    public Date getSendSomasi1Date() {
        return sendSomasi1Date;
    }

    public void setSendSomasi1Date(Date sendSomasi1Date) {
        this.sendSomasi1Date = sendSomasi1Date;
    }

    public Date getSendSomasi2Date() {
        return sendSomasi2Date;
    }

    public void setSendSomasi2Date(Date sendSomasi2Date) {
        this.sendSomasi2Date = sendSomasi2Date;
    }

    public String getStatusByPhone() {
        return statusByPhone;
    }

    public void setStatusByPhone(String statusByPhone) {
        this.statusByPhone = statusByPhone;
    }

    public String getPenerimaSomasi() {
        return penerimaSomasi;
    }

    public void setPenerimaSomasi(String penerimaSomasi) {
        this.penerimaSomasi = penerimaSomasi;
    }

    public String getHubunganPenerima() {
        return hubunganPenerima;
    }

    public void setHubunganPenerima(String hubunganPenerima) {
        this.hubunganPenerima = hubunganPenerima;
    }

    public String getStatusagent() {
        return statusagent;
    }

    public void setStatusagent(String statusagent) {
        this.statusagent = statusagent;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
    
}
