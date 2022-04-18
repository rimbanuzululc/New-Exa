/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.dao;

import com.bsi.exa.foundation.dto.HistoryStatus;
import com.bsi.exa.foundation.dto.KonsumenAggrement;
import com.bsi.exa.foundation.dto.ConfirmAgentPos;
import com.bsi.exa.foundation.dto.StatusSomasi2;
import com.bsi.exa.foundation.dto.SubmitSomasi;
import io.starlight.AutoWired;
import io.starlight.db.CommonDAO;
import io.starlight.db.DAO;
import io.vertx.core.Future;
import java.util.Date;

/**
 *
 * @author hp
 */
@DAO(config = "db")
public class HistoryStatusDAO extends CommonDAO{
    
    @AutoWired
    KonsumenAggreDAO aggreDAO;
    
    @AutoWired
    HubunganPenerimaDAO penerimaDAO;
    
    @AutoWired
    StatusKAPosDAO kaposDAO;
    
    public Future<String> history (String code, int konsumenId, int idAgentPos) {
        
        Future<String> result = Future.future();
        HistoryStatus historyStatus = new HistoryStatus();
        KonsumenAggrement aggrement = new KonsumenAggrement();
        
        Date date = new Date();
        
        historyStatus.setIdAgentPos(idAgentPos);
        historyStatus.setKonsumenId(konsumenId);
        historyStatus.setCreated(date);
        
            getByCodeSomasi1(code)
            .compose(somasi1 -> {

                if (somasi1 != null) {
                    historyStatus.setIdStatus1(somasi1.getIdStatus1());
                } 

                return getByCodeSomasi2(code);
            })
            .compose(somasi2 -> {

                if (somasi2 != null) {
                    historyStatus.setIdStatus2(somasi2.getIdStatus2());
                }

                return aggreDAO.getById(konsumenId);
            })
            .compose(konsumen -> {
                
                  if (konsumen.get(0) != null) {
                      aggrement.setStatusSomasi(code);
                    }
                  return aggreDAO.update(aggrement);
            })
            .compose(retx -> {
                
                return add(historyStatus);
            })
            .compose(add -> {
                
                if (add != null) {
                    result.complete("Succes!");
                } else {
                    result.fail("Gagal Add");
                }
                return Future.succeededFuture(result);
            });
            
        return result;
    }
    
    public Future<ConfirmAgentPos> getByCodeSomasi1 (String code) {
        Future<ConfirmAgentPos> result = Future.future();
        
        queryScriptWihtParam("getByCodeSomasi1", ConfirmAgentPos.class, "code", code)
                .setHandler(ret -> {
                    
                    if (ret.succeeded() && ret.result().size() > 0) {
                        result.complete(ret.result().get(0));
                    } else {
                        result.complete();
                    }
                
                });
        return result;
    }
    
    public Future<StatusSomasi2> getByCodeSomasi2 (String code) {
        Future<StatusSomasi2> result = Future.future();
        
        queryScriptWihtParam("getByCodeSomasi2", StatusSomasi2.class, "code", code)
                .setHandler(ret -> {
                    
                    if (ret.succeeded() && ret.result().size() > 0) {
                        result.complete(ret.result().get(0));
                    } else {
                        result.complete();
                    }
                
                });
        return result;
    }
    
    public Future<HistoryStatus> add (HistoryStatus historyStatus) {
        
        Future<HistoryStatus> result = Future.future();
        
        insert(historyStatus)
                .setHandler(ret -> {
                    
                    if (ret.succeeded()) {
                        
                        result.complete(ret.result());
                    } else {
                        result.complete();
                    }
                });
        return result;
    }
    
    public Future<String> submit (SubmitSomasi somasi) {
        
        
        Future<String> result = Future.future();
        HistoryStatus historyStatus = new HistoryStatus();
        ConfirmAgentPos status1 = new ConfirmAgentPos();
        
        Date date = new Date();
        
        historyStatus.setIdAgentPos(somasi.getIdAgentPos());
        historyStatus.setKonsumenId(somasi.getKonsumenId());
        historyStatus.setCreated(date);
        historyStatus.setPenerimaSomasi(somasi.getPenerimaSomasi());
        
        if (somasi.getHubunganPenerima() == null) {
            somasi.setHubunganPenerima("OKE");
        }
        
            getByCodeSomasi1(somasi.getCode())
            .compose(somasi1 -> {

                if (somasi1 != null) {
                    historyStatus.setIdStatus1(somasi1.getIdStatus1());
                    status1.setDescription(somasi1.getDescription());
                } 

                return getByCodeSomasi2(somasi.getCode());
            })
            .compose(somasi2 -> {

                if (somasi2 != null) {
                    historyStatus.setIdStatus2(somasi2.getIdStatus2());
                    status1.setDescription(somasi2.getDescription());
                }
                
                return penerimaDAO.getByCode(somasi.getHubunganPenerima());
            })
            .compose(hp -> {
                
                if (hp != null) {
                    historyStatus.setHubunganPenerima(somasi.getHubunganPenerima());
                }
                
                return aggreDAO.getById(somasi.getKonsumenId());
            })
            .compose(konsumen -> {
                
                  if (konsumen.get(0) != null) {
                      konsumen.get(0).setStatusSomasi(status1.getDescription());
                      konsumen.get(0).setConfirmationLongitude(somasi.getLongitude());
                      if (konsumen.get(0).getSendSomasi1Date() == null) {
                          konsumen.get(0).setSendSomasi1Date(date);
                      } else {
                          konsumen.get(0).setSendSomasi2Date(date);
                      }
                      konsumen.get(0).setType(somasi.getType());
                      konsumen.get(0).setPenerimaSomasi(somasi.getPenerimaSomasi());
                      konsumen.get(0).setHubunganPenerima(somasi.getHubunganPenerima());
                    }
                  return aggreDAO.update(konsumen.get(0));
            })
            .compose(retx -> {
                
                return add(historyStatus);
            })
            .compose(add -> {
                
                if (add != null) {
                    result.complete("Succes!");
                } else {
                    result.fail("Gagal Add");
                }
                return Future.succeededFuture(result);
            });
            
        return result;
    }
    
    public Future<String> SubmitKA (SubmitSomasi somasi) {
        
        Future<String> result = Future.future();
        HistoryStatus historyStatus = new HistoryStatus();
        Date date = new Date();
        String statusKa = "";
        
                kaposDAO.getByCode(somasi.getCode())
                .compose(ret -> {
                    
                    if (ret != null) {
                        historyStatus.setStatusByPhone(ret.getKesimpulan());
                        System.out.println("Status KA POS : " +historyStatus.getStatusByPhone());
                    }
                        
                    return aggreDAO.getById(somasi.getKonsumenId());
                
                })
                .compose(ret -> {
                    
                    if (ret.get(0) != null) {
                        
                        ret.get(0).setStatusByPhone(historyStatus.getStatusByPhone());
                        historyStatus.setIdAgentPos(somasi.getIdAgentPos());
                        historyStatus.setKonsumenId(somasi.getKonsumenId());
                        historyStatus.setCreated(date);
                        
                        result.complete("Success!!");
                        
                    } else {
                        
                        result.complete("");
                    }
                    
                    return aggreDAO.update(ret.get(0));
                
                })
                .compose(ret -> {
                
                    return add(historyStatus);
                });
        
        return result;
    }
}
