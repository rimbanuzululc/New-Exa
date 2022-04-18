package io.starlight.test.component;

/**
 *
 * @author denny
 */
public class SubPOJO {

    protected int subInt;
    protected String subString;

    public int getSubInt() {
        return subInt;
    }

    public void setSubInt(int subInt) {
        this.subInt = subInt;
    }

    public String getSubString() {
        return subString;
    }

    public void setSubString(String subString) {
        this.subString = subString;
    }

    public SubPOJO() {
    }

    public SubPOJO(int subInt, String subString) {
        this.subInt = subInt;
        this.subString = subString;
    }
    
    
}
