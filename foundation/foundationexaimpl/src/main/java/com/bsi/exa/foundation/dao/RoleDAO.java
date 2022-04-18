
package com.bsi.exa.foundation.dao;

import com.bsi.exa.foundation.dto.Role;
import io.starlight.db.CommonDAO;
import io.starlight.db.DAO;
import io.vertx.core.Future;
import java.util.List;

/**
 *
 * @author Abdul.Jalil.M
 */
@DAO(config = "db")
public class RoleDAO extends CommonDAO{
    
    public Future<Role> add(Role role){
        
        Future<Role> result = Future.future();
              
            insert(role)
                .setHandler(ret -> {

                    if(ret.succeeded())
                        result.complete(role);
                    else
                        result.fail(ret.cause());
                });
        
        return result;
    }
    
     public Future<Role> update(Role role) {
        
       Future<Role> result = Future.future();
         
         execWithParam("update somasi_role set name = {{name}} where roleid = {{roleid}}",
                 "name",role.getName(),"roleid",role.getRoleId())        
                 .setHandler(ret -> {
                     if(ret.succeeded()){
                         result.complete(role);
                     }else
                         result.fail("Failed to update");
                 });             
        
        return result;
    }
    
    public Future<Role> delete(Role role){
    
        Future<Role> result = Future.future();
        
        super.delete(role)
            .setHandler(ret5 -> {

                if (ret5.succeeded())
                    result.complete(role);
                else
                    result.fail(ret5.cause());
            });
                                    
        
        return result;
    }
    
    public Future<Role> getById(int roleId){
        Role role = new Role();
        role.setRoleId(roleId);
        return selectOne(role);              
    }
    
    public Future<List<Role>> search(String filter,int page){
        int rowPerPage = 10;
        int start = (page - 1) * rowPerPage;
        
        return queryScriptWihtParam("searchRole", Role.class, "filter", filter, "start", start, "rowPerPage", rowPerPage);
    }
    
    public Future<List<Role>> listAll(){
        
        return queryScriptWihtParam("listRole", Role.class);
    }
    
}
