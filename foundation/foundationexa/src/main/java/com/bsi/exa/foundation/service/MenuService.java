package com.bsi.exa.foundation.service;

import com.bsi.exa.foundation.dto.Menu;
import io.vertx.core.Future;
import java.util.List;

/**
 *
 * @author Abdul.Jalil.M
 */
public interface MenuService {
    
    Future<Menu> add(Menu menu);
    Future<Menu> update(Menu Menu);
    Future<Menu> delete(int MenuId);
    Future<Menu> getId(int MenuId);
    Future<List<Menu>> search(String filter, int parentId, int page);
    Future<List<Menu>> listAll();
}
