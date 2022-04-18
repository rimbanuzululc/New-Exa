/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.services;

import com.bsi.exa.foundation.dao.AssignFinanceDAO;
import com.bsi.exa.foundation.dao.ConfirmAgentPosDAO;
import com.bsi.exa.foundation.dto.AssignFinance;
import com.bsi.exa.foundation.dto.ReportProductivity;
import com.bsi.exa.foundation.service.AssignFinanceService;
import io.starlight.Service;
import io.vertx.core.Future;
import io.starlight.AutoWired;
import io.starlight.Service;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import java.time.Instant;
import java.util.Date;
import java.util.List;
/**
 *
 * @author aldy.kurniawan
 */

@Service
public class AssignFinanceServiceImpl implements AssignFinanceService {
    
    @AutoWired
    AssignFinanceDAO dao;
    
    @AutoWired
    protected ConfirmAgentPosDAO confirmAgentPosDao;
    
    @Override
    public Future<AssignFinance> update(AssignFinance assignFinance) {
        
        
        if (assignFinance.getConfirmDebitur()  != null && assignFinance.getNoDebitur() != null) {
            assignFinance.setSubmit_date(Date.from(Instant.now()));
        } else {
            assignFinance.setSubmit_date(null);
        }
        
        if (assignFinance.getConfirmDebitur() != null || !assignFinance.getConfirmDebitur().equals("")){
            
            confirmAgentPosDao.getByCode(assignFinance.getConfirmDebitur())
                    .setHandler(ret ->{
                        if (ret.succeeded() && ret.result() != null){
                            
                            switch (ret.result().getValue()) {
                                case 1:
                                    assignFinance.setStatus("Berhasil Terkirim");
                                    break;
                                case 2:
                                    assignFinance.setStatus("Pending");
                                    break;
                                case 3:
                                    assignFinance.setStatus("Failed");
                                    break;
                                default:
                                    break;
                            }
                            
                            dao.update(assignFinance);
                        }
                        
                    });
        }
            
        return dao.update(assignFinance);
    }
    
    @Override
    public Future<List<AssignFinance>> listDebitur() {
         return dao.listDebitur();
    }
    
    @Override
    public Future<List<AssignFinance>> listNoDebitur(String userId) {
         return dao.listNoDebitur(userId);
    }
    
    @Override
    public Future<List<AssignFinance>> listAllNoDebitur() {
         return dao.listAllNoDebitur();
    }
    
    @Override
    public Future<List<AssignFinance>> listNoDebiturAdmin(String userId) {
         return dao.listNoDebiturAdmin(userId);
    }
    
    @Override
    public Future<List<AssignFinance>> reportSendDebitur(int idAgent) {
         return dao.reportSendDebitur(idAgent);
    }
    
    @Override
    public Future<ReportProductivity> reportProductivity(Integer idAgent, Integer time, String param) {
            
        Future<ReportProductivity> result = Future.future();
        
        ReportProductivity productivity = new ReportProductivity();
                
         dao.reportProductivity(idAgent, "Berhasil",time, param)
              .setHandler(ret ->{
                  
                  if (ret.result().getBerhasil() == null) {
                        productivity.setBerhasil(0);
                    } else {
                        productivity.setBerhasil(ret.result().getBerhasil());
                    }
                  
                  dao.reportProductivity(idAgent, "pending",time, param)
                     .setHandler(pending -> {
                         
                        if (pending.result().getPending() == null) {
                               productivity.setPending(0);
                        } else {
                               productivity.setPending(pending.result().getPending());   
                        }
                         
                         dao.reportProductivity(idAgent, "failed",time, param)
                            .setHandler(fail -> {
                                
                                if (fail.result().getFailed() == null) {
                                    productivity.setFailed(0);
                                } else {
                                    productivity.setFailed(fail.result().getFailed());
                                }
                                
                                result.complete(productivity);
                                
                            });
                         
                     });
                          
                  
              });
         return result;
    }
    
    @Override
    public Future<List<AssignFinance>> listPending(int idAgent) {
         return dao.listPending(idAgent);
    }

    @Override
    public Future<List<AssignFinance>> reportSendDebiturAll(String userId) {
        return dao.reportSendAllDebitur(userId);
    }

    @Override
    public Future<List<AssignFinance>> reportSendDebiturforAdmin(String userId) {
        return dao.reportSendDebiturforAdmin(userId);
    }
    
    @Override
    public Future<List<AssignFinance>> reportAllSendDebitur() {
        return dao.reportAllSendDebitur();
    }

    @Override
    public Future<List<AssignFinance>> reportSendPerDebitur(String userId, Integer agentId) {
        return dao.reportSendPerDebitur(userId, agentId);
    }
}
