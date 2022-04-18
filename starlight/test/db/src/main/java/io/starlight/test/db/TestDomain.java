package io.starlight.test.db;

import io.starlight.db.AutoKey;
import io.starlight.db.Column;
import io.starlight.db.Computed;
import io.starlight.db.Table;

/**
 *
 * @author denny
 */
@Table("tbl_test2")
public class TestDomain {
    
    @AutoKey
    protected Integer dataInt;
    protected String dataString;
    
    @Column("yesno")
    protected Boolean dataBool;
    
    @Computed
    protected String hello;

    public Integer getDataInt() {
        return dataInt;
    }

    public void setDataInt(Integer dataInt) {
        this.dataInt = dataInt;
    }

    public String getDataString() {
        return dataString;
    }

    public void setDataString(String dataString) {
        this.dataString = dataString;
    }

    public Boolean isDataBool() {
        return dataBool;
    }

    public void setDataBool(Boolean dataBool) {
        this.dataBool = dataBool;
    }

    public String getHello() {
        return hello;
    }

    public void setHello(String hello) {
        this.hello = hello;
    }

    @Override
    public String toString() {
        return "TestDomain{" + "dataInt=" + dataInt + ", dataString=" + dataString + ", dataBool=" + dataBool + ", hello=" + hello + '}';
    }
}
