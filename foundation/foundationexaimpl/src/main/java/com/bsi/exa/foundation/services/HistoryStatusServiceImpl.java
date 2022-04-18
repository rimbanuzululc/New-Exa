/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.services;

import com.bsi.exa.foundation.dao.HistoryStatusDAO;
import com.bsi.exa.foundation.dao.ImageKonsumenFinanceDAO;
import com.bsi.exa.foundation.dto.SubmitSomasi;
import com.bsi.exa.foundation.service.HistoryStatusService;
import io.starlight.AutoWired;
import io.starlight.Service;
import io.vertx.core.Future;

/**
 *
 * @author hp
 */
@Service
public class HistoryStatusServiceImpl implements HistoryStatusService{
    
    @AutoWired
    HistoryStatusDAO dao;
    
    @AutoWired
    ImageKonsumenFinanceDAO imageDAO;
    
    @Override
    public Future<String> add(String code, int konsumenId, int idAgentPos) {
        return dao.history(code, konsumenId, idAgentPos);
    }

    @Override
    public Future<String> submit(SubmitSomasi somasi) {
        
        Future<String> result = Future.future();
        
        imageDAO.checkImage(somasi.getKonsumenId())
            .compose(ret -> {
                
                System.out.println("Check Image : "+ ret);
              if (ret != null) {
                  return dao.submit(somasi);
                }else {
                  return Future.succeededFuture("Foto Debitur Masih Kosong!!");
              }
            })
            .setHandler(res -> {
                System.out.println("Submit : "+res.result());
                result.complete(res.result());
            });
        return result;
    }

    @Override
    public Future<String> submitKA(Integer konsumenId, String code, String type, Integer agentId) {
        
        SubmitSomasi somasi = new SubmitSomasi();
        somasi.setCode(code);
        somasi.setIdAgentPos(agentId);
        somasi.setKonsumenId(konsumenId);
        somasi.setType(type);
        return dao.SubmitKA(somasi);
    }
    
}
