package io.starlight.test.config.client;

import io.starlight.Component;
import io.starlight.Config;

/**
 *
 * @author denny
 */
@Component
public class TestComponent {
    
    @Config("dataInt")
    protected int dataInt;
    
    @Config("configInt")
    protected int configInt;
    
    @Config("configString")
    protected String configString;
    
    @Config("configBool")
    protected boolean configBool;

    public int getConfigInt() {
        return configInt;
    }

    public void setConfigInt(int configInt) {
        this.configInt = configInt;
    }

    public String getConfigString() {
        return configString;
    }

    public void setConfigString(String configString) {
        this.configString = configString;
    }

    public boolean isConfigBool() {
        return configBool;
    }

    public void setConfigBool(boolean configBool) {
        this.configBool = configBool;
    }

    public int getDataInt() {
        return dataInt;
    }

    public void setDataInt(int dataInt) {
        this.dataInt = dataInt;
    }

    
    
}
