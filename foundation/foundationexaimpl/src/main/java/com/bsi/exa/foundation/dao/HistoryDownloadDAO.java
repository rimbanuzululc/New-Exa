/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.dao;

import com.bsi.exa.foundation.dto.HistoryDownload;
import io.starlight.db.CommonDAO;
import io.starlight.db.DAO;
import io.vertx.core.Future;

/**
 *
 * @author hp
 */
@DAO(config = "db")
public class HistoryDownloadDAO extends CommonDAO{
    
    public Future<HistoryDownload> add (HistoryDownload download) {
        
        Future<HistoryDownload> result = Future.future();
        
        insert(download)
            .setHandler(ret -> {
                
                if (ret.succeeded()) {
                    result.complete(ret.result());
                } else {
                    result.complete();
                }
                
            });
        return result;
    }
    
    public Future<HistoryDownload> update (HistoryDownload download) {
        
        
        Future<HistoryDownload> result = Future.future();
        
        upsert(download)
            .setHandler(ret -> {
                
                if (ret.succeeded()) {
                    result.complete(ret.result());
                } else {
                    result.complete();
                }
                
            });
        return result;
    }
}
