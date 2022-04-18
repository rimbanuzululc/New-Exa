
package com.bsi.exa.foundation.dao;

import com.bsi.exa.foundation.dto.RoleMenu;
import io.starlight.db.CommonDAO;
import io.starlight.db.DAO;
import io.vertx.core.Future;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Abdul.Jalil.M
 */
@DAO(config = "db")
public class RoleMenuDAO extends CommonDAO{
    
    public Future<RoleMenu> add(RoleMenu roleMenu){
        
        Future<RoleMenu> result = Future.future();
        
        queryWithParam("select * from somasi_rolemenu where roleid = {{roleid}} and menuid = {{menuid}}", RoleMenu.class,
                "roleid",roleMenu.getRoleId(),"menuid",roleMenu.getMenuId())
      
           .setHandler(ret -> {            
               if(ret.succeeded() && ret.result().size()>0){
                   result.fail("Role id and Menu id had been used");
               }else{
                  
                   insert(roleMenu)
                    .setHandler(ret2 -> {

                        if(ret2.succeeded())
                            result.complete(roleMenu);
                        else
                            result.fail(ret2.cause());
                });
               }
           });
            
        
        return result;
    }
    
     public Future<RoleMenu> update(RoleMenu roleMenu) {
        
       Future<RoleMenu> result = Future.future();
              
        super.update(roleMenu)
                .setHandler(ret -> {

                    if(ret.succeeded())
                        result.complete(roleMenu);
                    else
                        result.complete(roleMenu);
                });

        execWithParam("delete from somasi_rolemenu where roleid = {{roleid}}",
                "roleid", roleMenu.getListRole().get(0).getRoleId())
                .setHandler(ret -> {
                    if(ret.succeeded()){
                        
                        for(RoleMenu role : roleMenu.getListRole()){
                            insert(role)
                            //result.complete();
                            .setHandler(ret2 -> {
                                if(ret2.succeeded()){
                                    
                                }else                                   
                                    result.fail("failed to update");
                            });
                           
                        }
                    }else
                        result.fail("Failed to delete");
                });
        
        return result;
    }
    
    public Future<RoleMenu> delete(RoleMenu roleMenu){
    
        Future<RoleMenu> result = Future.future();
        
        super.delete(roleMenu)
            .setHandler(ret5 -> {

                if (ret5.succeeded())
                    result.complete(roleMenu);
                else
                    result.fail(ret5.cause());
            });
                                    
        
        return result;
    }
    
    public Future<RoleMenu> getById(int roleId){
        RoleMenu roleMenu = new RoleMenu();
        roleMenu.setRoleId(roleId);
        return selectOne(roleMenu);              
    }

    public Future<List<RoleMenu>> listAll(){
        
        return query("select * from somasi_rolemenu", RoleMenu.class);
    }
    
    public Future<List<RoleMenu>> listByRoleId(int roleId){
    
        return queryWithParam("select * from somasi_rolemenu where roleid = {{roleid}}", RoleMenu.class, "roleid",roleId);
    }
    
}
