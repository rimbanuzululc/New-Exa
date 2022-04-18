
package com.bsi.exa.foundation.dao;

import com.bsi.exa.foundation.dto.Menu;
import io.starlight.db.CommonDAO;
import io.starlight.db.DAO;
import io.vertx.core.Future;
import java.util.List;

/**
 *
 * @author Abdul.Jalil.M
 */
@DAO(config = "db")
public class MenuDAO extends CommonDAO{
    
    public Future<Menu> add(Menu menu){
        
        Future<Menu> result = Future.future();
              
            insert(menu)
                .setHandler(ret -> {

                    if(ret.succeeded())
                        result.complete(menu);
                    else
                        result.fail(ret.cause());
                });
        
        return result;
    }
    
     public Future<Menu> update(Menu menu) {
        
          Future<Menu> result = Future.future();
              
            super.update(menu)
                .setHandler(ret -> {

                    if(ret.succeeded())
                        result.complete(menu);
                    else
                        result.fail(ret.cause());
                });
        
        return result;
    }
    
    public Future<Menu> delete(Menu menu) {
        
        Future<Menu> result = Future.future();
              
            super.delete(menu)
                .setHandler(ret -> {

                    if(ret.succeeded())
                        result.complete(menu);
                    else
                        result.fail(ret.cause());
                });
        
        return result;
    }
    
    public Future<Menu> getById(int menuId) {
        
        Menu Menu=new Menu();
        Menu.setMenuId(menuId);
        
        return selectOne(Menu);
    }
        
    public Future<List<Menu>> search(String filter, int parentId, int page) {
       
        int rowPerPage = 10;
        int start = (page - 1) * rowPerPage;
        
        String parentFilter = "";
        
          if (parentId > -1)
            parentFilter = " and parentid =  " + parentId + " ";
                   
        return queryScriptWihtParam("searchMenu", Menu.class, "titleFilter", filter, "parentId", parentFilter, "start", start, "rowPerPage", rowPerPage);
    }
    
    public Future<List<Menu>> listAll() {
       
        return queryScriptWihtParam("listAllMenu", Menu.class);
    }
    
    
    
}
