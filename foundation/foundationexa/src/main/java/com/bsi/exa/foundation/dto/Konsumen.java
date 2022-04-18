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

@Table("backup_konsumen")
public class Konsumen {
    
    @AutoKey
    protected Integer idKonsumen;
    
    protected String ref_No;
    protected String cardNo;
    protected String name;
    protected String outstanding;
    protected String main_address_1;
    protected String address_1_city;
    protected String address_1_zipcode;
    protected String address_2;
    protected String address_2_zipcode;
    protected String address_2_city;
    protected String address_3;
    protected String address_3_zipcode;
    protected String address_3_city;
    protected String hp_no;
    protected String hp_no2;
    protected String home_phone_1;
    protected String office_phone_1;
    protected String no_ktp;
    protected String expired_date;
    protected String kesimpulan;
    protected String shippingstatus;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    protected Date shippingdate;
            
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    protected Date upload_date;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    protected Date last_modify;
    
    protected Integer dpd;
    
    @Computed
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    protected Date assignDate;

    public Date getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(Date assignDate) {
        this.assignDate = assignDate;
    }
    
    public Integer getIdKonsumen() {
        return idKonsumen;
    }

    public void setIdKonsumen(Integer idKonsumen) {
        this.idKonsumen = idKonsumen;
    }

    public String getRef_No() {
        return ref_No;
    }

    public void setRef_No(String ref_No) {
        this.ref_No = ref_No;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOutstanding() {
        return outstanding;
    }

    public void setOutstanding(String outstanding) {
        this.outstanding = outstanding;
    }

    public String getMain_address_1() {
        return main_address_1;
    }

    public void setMain_address_1(String main_address_1) {
        this.main_address_1 = main_address_1;
    }

    public String getAddress_1_city() {
        return address_1_city;
    }

    public void setAddress_1_city(String address_1_city) {
        this.address_1_city = address_1_city;
    }

    public String getAddress_1_zipcode() {
        return address_1_zipcode;
    }

    public void setAddress_1_zipcode(String address_1_zipcode) {
        this.address_1_zipcode = address_1_zipcode;
    }

    public String getAddress_2() {
        return address_2;
    }

    public void setAddress_2(String address_2) {
        this.address_2 = address_2;
    }

    public String getAddress_2_zipcode() {
        return address_2_zipcode;
    }

    public void setAddress_2_zipcode(String address_2_zipcode) {
        this.address_2_zipcode = address_2_zipcode;
    }

    public String getAddress_2_city() {
        return address_2_city;
    }

    public void setAddress_2_city(String address_2_city) {
        this.address_2_city = address_2_city;
    }

    public String getAddress_3() {
        return address_3;
    }

    public void setAddress_3(String address_3) {
        this.address_3 = address_3;
    }

    public String getAddress_3_zipcode() {
        return address_3_zipcode;
    }

    public void setAddress_3_zipcode(String address_3_zipcode) {
        this.address_3_zipcode = address_3_zipcode;
    }

    public String getAddress_3_city() {
        return address_3_city;
    }

    public void setAddress_3_city(String address_3_city) {
        this.address_3_city = address_3_city;
    }

    public String getHp_no() {
        return hp_no;
    }

    public void setHp_no(String hp_no) {
        this.hp_no = hp_no;
    }

    public String getHp_no2() {
        return hp_no2;
    }

    public void setHp_no2(String hp_no2) {
        this.hp_no2 = hp_no2;
    }

    public String getHome_phone_1() {
        return home_phone_1;
    }

    public void setHome_phone_1(String home_phone_1) {
        this.home_phone_1 = home_phone_1;
    }

    public String getOffice_phone_1() {
        return office_phone_1;
    }

    public void setOffice_phone_1(String office_phone_1) {
        this.office_phone_1 = office_phone_1;
    }

    public String getNo_ktp() {
        return no_ktp;
    }

    public void setNo_ktp(String no_ktp) {
        this.no_ktp = no_ktp;
    }

    public String getExpired_date() {
        return expired_date;
    }

    public void setExpired_date(String expired_date) {
        this.expired_date = expired_date;
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

    public Integer getDpd() {
        return dpd;
    }

    public void setDpd(Integer dpd) {
        this.dpd = dpd;
    }

    public String getKesimpulan() {
        return kesimpulan;
    }

    public void setKesimpulan(String kesimpulan) {
        this.kesimpulan = kesimpulan;
    }

    public String getShippingstatus() {
        return shippingstatus;
    }

    public void setShippingstatus(String shippingstatus) {
        this.shippingstatus = shippingstatus;
    }

    public Date getShippingdate() {
        return shippingdate;
    }

    public void setShippingdate(Date shippingdate) {
        this.shippingdate = shippingdate;
    }
}
