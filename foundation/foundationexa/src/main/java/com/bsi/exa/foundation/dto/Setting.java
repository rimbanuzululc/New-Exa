package com.bsi.exa.foundation.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.starlight.db.Key;
import io.starlight.db.Table;

/**
 *
 * @author anisa.pebriani
 */
@Table("somasi_setting")
public class Setting {
    
    @Key
    protected String code;
    protected String name;
    protected String description;       
    protected String type;
    protected String value;
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @JsonIgnore
    public Integer getIntValue() {
        
        return getIntValue(null);
    }
    
    @JsonIgnore
    public Integer getIntValue(Integer defaultValue) {
        
        Integer result = null;
        
        try {
            result = Integer.parseInt(value);
        }
        catch (Exception e) {
            
            result = defaultValue;
        }
        
        return result;
    }

    @JsonIgnore
    public Boolean getBoolValue() {
        
        return getBoolValue(null);
    }
    
    @JsonIgnore
    public Boolean getBoolValue(Boolean defaultValue) {
        
        Boolean result = null;
        
        try {
            result = Boolean.parseBoolean(value);
        }
        catch (Exception e) {
            
            result = defaultValue;
        }
        
        return result;
    }
}
