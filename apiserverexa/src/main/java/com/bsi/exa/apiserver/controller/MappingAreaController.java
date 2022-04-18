/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.apiserver.controller;

import com.bsi.exa.apiserver.dto.APIResult;
import com.bsi.exa.foundation.dto.MappingAreaAdminPos;
import com.bsi.exa.foundation.dto.MappingAreaKAPos;
import com.bsi.exa.foundation.service.MappingAreaAdminPosService;
import com.bsi.exa.foundation.service.MappingAreaKAPosService;
import io.starlight.AutoWired;
import io.starlight.http.PathParam;
import io.starlight.http.RequestBody;
import io.starlight.http.RequestMapping;
import io.starlight.http.RestController;
import io.vertx.core.Future;
import io.vertx.core.http.HttpMethod;

/**
 *
 * @author hp
 */
@RestController(value = "/mapping")
public class MappingAreaController {
    
    
    @AutoWired
    MappingAreaKAPosService area;
    
    @AutoWired
    MappingAreaAdminPosService areaAdmin;
    
    @RequestMapping(value = "/kapos", method = HttpMethod.POST)
    public Future<APIResult> add (@RequestBody MappingAreaKAPos mappingArea) {
        Future<APIResult> result = Future.future();
        
        area.add(mappingArea)
                .setHandler(ret -> {
                    
                    APIResult apiResult = new APIResult();
                    
                    if (ret.succeeded()) {
                        apiResult.setResult(ret.result());
                    } else {
                        apiResult.setResult("Eror!!");
                    }
                    
                    result.complete(apiResult);
                });
        return result;
    }
    
    @RequestMapping(value = "/admin", method = HttpMethod.POST)
    public Future<APIResult> adddmin (@RequestBody MappingAreaAdminPos mappingArea) {
        Future<APIResult> result = Future.future();
        
        areaAdmin.add(mappingArea)
                .setHandler(ret -> {
                    
                    APIResult apiResult = new APIResult();
                    
                    if (ret.succeeded()) {
                        apiResult.setResult(ret.result());
                    } else {
                        apiResult.setResult("Eror!!");
                    }
                    
                    result.complete(apiResult);
                });
        return result;
    }
    
    @RequestMapping(value = "/list")
    public Future<APIResult> list () {
        Future<APIResult> result = Future.future();
        
        area.list()
                .setHandler(ret -> {
                    
                    APIResult apiResult = new APIResult();
                    
                    if (ret.succeeded()) {
                        apiResult.setResult(ret.result());
                    } else {
                        apiResult.setResult("Eror!!");
                    }
                    
                    result.complete(apiResult);
                });
        return result;
    }
    
    @RequestMapping(value = "/kapos", method = HttpMethod.PUT)
    public Future<APIResult> update (@RequestBody MappingAreaKAPos mappingArea) {
        Future<APIResult> result = Future.future();
        
        area.update(mappingArea)
                .setHandler(ret -> {
                    
                    APIResult apiResult = new APIResult();
                    
                    if (ret.succeeded()) {
                        apiResult.setResult(ret.result());
                    } else {
                        apiResult.setResult("Eror!!");
                    }
                    
                    result.complete(apiResult);
                });
        return result;
    }
    
    @RequestMapping(value = "/admin", method = HttpMethod.PUT)
    public Future<APIResult> updateAdmin (@RequestBody MappingAreaAdminPos mappingArea) {
        Future<APIResult> result = Future.future();
        
        areaAdmin.update(mappingArea)
                .setHandler(ret -> {
                    
                    APIResult apiResult = new APIResult();
                    
                    if (ret.succeeded()) {
                        apiResult.setResult(ret.result());
                    } else {
                        apiResult.setResult("Eror!!");
                    }
                    
                    result.complete(apiResult);
                });
        return result;
    }
    
    @RequestMapping(value = "/byid/:id")
    public Future<APIResult> getById (@PathParam ("id") int id) {
        Future<APIResult> result = Future.future();
        
        area.getById(id)
                .setHandler(ret -> {
                    
                    APIResult apiResult = new APIResult();
                    
                    if (ret.succeeded()) {
                        apiResult.setResult(ret.result());
                    } else {
                        apiResult.setResult("Eror!!");
                    }
                    
                    result.complete(apiResult);
                });
        return result;
    }
    
    @RequestMapping(value = "/byidAdmin/:id")
    public Future<APIResult> getByIdAdmin (@PathParam ("id") int id) {
        Future<APIResult> result = Future.future();
        
        areaAdmin.getById(id)
                .setHandler(ret -> {
                    
                    APIResult apiResult = new APIResult();
                    
                    if (ret.succeeded()) {
                        apiResult.setResult(ret.result());
                    } else {
                        apiResult.setResult("Eror!!");
                    }
                    
                    result.complete(apiResult);
                });
        return result;
    }
}
