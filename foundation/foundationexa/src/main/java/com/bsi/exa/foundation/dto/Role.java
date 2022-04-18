
package com.bsi.exa.foundation.dto;

import io.starlight.db.AutoKey;
import io.starlight.db.Computed;
import io.starlight.db.Table;

/**
 *
 * @author Abddul.Jalil.M
 */
@Table("somasi_role")
public class Role {
    @AutoKey
    protected Integer roleId;
    protected String name;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
  
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
