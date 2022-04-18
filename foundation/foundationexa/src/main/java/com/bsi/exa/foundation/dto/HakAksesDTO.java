/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.starlight.db.AutoKey;
import io.starlight.db.Computed;
import io.starlight.db.Table;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class HakAksesDTO {
    
    
    protected Integer id;
    protected String title;
    protected String icon;
    @Computed
    public List<SubMenu> subMenu;

    
     public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<SubMenu> getSubMenu() {
        return subMenu;
    }

    public void setSubMenu(List<SubMenu> subMenu) {
        this.subMenu = subMenu;
    }

   

    
    
    
}
