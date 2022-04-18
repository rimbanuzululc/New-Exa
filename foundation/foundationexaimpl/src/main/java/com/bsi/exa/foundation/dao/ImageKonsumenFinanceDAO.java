/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.dao;

import com.bsi.exa.foundation.dto.ImageKonsumenFinance;
import io.starlight.db.CommonDAO;
import io.starlight.db.DAO;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import java.util.Date;
import java.util.List;

/**
 *
 * @author hp
 */
@DAO(config = "db")
public class ImageKonsumenFinanceDAO extends CommonDAO {
    
    public Future<ImageKonsumenFinance> add (ImageKonsumenFinance finance) {
        Date date = new Date();
        finance.setCreated(date);
        
        Future<ImageKonsumenFinance> result = Future.future();
        
        insert(finance)
                .setHandler(ret -> {
                    
                    if (ret.succeeded()) {
                        result.complete(ret.result());
                    } else {
                        result.fail(ret.cause());
                    }
                });
        return result;
    }
    
    public Future<ImageKonsumenFinance> update (ImageKonsumenFinance finance) {
        
        Date date = new Date();
        
        Future<ImageKonsumenFinance> result = Future.future();
        
        queryScriptSingleWithParam("checkKonsumenImage", ImageKonsumenFinance.class, "konsumenId", finance.getKonsumenid(), "imageName", finance.getImageName())
                .setHandler(ret ->{
                    if (ret.succeeded() && ret.result() != null){
                        finance.setIdKonsumenFinance(ret.result().getIdKonsumenFinance());
                        finance.setModifiedDate(date);
                        super.update(finance)
                                .setHandler(ret2 -> {
                    
                                    if (ret.succeeded()) {
                                        result.complete(ret2.result());
                                    } else {
                                        result.fail(ret2.cause());
                                    }
                                });
                    }
                });
        
        return result;
    }
    
    public Future<ImageKonsumenFinance> checkImage(Integer id) {
        Future<ImageKonsumenFinance> result = Future.future();
        
        queryScriptWihtParam("checkImage", ImageKonsumenFinance.class, "id", id)
            .setHandler(ret -> {
                
                if (ret.result() != null && ret.result().size() > 0) {
                    result.complete(ret.result().get(0));
                } else {
                    result.complete(null);
                }
            });
        return result;
    }
    
        
        public Future<List<ImageKonsumenFinance>> listbykonsumen(int id){
            return queryScriptWihtParam("listbykonsumen", ImageKonsumenFinance.class, "id", id);
        }
    
}
