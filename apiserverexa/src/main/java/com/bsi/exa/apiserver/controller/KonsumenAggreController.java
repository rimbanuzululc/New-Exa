/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.apiserver.controller;

import com.bsi.exa.apiserver.dto.APIResult;
import com.bsi.exa.foundation.Errors;
import com.bsi.exa.foundation.dto.KonsumenAggrement;
import com.bsi.exa.foundation.service.KonsumenAggrementService;
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

@RestController(value = "/konsumen/finance/")
public class KonsumenAggreController {
    
    @AutoWired
    KonsumenAggrementService service;
    
    @RequestMapping(value = "/add", method = HttpMethod.POST)
    public Future<APIResult> add (@RequestBody KonsumenAggrement konsumen) {
        
       Future<APIResult> result = Future.future();
       
       service.add(konsumen)
               .setHandler(ret -> {
                   
                APIResult apiResult = new APIResult();
                
                if (ret.succeeded()) {
                    
                    apiResult.setResult(ret.result());
                } else {
                    apiResult.setErrorMsg("Gagal Add Konsumen");
                }
               
                result.complete(apiResult);
               });
        return result;
    }
    
    @RequestMapping(value = "/update", method = HttpMethod.PUT)
    public Future<APIResult> update (@RequestBody KonsumenAggrement konsumen) {
        
       Future<APIResult> result = Future.future();
       
       service.update(konsumen)
               .setHandler(ret -> {
                   
                APIResult apiResult = new APIResult();
                
                if (ret.succeeded()) {
                    
                    apiResult.setResult(ret.result());
                } else {
                    apiResult.setErrorMsg("Gagal Add Konsumen");
                }
               
                result.complete(apiResult);
               });
        return result;
    }
    
    @RequestMapping(value = "/list")
    public Future<APIResult> list () {
        
        Future<APIResult> result = Future.future();
       
       service.list()
               .setHandler(ret -> {
                   
                APIResult apiResult = new APIResult();
                
                if (ret.succeeded()) {
                    
                    apiResult.setResult(ret.result());
                } else {
                    apiResult.setErrorMsg("Gagal Add Konsumen");
                }
               
                result.complete(apiResult);
               });
        return result;
    }
    
    @RequestMapping(value = "/search")
    public Future<APIResult> search (@QueryParam("param") String param, @QueryParam("filter") String filter, 
                                    @QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate 
                                    ,@QueryParam("page") int page) {
        
        Future<APIResult> result = Future.future();
        
        service.search(param, filter, startDate, endDate, page)
                .setHandler(ret -> {
                    
                APIResult apiResult = new APIResult();
                
                if (ret.succeeded()) {
                    
                    apiResult.setResult(ret.result());
                } else {
                    apiResult.setErrorMsg("Gagal Add Konsumen");
                }
               
                result.complete(apiResult);
               });
        
        return result;
                
    }
    
    @RequestMapping(value = "/getbyna")
    public Future<APIResult> getByNoAggrement (@QueryParam("noAggre") String noAggre ) {
        
        Future<APIResult> result = Future.future();
       
       service.getByNoAggrement(noAggre)
               .setHandler(ret -> {
                   
                APIResult apiResult = new APIResult();
                
                if (ret.succeeded()) {
                    
                    apiResult.setResult(ret.result());
                } else {
                    apiResult.setErrorMsg("Gagal Get Konsumen");
                }
               
                result.complete(apiResult);
               });
        return result;
    }
    
    @RequestMapping(value = "/listSomasi")
    public Future<APIResult> listSomasi (@QueryParam("idAgent") Integer idAgent ) {
        
        Future<APIResult> result = Future.future();
       
       service.listSomasi(idAgent)
               .setHandler(ret -> {
                   
                APIResult apiResult = new APIResult();
                
                if (ret.succeeded()) {
                    
                    apiResult.setResult(ret.result());
                } else {
                    apiResult.setErrorMsg("Gagal Get Konsumen");
                }
               
                result.complete(apiResult);
               });
        return result;
    }
    
    @RequestMapping(value = "/listSomasiKA")
    public Future<APIResult> listSomasiKA (@QueryParam("idAgent") Integer idAgent ) {
        
        Future<APIResult> result = Future.future();
       
       service.listSomasiKA(idAgent)
               .setHandler(ret -> {
                   
                APIResult apiResult = new APIResult();
                
                if (ret.succeeded()) {
                    
                    apiResult.setResult(ret.result());
                } else {
                    apiResult.setErrorMsg("Gagal Get Konsumen");
                }
               
                result.complete(apiResult);
               });
        return result;
    }
    
    @RequestMapping(value = "/history")
    public Future<APIResult> history (@QueryParam("idAgent") Integer idAgent ) {
        
        Future<APIResult> result = Future.future();
       
       service.history(idAgent)
               .setHandler(ret -> {
                   
                APIResult apiResult = new APIResult();
                
                if (ret.succeeded()) {
                    
                    apiResult.setResult(ret.result());
                } else {
                    apiResult.setErrorMsg("Gagal Get Konsumen");
                }
               
                result.complete(apiResult);
               });
        return result;
    }
    
    @RequestMapping(value = "/historyKA")
    public Future<APIResult> historyKA (@QueryParam("idAgent") Integer idAgent ) {
        
        Future<APIResult> result = Future.future();
       
       service.historyKA(idAgent)
               .setHandler(ret -> {
                   
                APIResult apiResult = new APIResult();
                
                if (ret.succeeded()) {
                    
                    apiResult.setResult(ret.result());
                } else {
                    apiResult.setErrorMsg("Gagal Get Konsumen");
                }
               
                result.complete(apiResult);
               });
        return result;
    }
    
    @RequestMapping(value = "/listuncompleted")
    public Future<APIResult> listUnCompleted () {
        
        Future<APIResult> result = Future.future();
       
       service.listUnCompleted()
               .setHandler(ret -> {
                   
                APIResult aPIResult = new APIResult();
                    
                    if (ret.succeeded()) {
                        aPIResult.setResult(ret.result());
                    } else {
                        aPIResult.error(Errors.COMMON, "" + ret.cause());
                    }
                    result.complete(aPIResult);
                });
        return result;
    }
    
    @RequestMapping(value = "/byid/:id")
    public Future<APIResult> getById (@PathParam ("id") int id) {
        
        Future<APIResult> result = Future.future();
       
       service.getById(id)
               .setHandler(ret -> {
                   
                APIResult aPIResult = new APIResult();
                    
                    if (ret.succeeded()) {
                        aPIResult.setResult(ret.result());
                    } else {
                        aPIResult.error(Errors.COMMON, "" + ret.cause());
                    }
                    result.complete(aPIResult);
                });
        return result;
    }
}
