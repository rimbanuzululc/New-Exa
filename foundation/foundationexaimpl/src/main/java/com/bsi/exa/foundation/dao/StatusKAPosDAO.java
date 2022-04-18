/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.dao;

import com.bsi.exa.foundation.dto.StatusKAPos;
import io.starlight.db.CommonDAO;
import io.starlight.db.DAO;
import io.vertx.core.Future;
import java.util.List;

/**
 *
 * @author hp
 */
@DAO(config = "db")
public class StatusKAPosDAO extends CommonDAO{
    
    public Future<StatusKAPos> getByCode (String code) {
        
        Future<StatusKAPos> result = Future.future();
        
        queryScriptWihtParam("getByCode", StatusKAPos.class, "code", code)
            .setHandler(ret -> {
                
                if (ret.succeeded()) {
                    
                    result.complete(ret.result().get(0));
                    
                } else {
                    
                    result.complete(null);
                }
                
            });
        return result;
    }
    
    public Future<List<StatusKAPos>> listAll() {
       
        return queryScriptWihtParam("listAll", StatusKAPos.class);
    }
    
}
