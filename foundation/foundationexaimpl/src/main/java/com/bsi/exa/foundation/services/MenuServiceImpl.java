
package com.bsi.exa.foundation.services;

import com.bsi.exa.foundation.dao.MenuDAO;
import com.bsi.exa.foundation.dto.Menu;
import com.bsi.exa.foundation.service.MenuService;
import io.starlight.AutoWired;
import io.starlight.Service;
import io.vertx.core.Future;
import java.util.List;

/**
 *
 * @author anisa.pebriani
 */
@Service
public class MenuServiceImpl implements MenuService{

    @AutoWired
    protected MenuDAO dao;
    
    @Override
    public Future<Menu> add(Menu menu) {
        return dao.add(menu);
    }

    @Override
    public Future<Menu> update(Menu menu) {
        return dao.update(menu);
    }

    @Override
    public Future<Menu> delete(int MenuId) {
         Menu menu = new Menu();
        menu.setMenuId(MenuId);
        return dao.delete(menu);
    }

    @Override
    public Future<Menu> getId(int menuId) {
        return dao.getById(menuId);
    }

    @Override
    public Future<List<Menu>> search(String filter, int parentId, int page) {
       return dao.search(filter,parentId,page);
    }

    @Override
    public Future<List<Menu>> listAll() {
         return dao.listAll();
    }

   
}
