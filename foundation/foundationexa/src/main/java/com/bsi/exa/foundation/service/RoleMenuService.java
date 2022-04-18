
package com.bsi.exa.foundation.service;

import com.bsi.exa.foundation.dto.RoleMenu;
import io.vertx.core.Future;
import java.util.List;


public interface RoleMenuService {
    
    Future<RoleMenu> add(RoleMenu role);
    Future<RoleMenu> update(RoleMenu role);
    Future<RoleMenu> delete(int userId);
    Future<RoleMenu> getId(int userId);
    Future<List<RoleMenu>> listAll();
    Future<List<RoleMenu>> listByRoleId(int roleId);

}
