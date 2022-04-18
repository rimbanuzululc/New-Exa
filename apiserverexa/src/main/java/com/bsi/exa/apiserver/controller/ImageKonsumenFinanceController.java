/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.apiserver.controller;

import com.bsi.exa.apiserver.dto.APIResult;
import com.bsi.exa.foundation.dto.ImageKonsumenFinance;
import com.bsi.exa.foundation.service.ImageKonsumenFinanceService;
import io.starlight.AutoWired;
import io.starlight.http.PathParam;
import io.starlight.http.QueryParam;
import io.starlight.http.RequestBody;
import io.starlight.http.RequestMapping;
import io.starlight.http.ResponseType;
import io.starlight.http.RestController;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.FileSystem;
import io.vertx.core.http.HttpMethod;
import java.util.Base64;

/**
 *
 * @author hp
 */
@RestController(value = "/img")
public class ImageKonsumenFinanceController {
    
    @AutoWired
    protected ImageKonsumenFinanceService service;
    
    @RequestMapping(value = "", method = HttpMethod.POST)
    public Future<APIResult> add (@RequestBody ImageKonsumenFinance finance) {
        
        Future<APIResult> result = Future.future();
        
        service.add(finance)
                .setHandler(ret -> {
                    
                    APIResult aPIResult = new APIResult();
                    
                    if (ret.succeeded()) {
                        aPIResult.setResult(ret.result());
                    } else {
                        aPIResult.setErrorMsg("Gagal");
                    }
                    
                    result.complete(aPIResult);
                });
        return result;
    }
    
    @RequestMapping(value = "/list")
    public Future<APIResult> list (@QueryParam("konsumenId") Integer konsumenId) {
        Future<APIResult> result = Future.future();
        
        service.history(konsumenId)
                .setHandler(ret -> {
                    
                    APIResult aPIResult = new APIResult();
                    
                    if (ret.succeeded()) {
                        aPIResult.setResult(ret.result());
                    } else {
                        aPIResult.setResult(ret.cause());
                    }
                    
                    result.complete(aPIResult);
                
                });
        return result;
    }
    
    @RequestMapping(value = "/history/:id/filebytype/:filename", produce = ResponseType.RAW)
    public Future<Buffer> getFileByType(io.vertx.ext.web.RoutingContext rc, @PathParam("id") int id,@PathParam("filename") String filename) {
        
        Future<Buffer> result = Future.future();
        
        service.getImage(filename,id)
            .setHandler(ret -> {

                if (ret.succeeded() && ret.result() != null && ret.result().getImageFile()!= null) {

                    String mime = "image/jpg";
                    
                    int pos = 0;
                    
                    if (ret.result().getImageName() != null)
                        pos = ret.result().getImageName().lastIndexOf(".");
                    
                    String ext = "jpg";

                    if (pos > 0)
                        ext = ret.result().getImageName().substring(pos + 1);

                    ext = ext.toLowerCase();

                    switch (ext) {

                        case "png" : mime = "image/png"; break;
                        case "jpg" : mime = "image/jpg"; break;
                        case "jpeg" : mime = "image/jpeg"; break;
                        case "gif" : mime = "image/gif"; break;
                        default : mime = "application/octet-stream";break;
                    }

                    Buffer buff = Buffer.buffer(Base64.getDecoder().decode(ret.result().getImageFile()));

                    rc.response().headers().add("Content-Type", mime);
                    rc.response().headers().add("Content-Length", "" + buff.length());                        
                    rc.response().headers().add("Content-Disposition", "inline; filename=\"" + ret.result().getImageName()+ "\"");

                    result.complete(buff);
                }
                else {

                    FileSystem fs = Vertx.currentContext().owner().fileSystem();
                    fs.readFile("webroot/img/imgerr.png", ret2 -> {

                        if (ret2.succeeded()) {

                            rc.response().headers().add("Content-Type", "image/png");
                            rc.response().headers().add("Content-Length", "" + ret2.result().length());                        
                            rc.response().headers().add("Content-Disposition", "inline; filename=\"imgerr.png\"");

                            result.complete(ret2.result());
                        }
                        else
                            result.fail(new Exception("Image not found"));
                    });
                }
            });
        
        return result;
    } 
}
