package com.bsi.exa.apiserver.controller;

/**
 *
 * @author aldy.kurniawan
 */

import com.bsi.exa.apiserver.dto.APIResult;
import com.bsi.exa.foundation.Errors;
import com.bsi.exa.foundation.dto.AssignFinance;
import com.bsi.exa.foundation.service.AssignFinanceService;
import com.bsi.exa.foundation.service.KAPosService;
import com.bsi.exa.foundation.service.KonsumenAggrementService;
import io.starlight.AutoWired;
import io.starlight.http.PathParam;
import io.starlight.http.QueryParam;
import io.starlight.http.RequestBody;
import io.starlight.http.RequestMapping;
import io.starlight.http.RestController;
import io.vertx.core.Future;
import io.vertx.core.http.HttpMethod;


@RestController("/admin")
public class AdminController {
    
    @AutoWired
    AssignFinanceService assignService;
    
    @AutoWired
    KAPosService kapos;
    
    @AutoWired
    KonsumenAggrementService konsumenAggreService;
    
    @RequestMapping(value = "/assign", method = HttpMethod.PUT)
    public Future<APIResult> updateAssign(@RequestBody AssignFinance assignFinance) {
        
        Future<APIResult> result = Future.future();
        
        assignService.update(assignFinance)
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
    
    @RequestMapping("/listdebitur")
    public Future<APIResult> listDebiturAdmin(@QueryParam ("userId") String userId) {
        
        Future<APIResult> result = Future.future();
        
        konsumenAggreService.listDebiturAdmin(userId)
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
    
    @RequestMapping("/reportKAPOS")
    public Future<APIResult> reportKAPOS (@QueryParam ("userId") String userId) {
        
        Future<APIResult> result = Future.future();
        
        kapos.reportVerifikasiForAdmin(userId)
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
    
    @RequestMapping("/reportSendDebitur")
    public Future<APIResult> reportSendDebitur (@QueryParam ("userId") String userId) {
        
        Future<APIResult> result = Future.future();
        
        assignService.reportSendDebiturforAdmin(userId)
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
    public Future<APIResult> listNoDebiturAdmin(@QueryParam ("userId") String userId) {
        
        Future<APIResult> result = Future.future();
        
        assignService.listNoDebiturAdmin(userId)
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
    
    @RequestMapping("/reportallkapos")
    public Future<APIResult> reportAllKAPos() {
        
        Future<APIResult> result = Future.future();
        
        kapos.reportAllVerifikasi()
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
    
    @RequestMapping("/reportallsenddebitur")
    public Future<APIResult> reportAllSendDebitur() {
        
        Future<APIResult> result = Future.future();
        
        assignService.reportAllSendDebitur()
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
    
    @RequestMapping("/listallnodebitur")
    public Future<APIResult> listAllNoDebitur() {
        
        Future<APIResult> result = Future.future();
        
        assignService.listAllNoDebitur()
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
    
    @RequestMapping("/listalldebitur")
    public Future<APIResult> listAllDebitur() {
        
        Future<APIResult> result = Future.future();
        
        konsumenAggreService.listAllDebitur()
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
