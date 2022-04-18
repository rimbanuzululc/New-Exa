/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.dao;

import com.bsi.exa.foundation.dto.Assign;
import io.starlight.db.CommonDAO;
import io.starlight.db.DAO;
import io.vertx.core.Future;
import java.util.Date;
import java.util.List;

/**
 *
 * @author hp
 */
@DAO(config = "db")
public class AssignDAO extends CommonDAO {
    
    public Future<Assign> add (Assign assign) {
        
        Future<Assign> result = Future.future();
        
        insert(assign)
                .setHandler(ret -> {
                if (ret.succeeded()) {
                        result.complete(ret.result());
                    } else {
                        result.fail(ret.cause());
                    }
               });
        return result; 
        
    }
    
}
