/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.dao;

import com.bsi.exa.foundation.dto.Konsumen;
import io.vertx.core.Future;
import io.starlight.db.CommonDAO;
import io.starlight.db.DAO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 *
 * @author hp
 */
@DAO(config = "db")
public class KonsumenDAO extends CommonDAO{
    
    public Future<Konsumen> add (Konsumen konsumen) {
        
        Future<Konsumen> result = Future.future();
        
        Date date = new Date();
        konsumen.setUpload_date(date);
        
        insert(konsumen)
                .setHandler(ret -> {
                if (ret.succeeded()) {
                        result.complete(konsumen);
                    } else {
                        result.fail(ret.cause());
                    }
               });
        return result; 
    }
    
    public Future<List<Konsumen>> list () {
        return queryScript("listKonsumen", Konsumen.class) 
                .compose(ret -> {
                
                    return Future.succeededFuture(ret);
                });
    }
    
    public Future<List<Konsumen>> search (String addParam, String filter, int page) {
        
        int rowPerPage = 10;
        int start = (page - 1) * rowPerPage;
        
        if (addParam.equalsIgnoreCase("NamaDebitur")) {
            
            return queryScriptWihtParam("seacrhNama", Konsumen.class, "param", filter, "start", start, "rowPerPage", rowPerPage);
            
        } else  if (addParam.equalsIgnoreCase("REFNumber")){
            
            return queryScriptWihtParam("seacrhRef", Konsumen.class, "param", filter, "start", start, "rowPerPage", rowPerPage);
            
        } else if (addParam.equalsIgnoreCase("CardNumber")) {
            
            return queryScriptWihtParam("seacrhCard", Konsumen.class, "param", filter, "start", start, "rowPerPage", rowPerPage);
            
        } else if (addParam.equalsIgnoreCase("StatusPengiriman")) {
            
            return queryScriptWihtParam("seacrhStatus", Konsumen.class, "param", filter, "start", start, "rowPerPage", rowPerPage);
            
        } else {
            List<Konsumen> konsumen = new ArrayList<>();
            return Future.succeededFuture(konsumen);
        }
    }
    
    public Future<List<Konsumen>> getById (int id) {
        
        return queryScriptWihtParam("getById", Konsumen.class, "id", id);
    }
    
}
