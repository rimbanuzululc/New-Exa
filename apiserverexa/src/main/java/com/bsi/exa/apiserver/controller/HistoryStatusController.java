/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.apiserver.controller;

import com.bsi.exa.apiserver.dto.APIResult;
import com.bsi.exa.foundation.dto.SubmitSomasi;
import com.bsi.exa.foundation.service.HistoryStatusService;
import io.starlight.AutoWired;
import io.starlight.http.PathParam;
import io.starlight.http.QueryParam;
import io.starlight.http.RequestBody;
import io.starlight.http.RequestMapping;
import io.starlight.http.RestController;
import io.vertx.core.Future;
import io.vertx.core.http.HttpMethod;

/**
 *
 * @author hp
 */
@RestController("/status")
public class HistoryStatusController {
    
    @AutoWired
    HistoryStatusService service;
    
    @RequestMapping(value = "/add", method = HttpMethod.POST)
    public Future<APIResult> add (@QueryParam("code") String code, @QueryParam("konsumenId") Integer konsumenId,
                                    @QueryParam("idAgentPos") Integer idAgentPos) {
        
        Future<APIResult> result = Future.future();
        
        service.add(code, konsumenId, idAgentPos)
                .setHandler(ret -> {
                
                    APIResult apiResult = new APIResult();
                    if (ret.succeeded()) {
                        apiResult.setResult(ret.result());
                    } else {
                        apiResult.setErrorMsg("GAGAL");
                    }
                    
                result.complete(apiResult);
                });
        return result;
    }
    
    @RequestMapping(value = "/submit", method = HttpMethod.POST)
    public Future<APIResult> submit (@RequestBody SubmitSomasi somasi) {
        
        Future<APIResult> result = Future.future();
        
        service.submit(somasi)
                .setHandler(ret -> {
                
                    APIResult apiResult = new APIResult();
                    if (ret.succeeded()) {
                        apiResult.setResult(ret.result());
                    } else {
                        apiResult.setErrorMsg("GAGAL");
                    }
                    
                result.complete(apiResult);
                });
        return result;
        
    }
    
    @RequestMapping(value = "/submitKA", method = HttpMethod.POST)
    public Future<APIResult> submitKA (@QueryParam("konsumenId") Integer konsumenId, @QueryParam("code") String code, 
                                       @QueryParam("type") String type ,@QueryParam("idAgent") Integer idAgent) {
        
        Future<APIResult> result = Future.future();
        
        service.submitKA(konsumenId, code, type, idAgent)
                .setHandler(ret -> {
                
                    APIResult apiResult = new APIResult();
                    if (ret.succeeded()) {
                        apiResult.setResult(ret.result());
                    } else {
                        apiResult.setErrorMsg("GAGAL");
                    }
                    
                result.complete(apiResult);
                });
        return result;
        
    }
    
}
