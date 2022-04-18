/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.apiserver.controller;

import com.bsi.exa.apiserver.dto.APIResult;
import com.bsi.exa.foundation.Errors;
import com.bsi.exa.foundation.dto.ConfirmKAPos;
import com.bsi.exa.foundation.service.AssignFinanceService;
import com.bsi.exa.foundation.service.KAPosService;
import com.bsi.exa.foundation.service.StatusKAPosService;
import io.starlight.AutoWired;
import io.starlight.http.QueryParam;
import io.starlight.http.RequestBody;
import io.starlight.http.RequestMapping;
import io.starlight.http.RestController;
import io.vertx.core.Future;
import io.vertx.core.http.HttpMethod;

/**
 *
 * @author aldy.kurniawan
 */

@RestController("/kapos")
public class KAPosController {
    
    @AutoWired
    KAPosService service;
    
    @AutoWired
    AssignFinanceService financeService;
    
    @AutoWired
    StatusKAPosService statusService;
    
    @RequestMapping(value = "/submit", method = HttpMethod.POST)
    public Future<APIResult> submit(@RequestBody ConfirmKAPos confirm) {
        
        Future<APIResult> result = Future.future();
        
        service.submit(confirm)
                .setHandler(ret -> {
                   
                    APIResult apiResult = new APIResult();
                    
                    if (ret.succeeded())
                        apiResult.setResult(ret.result());
                    else
                        apiResult.error(ret.cause());
                    
                    result.complete(apiResult);
                });
        
        return result;
    }
    
    
    @RequestMapping("/listallstatus")
    public Future<APIResult> listAllStatus() {
        
        Future<APIResult> result = Future.future();
        
        statusService.listAll()
            .setHandler(ret -> {

                APIResult apiResult = new APIResult();

                if (ret.succeeded())
                    apiResult.setResult(ret.result());
                else
                    apiResult.error(Errors.COMMON, "" + ret.cause());

                result.complete(apiResult);
            });
        
        return result;
    }
    
    @RequestMapping("/reportVerfikasi")
    public Future<APIResult> reportVerifikasi (@QueryParam ("userId") String id) {
        
        Future<APIResult> result = Future.future();
        
        service.reportVerifikasi(id)
             .setHandler(ret -> {
                 
                 APIResult apiResult = new APIResult();

                if (ret.succeeded())
                    apiResult.setResult(ret.result());
                else
                    apiResult.error(Errors.COMMON, "" + ret.cause());

                result.complete(apiResult);
            });
        
        return result;
                 
    }
    
    @RequestMapping("/reportAllSend")
    public Future<APIResult> reportAllSendDebitur (@QueryParam ("userId") String userId) {
        
        Future<APIResult> result = Future.future();
        
        financeService.reportSendDebiturAll(userId)
             .setHandler(ret -> {
                 
                 APIResult apiResult = new APIResult();

                if (ret.succeeded())
                    apiResult.setResult(ret.result());
                else
                    apiResult.error(Errors.COMMON, "" + ret.cause());

                result.complete(apiResult);
            });
        
        return result;
    }
    
    @RequestMapping("/reportPerSend")
    public Future<APIResult> reportPerSendDebitur (@QueryParam ("userId") String userId, @QueryParam("idAgent") Integer idAgent) {
        
        Future<APIResult> result = Future.future();
        
        financeService.reportSendPerDebitur(userId, idAgent)
             .setHandler(ret -> {
                 
                 APIResult apiResult = new APIResult();

                if (ret.succeeded())
                    apiResult.setResult(ret.result());
                else
                    apiResult.error(Errors.COMMON, "" + ret.cause());

                result.complete(apiResult);
            });
        
        return result;
    }
    
    @RequestMapping("/listnodebitur")
    public Future<APIResult> listNoDebitur(@QueryParam("userId") String userId) {
        
        Future<APIResult> result = Future.future();
        
        financeService.listNoDebitur(userId)
            .setHandler(ret -> {

                APIResult apiResult = new APIResult();

                if (ret.succeeded())
                    apiResult.setResult(ret.result());
                else
                    apiResult.error(Errors.COMMON, "" + ret.cause());

                result.complete(apiResult);
            });
        
        return result;
    }
        
}
