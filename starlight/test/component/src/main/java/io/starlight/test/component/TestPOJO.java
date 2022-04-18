package io.starlight.test.component;

import java.util.List;

/**
 *
 * @author denny
 */
public class TestPOJO {

    protected int dataInt;
    protected boolean dataBool;
    protected String dataString;
    
    protected List<SubPOJO> subList;

    public TestPOJO() {
    }
    
    public TestPOJO(int dataInt, boolean dataBool, String dataString) {
        this.dataInt = dataInt;
        this.dataBool = dataBool;
        this.dataString = dataString;
    }
    
    public int getDataInt() {
        return dataInt;
    }

    public void setDataInt(int dataInt) {
        this.dataInt = dataInt;
    }

    public boolean isDataBool() {
        return dataBool;
    }

    public void setDataBool(boolean dataBool) {
        this.dataBool = dataBool;
    }

    public String getDataString() {
        return dataString;
    }

    public void setDataString(String dataString) {
        this.dataString = dataString;
    }

    public List<SubPOJO> getSubList() {
        return subList;
    }

    public void setSubList(List<SubPOJO> subList) {
        this.subList = subList;
    }
    
    
}
