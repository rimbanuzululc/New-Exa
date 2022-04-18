/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.apiserver.controller;

import com.bsi.exa.apiserver.dto.APIResult;
import com.bsi.exa.foundation.dto.Konsumen;
import com.bsi.exa.foundation.service.KonsumenService;
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
@RestController(value = "/konsumen/")
public class KonsumenController {
    
    @AutoWired
    KonsumenService service;
    
    @RequestMapping(value = "/add", method = HttpMethod.POST)
    public Future<APIResult> add (@RequestBody Konsumen konsumen) {
        
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
    public Future<APIResult> search (@QueryParam("param") String param, @QueryParam("filter") String filter
                                    , @QueryParam("page") int page) {
        
        Future<APIResult> result = Future.future();
        System.out.println(param + filter);
        service.search(param, filter, page)
                .setHandler(ret -> {
                
                APIResult apiResult = new APIResult();
                
                if (ret.succeeded()) {
                    
                    apiResult.setResult(ret.result());
                    
                    if (ret.result() == null) {
                        apiResult.setErrorMsg("Data Not Found");
                    }
                } else {
                    apiResult.setErrorMsg("Failure");
                }
               
                result.complete(apiResult);
               });
        return result;
        
    }
    
}
