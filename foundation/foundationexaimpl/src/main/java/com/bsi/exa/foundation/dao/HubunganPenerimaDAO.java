/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.dao;

import com.bsi.exa.foundation.dto.HubunganPenerima;
import io.starlight.db.CommonDAO;
import io.starlight.db.DAO;
import io.vertx.core.Future;

/**
 *
 * @author hp
 */
@DAO(config = "db")
public class HubunganPenerimaDAO extends CommonDAO{
    
    public Future<HubunganPenerima> getByCode (String code) {
        
        Future<HubunganPenerima> result = Future.future();
        
        queryScriptWihtParam("getByCode", HubunganPenerima.class, "code", code)
            .setHandler(ret -> {
                
                if (ret.succeeded() && ret.result() != null && ret.result().size() > 0) {
                    result.complete(ret.result().get(0));
                } else {
                    result.complete(null);
                }
                
            });
        return result;
    }
    
}
