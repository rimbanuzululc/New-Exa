/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.dao;

import com.bsi.exa.foundation.dto.District;
import io.starlight.db.CommonDAO;
import io.starlight.db.DAO;
import io.vertx.core.Future;
import java.util.List;

/**
 *
 * @author hp
 */
@DAO(config = "db")
public class DistrictDAO extends CommonDAO {
    
    public Future<List<District>> listByCity (int id) {
        return queryScriptWihtParam("listByCity", District.class, "id", id);
    }
    
    public Future<List<District>> listDistrictByKodeArea (String idMapping) {
        Future<List<District>> result = Future.future();
        queryScriptWihtParam("listDistrictByKodeArea", District.class , "idMapping", idMapping)
                .setHandler(ret -> {
                    
                    if (ret.succeeded()) {
                        result.complete(ret.result());
                    } else {
                        result.complete(ret.result());
                    }
                    
                });
        return result;
    }
    
}
