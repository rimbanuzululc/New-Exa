/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.services;

import com.bsi.exa.foundation.dao.ConfirmAgentPosDAO;
import com.bsi.exa.foundation.dao.KonsumenAggreDAO;
import com.bsi.exa.foundation.dto.DetailDebitur;
import com.bsi.exa.foundation.dto.KonsumenAggrement;
import com.bsi.exa.foundation.dto.ListSomasi;
import com.bsi.exa.foundation.service.KonsumenAggrementService;
import io.starlight.AutoWired;
import io.starlight.Service;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author hp
 */

@Service
public class KonsumenAggreServiceImpl implements KonsumenAggrementService {

    @AutoWired
    KonsumenAggreDAO  aggreDAO;
    
    @AutoWired
    ConfirmAgentPosDAO confirmDAO;
    
    @Override
    public Future<KonsumenAggrement> add(KonsumenAggrement aggremnt) {
        return aggreDAO.add(aggremnt);
    }
    
    @Override
    public Future<KonsumenAggrement> update(KonsumenAggrement aggremnt) {
        return aggreDAO.update(aggremnt);
    }
    
    @Override
    public Future<KonsumenAggrement> getById (int id) {
        return aggreDAO.getByIds(id);
    }

    @Override
    public Future<List<KonsumenAggrement>> list() {
        return aggreDAO.list();
    }

    @Override
    public Future<List<KonsumenAggrement>> search(String param, String value, String startDate, String endDate, int page) {
        return aggreDAO.search(param, value, startDate, endDate, page);
    }

    @Override
    public Future<DetailDebitur> getByNoAggrement(String no) {
        Future<DetailDebitur> result = Future.future();
        
        DetailDebitur dd = new DetailDebitur();
        
        aggreDAO.getByNo(no)
                .setHandler(ret -> {
                    
                    dd.setKonsumen(ret.result());
                   
                    confirmDAO.list("DEB")
                      .setHandler(ret2 -> {
                      
                          dd.setStatusDebitur(ret2.result());
                          
                            confirmDAO.list("UNT")
                                .setHandler(ret3 -> {
                      
                                dd.setStatusUnit(ret3.result());
                            });
                                confirmDAO.list("KSP")
                                    .setHandler(ret4 -> {
                      
                                    dd.setKesimpulan(ret4.result());
                                    
                                    result.complete(dd);
                                });
                      });
                    
                });
        return result;
    }

    @Override
    public Future<List<ListSomasi>> listSomasi(int idAgent) {
        
        Future<List<ListSomasi>> result = Future.future();
        
        aggreDAO.listSomasi(idAgent)
                .setHandler(ret ->{
                
                    if (ret.result() != null) {
                        
                        result.complete(ret.result());
                    } else {
                        result.fail(ret.cause());
                    }
                });
        return result;
        
//        return
//        aggreDAO.getKonsumenByIdAgent(idAgent)
//                .compose(konsumen -> {
//                
//                    if (konsumen.get(0) != null) {
//                        
//                        for (int i = 0; i < konsumen.size(); i++) 
//                        {
//                            
//                            listFuture.add(checkStatusSomasi(konsumen.get(i)));
//                        }
//                    } 
//                    return CompositeFuture.join(listFuture);
//        })
//        .compose(ret -> {
//        
//            return aggreDAO.listSomasi(idAgent);
//        })
//        .compose(ret2 -> {
//            
//            return Future.succeededFuture(ret2);
//        });
    }
    
    public Future<KonsumenAggrement> checkStatusSomasi (KonsumenAggrement aggrement) {
        
        Future<KonsumenAggrement> result = Future.future();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Date sp1 = aggrement.getSendSomasi1Date();
        String today = sdf.format(date);
        System.out.println("Status Somasi : "+ aggrement.getType());
        
        if (sp1 != null) {
                    String tglsp1 = sdf.format(sp1);

            try {
                
                Date harini = sdf.parse(today);
                long selisih = harini.getTime() - sp1.getTime();
                System.out.println("Hari ini : "+ today);
                System.out.println("Tanggal SP1 : "+ tglsp1);
                
                long selisihDay = selisih / (24 * 60 * 60 * 1000);
                System.out.println("Selisih : " + selisihDay + "Hari");

                
                if (selisihDay > 5) {
                    aggrement.setType("SOMASI2");
                }
                
            } catch (Exception e) {
                
            }
            
        }
        
        aggreDAO.update(aggrement)
                .setHandler(ret -> {
                    
                    if (ret.result() != null) {
                        result.complete(ret.result());
                    }
                });
        
        return result;
    }

    @Override
    public Future<List<KonsumenAggrement>> history(int idAgent) {
        return aggreDAO.history(idAgent);
    }

    @Override
    public Future<List<ListSomasi>> listSomasiKA(int idAgent) {
        return aggreDAO.listSomasiKA(idAgent);
    }

    @Override
    public Future<List<KonsumenAggrement>> historyKA(int idAgent) {
        return aggreDAO.historyKA(idAgent);
    }
    
    @Override
    public Future<List<KonsumenAggrement>> listUnCompleted() {
        return aggreDAO.listUnCompleted();
    }
    
    @Override
    public Future<List<KonsumenAggrement>> listDebiturAdmin(String userId) {
        return aggreDAO.listDebiturAdmin(userId);
    }
    
    @Override
    public Future<List<KonsumenAggrement>> listDebiturAgentPos(int idAgent) {
        return aggreDAO.listDebiturAgentPos(idAgent);
    }
    
    @Override
    public Future<List<KonsumenAggrement>> listAllDebitur() {
        return aggreDAO.listAllDebitur();
    }
    
}
