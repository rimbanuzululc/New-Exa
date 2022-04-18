/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.apiserver.controller;

import com.bsi.exa.apiserver.dto.APIResult;
import com.bsi.exa.foundation.Errors;
import com.bsi.exa.foundation.dto.Agent;
import com.bsi.exa.foundation.dto.HistoryDownload;
import com.bsi.exa.foundation.service.AgentService;
import com.bsi.exa.foundation.service.AgentPosService;
import com.bsi.exa.foundation.service.HistoryDownloadService;
import com.bsi.exa.foundation.service.KonsumenAggrementService;
import com.bsi.exa.foundation.service.TemplateService;
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
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author hp
 */

@RestController("/download")
public class AgentController {
    
    @AutoWired
    protected AgentService agentService;
    
    @AutoWired
    protected AgentPosService templateService;
    
    @AutoWired
    protected KonsumenAggrementService aggrementService;
    
    @AutoWired
    protected HistoryDownloadService downloadService;
    
    
    @RequestMapping(value = "/add", method = HttpMethod.POST)
    public Future<APIResult> add (@RequestBody Agent agent) {
        
        Future<APIResult> result = Future.future();
        
        agentService.add(agent)
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
    
    @RequestMapping(value = "/list")
    public Future<APIResult> list () {
    
        Future<APIResult> result = Future.future();
        
        agentService.listAgent()
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
    
    
    @RequestMapping(value = "/somasi1", produce = ResponseType.RAW)
    public Future<Buffer> getVoucherHistoryExel1(@QueryParam("no")String no,RoutingContext rc) {
        
    	rc.response().headers().add("Cache-Control","public");
        
        String userId = rc.get("userId") == null ? null : rc.get("userId").toString();
        
        System.out.println("user : "+userId);
        
        HistoryDownload download = new HistoryDownload();
    	
        Future<Buffer> result = Future.future();
        Map<String, Object> var = new HashMap<>();
        System.out.println("No Aggrement : "+no);
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMMM yyyy");
        Date date = new Date();
        String tgl = sdf.format(date);
        
        download.setDownloadby(userId);
        download.setDownloadTime(date);
        
                
                downloadService.add(download)
                .compose((HistoryDownload history) -> {
                    
                    SimpleDateFormat bulan = new SimpleDateFormat("MM");
                    SimpleDateFormat tahun = new SimpleDateFormat("yyyy");
                    
                    String noSurat = "";
                    String nomor = "";
                    String bln = bulan.format(date);
                    String thn = tahun.format(date);
                switch (String.valueOf(history.getId()).length()) {
                    case 1:
                        nomor = "00"+String.valueOf(history.getId());
                        break;
                    case 2:
                        nomor = "0"+String.valueOf(history.getId());
                        break;
                    default:
                        nomor = String.valueOf(history.getId());
                        break;
                }
                
                    noSurat = nomor+"/DANCONS-SMSF/SOMASI/"+bln+"/"+thn;
                    
                    System.out.println("Nomor Surat : "+ noSurat);
                    
                    var.put("noSurat", noSurat);
                    
                    history.setNosuratsomasi(noSurat);
                    
                    return downloadService.update(history);
                    
                })
                .compose(update -> {
        
                 return aggrementService.getByNoAggrement(no);
                        
                })
                .compose(kons -> {
                    
                     Date now = null;
                    
                    try {
                        now = sdf.parse(tgl);
                    } catch (Exception e) {
                    }
                    
                    if (kons != null) {
//                        kons.setAssignDate(now);
                        var.put("kons", kons);
                    }
                
                    return templateService.generatePDFFromTempate("somasi01", var);
                })
                .compose(attc -> {
                         
                        System.out.println("dddd" + attc);
           
                            FileSystem fs = Vertx.currentContext().owner().fileSystem();
                            fs.readFile(attc, ret2 -> {

                                if (ret2.succeeded()) {

                                    rc.response().headers().add("Content-Type", "application/x-pdf");
                                    rc.response().headers().add("Content-Length", "" + ret2.result().length());                        
                                    rc.response().headers().add("Content-Disposition", "attachment; filename=somasi1.pdf");

                                    result.complete(ret2.result());
                                }
                                else {
                                    System.out.println("XXXX : " + ret2.cause());
                                    result.fail(new Exception("File not found"));
                                }
                                    
                            });
                            
                            return Future.succeededFuture();
                       
                    });
             return result;        
    }
    
    @RequestMapping(value = "/somasi2", produce = ResponseType.RAW)
    public Future<Buffer> getVoucherHistoryExel2(@QueryParam("no")String no,RoutingContext rc) {
        
    	rc.response().headers().add("Cache-Control","public");
        String userId = rc.get("userId") == null ? null : rc.get("userId").toString();
         
         System.out.println("Rc : "+ Json.encode(rc.getBodyAsString()));
         
         String user = rc.request().getParam("userId");
         System.out.println("User : "+user);
    	
        Future<Buffer> result = Future.future();
        Map<String, Object> var = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMMM yyyy");
        Date date = new Date();
        String tgl = sdf.format(date);
        HistoryDownload download = new HistoryDownload();
        
        download.setDownloadby(userId);
        download.setDownloadTime(date);
        
                
                downloadService.add(download)
                .compose(history -> {
                    
                    SimpleDateFormat bulan = new SimpleDateFormat("MM");
                    SimpleDateFormat tahun = new SimpleDateFormat("yyyy");
                    
                    String noSurat = "";
                    String nomor = "";
                    String bln = bulan.format(date);
                    String thn = tahun.format(date);
                switch (String.valueOf(history.getId()).length()) {
                    case 1:
                        nomor = "00"+String.valueOf(history.getId());
                        break;
                    case 2:
                        nomor = "0"+String.valueOf(history.getId());
                        break;
                    default:
                        nomor = String.valueOf(history.getId());
                        break;
                }
                
                    noSurat = nomor+"/DANCONS-SMSF/SOMASI/"+bln+"/"+thn;
                    
                    System.out.println("Nomor Surat : "+ noSurat);
                    
                    var.put("noSurat", noSurat);
                    
                    history.setNosuratsomasi(noSurat);
                    
                    return downloadService.update(history);
                    
                })
                .compose(update -> {
                    
                        return aggrementService.getByNoAggrement(no);
                })
                .compose(kons -> {
                    
                    Date now = null;
                    Date tgl_nunggak = null;
//                    String nunggak = sdf.format(kons.getTglMulaiMenunggak());
                    
                    try {
                        now = sdf.parse(tgl);
//                        tgl_nunggak = sdf.parse(nunggak);
                    } catch (Exception e) {
                    }
                    
//                    if (kons != null) {
//                        kons.setAssignDate(now);
//                        kons.setTglMulaiMenunggak(tgl_nunggak);
//                        var.put("kons", kons);
//                    }
                    var.put("kons", kons);
                
                    return templateService.generatePDFFromTempate("somasi01", var);
                })
                    .compose(attc -> {
                         
                        System.out.println("dddd" + attc);
           
                            FileSystem fs = Vertx.currentContext().owner().fileSystem();
                            fs.readFile(attc, ret2 -> {

                                if (ret2.succeeded()) {

                                    rc.response().headers().add("Content-Type", "application/x-pdf");
                                    rc.response().headers().add("Content-Length", "" + ret2.result().length());                        
                                    rc.response().headers().add("Content-Disposition", "attachment; filename=somasi2.pdf");

                                    result.complete(ret2.result());
                                }
                                else {
                                    System.out.println("XXXX : " + ret2.cause());
                                    result.fail(new Exception("File not found"));
                                }
                                    
                            });
                        return Future.succeededFuture();
                    });
                    return result;
    }
    
}
