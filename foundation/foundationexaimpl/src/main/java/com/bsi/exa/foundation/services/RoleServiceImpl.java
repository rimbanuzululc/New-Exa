
package com.bsi.exa.foundation.services;

import com.bsi.exa.foundation.dao.RoleDAO;
import com.bsi.exa.foundation.dao.RoleMenuDAO;
import com.bsi.exa.foundation.dto.Role;
import com.bsi.exa.foundation.service.RoleService;
import io.starlight.AutoWired;
import io.starlight.Service;
import io.vertx.core.Future;
import java.util.List;

/**
 *
 * @author anisa.pebriani
 */
@Service
public class RoleServiceImpl implements RoleService{

    @AutoWired
    protected RoleDAO dao;
    
    @Override
    public Future<Role> add(Role role) {
        return dao.add(role);
    }

    @Override
    public Future<Role> update(Role role) {
       return dao.update(role);
    }

    @Override
    public Future<Role> delete(int roleId) {
        Role role = new Role();
        role.setRoleId(roleId);
      
        return dao.delete(role);
    }

    @Override
    public Future<Role> getId(int roleId) {
        return dao.getById(roleId);
    }

    @Override
    public Future<List<Role>> search(String filter, int page) {
        return dao.search(filter, page);
    }

    @Override
    public Future<List<Role>> listAll() {
         return dao.listAll();
    }
}
