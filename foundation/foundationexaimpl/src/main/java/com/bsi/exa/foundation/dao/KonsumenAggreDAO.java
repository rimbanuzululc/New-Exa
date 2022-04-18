/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.dao;

import com.bsi.exa.foundation.dto.KonsumenAggrement;
import com.bsi.exa.foundation.dto.ListSomasi;
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
public class KonsumenAggreDAO extends CommonDAO{
    
    public Future<KonsumenAggrement> add (KonsumenAggrement konsumen) {
        
        Future<KonsumenAggrement> result = Future.future();
        
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
    
    public Future<KonsumenAggrement> update (KonsumenAggrement konsumen) {
        
        Future<KonsumenAggrement> result = Future.future();
        
        Date date = new Date();
        konsumen.setLast_modify(date);
        
        super.upsert(konsumen)
                .setHandler(ret -> {
                if (ret.succeeded()) {
                        result.complete(konsumen);
                    } else {
                        result.fail(ret.cause());
                    }
               });
        return result; 
    }
    
    public Future<List<KonsumenAggrement>> list () {
        return queryScript("listKonsumen", KonsumenAggrement.class) 
                .compose(ret -> {
                
                    return Future.succeededFuture(ret);
                });
    }
    
    public Future<List<KonsumenAggrement>> getById (int id) {
        
        return queryScriptWihtParam("getbyId", KonsumenAggrement.class, "id", id);
    }
    
    public Future<KonsumenAggrement> getByIds (int id) {
        
        KonsumenAggrement konsumen = new KonsumenAggrement();
        konsumen.setKonsumenId(id);
        
        return selectOne(konsumen);
    }
    
    public Future<List<KonsumenAggrement>> search (String param, String filter, String startDate, String endDate, int page) {
        
        int rowPerPage = 10;
        int start = (page - 1) * rowPerPage;
        String addParam = "";
        
        if ("NamaDebitur".equalsIgnoreCase(param)) {
            
            addParam += "where k.namadebitur ilike '%"+filter+"%' ";
            if(!"".equals(startDate) || !"".equals(endDate)) {
                addParam += "and (k.upload_date BETWEEN '" + startDate + "' AND '" + endDate +"' )"; 
            }
            
        } else if ("NoAgreement".equalsIgnoreCase(param)){
            
            addParam += "where k.noaggrement ilike '%"+filter+"%' ";
            
            if(!"".equals(startDate) || !"".equals(endDate)) {
                addParam += "and (k.upload_date BETWEEN '" + startDate + "' AND '" + endDate +"' )"; 
            }
            
        } else if ("NomorPolisi".equalsIgnoreCase(param)) {
            
            addParam += "where k.nomorpolisi ilike '%"+filter+"%' ";  
            
            if(!"".equals(startDate) || !"".equals(endDate)) {
                addParam += "and (k.upload_date BETWEEN '" + startDate + "' AND '" + endDate +"' )"; 
            }
        } else if ("TahunKendaraan".equalsIgnoreCase(param)) {
            
            addParam += "where k.tahunkendaraan ilike '%"+filter+"%' ";  
            
            if(!"".equals(startDate) || !"".equals(endDate)) {
                addParam += "and (k.upload_date BETWEEN '" + startDate + "' AND '" + endDate +"' )"; 
            }
            
        } else if ("StatusSomasi".equalsIgnoreCase(param)) {
            
            addParam += "where k.statusSomasi ilike '%"+filter+"%' ";
            
            if(!"".equals(startDate) || !"".equals(endDate)) {
                addParam += "and (k.upload_date BETWEEN '" + startDate + "' AND '" + endDate +"' )"; 
            }
        } else {
            addParam += "where k.upload_date BETWEEN '" + startDate + "' AND '" + endDate +"'"; 
        }
        
        System.out.println("addParam : " + addParam+", OFFSET" + start);
        
        return queryScriptWihtParam("searchFinance", KonsumenAggrement.class, "addParam", addParam, "start", start);
                        
    }
    
    
    public Future<KonsumenAggrement> getByNo (String no) {
        
        Future<KonsumenAggrement> result = Future.future();
        
        queryScriptWihtParam("getByNa", KonsumenAggrement.class, "no", no)
                .setHandler(ret -> {
                
                    if (ret.succeeded() ) {
                        result.complete(ret.result().get(0));
                        
                    } else {
                        result.complete();
                    }
                
                });
        return result;
    }
    
    public Future<List<ListSomasi>> listSomasi (int idAgent) {
        return queryScriptWihtParam("listSomasi", ListSomasi.class, "idAgent", idAgent);
    }
    
    public Future<List<KonsumenAggrement>> getKonsumenByIdAgent (int idAgent) {
        return queryScriptWihtParam("getKonsumenByIdAgent", KonsumenAggrement.class, "idAgent", idAgent);
    }
    
    public Future<List<KonsumenAggrement>> history (int idAgent) {
        return queryScriptWihtParam("history", KonsumenAggrement.class, "idAgent", idAgent);
    }
    
    public Future<List<ListSomasi>> listSomasiKA (int idAgent) {
        return queryScriptWihtParam("listSomasiKA", ListSomasi.class, "idAgent", idAgent);
    }
    
    public Future<List<KonsumenAggrement>> historyKA (int idAgent) {
        return queryScriptWihtParam("historyKA", KonsumenAggrement.class, "idAgent", idAgent);
    }
    
    public Future<List<KonsumenAggrement>> listUnCompleted () {
        return queryScript("listUnCompleted", KonsumenAggrement.class);
    }
    
    public Future<List<KonsumenAggrement>> listDebiturAdmin (String userId) {
        return queryScriptWihtParam("listDebiturAdmin", KonsumenAggrement.class, "userId", userId);
    }
    
    public Future<List<KonsumenAggrement>> listDebiturAgentPos (int idAgent) {
        return queryScriptWihtParam("listDebiturAgentPos", KonsumenAggrement.class, "id", idAgent);
    }
    
    public Future<List<KonsumenAggrement>> listAllDebitur() {
        return queryScript("listAllDebitur", KonsumenAggrement.class);
    }
    
}
