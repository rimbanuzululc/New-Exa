/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.dao;

import com.bsi.exa.foundation.dto.ConfirmKAPos;
import io.starlight.db.CommonDAO;
import io.starlight.db.DAO;
import io.vertx.core.Future;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aldy.kurniawan
 */

@DAO(config = "db")
public class KAPosDAO extends CommonDAO{
    
    public Future<ConfirmKAPos> submit (ConfirmKAPos confirm) {
        
        Future<ConfirmKAPos> result = Future.future();
        
        insert(confirm)
                .setHandler(ret -> {
                if (ret.succeeded()) {
                        result.complete(ret.result());
                    } else {
                        result.fail(ret.cause());
                    }
               });
        return result; 
        
    }
    
    public Future<List<ConfirmKAPos>> reportVerifikasi (String userId) {
        
        return queryScriptWihtParam("reportVerifikasi", ConfirmKAPos.class, "id", userId);
    }
    
    public Future<List<ConfirmKAPos>> reportVerifikasiforAdmin (String userId) {
        
        Future<List<ConfirmKAPos>> result = Future.future();
        
        List<ConfirmKAPos> list = new ArrayList<>();
        
        queryScriptWihtParam("reportVerifikasiforAdmin", ConfirmKAPos.class, "userId", userId)
                .setHandler(ret -> {
                    
                    if (ret.succeeded() && ret.result().size() > 0) {
                            
                        for (int i = 0; i < ret.result().size(); i++) {
                            
                            if (ret.result().get(i).getIdConfirmKapos() != null) {
                                
                                list.add(ret.result().get(i));
                            }
                        }
                        
                       result.complete(list);
                        
                    } else {
                        result.fail(ret.cause());
                    }
                    
                });
        return result;
    }
    
    public Future<List<ConfirmKAPos>> reportAllVerifikasi() {
        
        Future<List<ConfirmKAPos>> result = Future.future();
        
        List<ConfirmKAPos> list = new ArrayList<>();
        
        queryScriptWihtParam("reportAllVerifikasi", ConfirmKAPos.class)
                .setHandler(ret -> {
                    
                    if (ret.succeeded() && ret.result().size() > 0) {
                            
                        for (int i = 0; i < ret.result().size(); i++) {
                            
                            if (ret.result().get(i).getIdConfirmKapos() != null) {
                                
                                list.add(ret.result().get(i));
                            }
                        }
                        
                       result.complete(list);
                        
                    } else {
                        result.fail(ret.cause());
                    }
                    
                });
        return result;
    }
}
