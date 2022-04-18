
package com.bsi.exa.foundation.services;

import com.bsi.exa.foundation.dao.RoleMenuDAO;
import com.bsi.exa.foundation.dto.RoleMenu;
import com.bsi.exa.foundation.service.RoleMenuService;
import io.starlight.AutoWired;
import io.starlight.Service;
import io.vertx.core.Future;
import java.util.List;

/**
 *
 * @author anisa.pebriani
 */
@Service
public class RoleMenuServiceImpl implements RoleMenuService{

    @AutoWired
    protected RoleMenuDAO dao;
    
    @Override
    public Future<RoleMenu> add(RoleMenu roleMenu) {
        return dao.add(roleMenu);
    }

    @Override
    public Future<RoleMenu> update(RoleMenu roleMenu) {
       return dao.update(roleMenu);
    }

    @Override
    public Future<RoleMenu> delete(int roleId) {
        RoleMenu roleMenu = new RoleMenu();
        roleMenu.setRoleId(roleId);
        return dao.delete(roleMenu);
    }

    @Override
    public Future<RoleMenu> getId(int roleId) {
        return dao.getById(roleId);
    }

    @Override
    public Future<List<RoleMenu>> listAll() {
         return dao.listAll();
    }

    @Override
    public Future<List<RoleMenu>> listByRoleId(int roleId) {
        return dao.listByRoleId(roleId);
    }
}
