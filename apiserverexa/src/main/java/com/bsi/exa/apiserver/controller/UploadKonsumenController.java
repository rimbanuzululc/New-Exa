/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.apiserver.controller;

import com.bsi.exa.apiserver.dto.APIResult;
import com.bsi.exa.foundation.Errors;
import com.bsi.exa.foundation.service.UploadKonsumenService;
import io.starlight.AutoWired;
import io.starlight.http.RequestMapping;
import io.starlight.http.RestController;
import io.vertx.core.Future;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.Json;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.RoutingContext;
import java.util.Set;

/**
 *
 * @author hp
 */
@RestController(value = "/upload")
public class UploadKonsumenController {
    
    @AutoWired
    protected UploadKonsumenService konsumenService;
    
    @RequestMapping(value = "/getFileExe", method = HttpMethod.POST)
    public Future<APIResult> getFileExe (RoutingContext ctx) {
        
        Future<APIResult> result = Future.future();
        final String contentType = ctx.request().getHeader(HttpHeaders.CONTENT_TYPE);
        
        System.out.println("contentType : "+ contentType);
        Set<FileUpload> uploads = ctx.fileUploads();
        
        System.out.println("upload : "+ Json.encode(uploads.toString()));
        
        FileUpload tampungUpload = null;
        for(FileUpload file : uploads){
            tampungUpload = file;
            break;
        }
        
        System.out.println("tampung upload : "+tampungUpload.uploadedFileName());
        
        konsumenService.getFileExcel(tampungUpload.uploadedFileName())
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
    
    @RequestMapping(value = "/finance/getFileExe", method = HttpMethod.POST)
    public Future<APIResult> getFileExeFinance (RoutingContext ctx) {
        
        Future<APIResult> result = Future.future();
        final String contentType = ctx.request().getHeader(HttpHeaders.CONTENT_TYPE);
        
        System.out.println("contentType : "+ contentType);
        Set<FileUpload> uploads = ctx.fileUploads();
        
        System.out.println("upload : "+ Json.encode(uploads.toString()));
        
        FileUpload tampungUpload = null;
        for(FileUpload file : uploads){
            tampungUpload = file;
            break;
        }
        
        System.out.println("tampung upload : "+tampungUpload.uploadedFileName());
        
        konsumenService.getFileExcelFinance(tampungUpload.uploadedFileName())
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
