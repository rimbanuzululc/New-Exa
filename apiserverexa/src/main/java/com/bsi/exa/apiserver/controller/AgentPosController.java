/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.apiserver.controller;

import com.bsi.exa.apiserver.dto.APIResult;
import com.bsi.exa.apiserver.dto.LoginResult;
import com.bsi.exa.apiserver.service.TokenService;
import com.bsi.exa.foundation.CodedException;
import com.bsi.exa.foundation.Errors;
import com.bsi.exa.foundation.dto.AssignDTO;
import com.bsi.exa.foundation.dto.AgentPos;
import com.bsi.exa.foundation.dto.OTP;
import com.bsi.exa.foundation.service.AgentPosService;
import com.bsi.exa.foundation.service.AssignFinanceService;
import com.bsi.exa.foundation.service.KonsumenAggrementService;
import io.starlight.AutoWired;
import io.starlight.http.PathParam;
import io.starlight.http.QueryParam;
import io.starlight.http.RequestBody;
import io.starlight.http.RequestMapping;
import io.starlight.http.RestController;
import io.vertx.core.Future;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.Json;

/**
 *
 * @author hp
 */
@RestController(value = "/agentpos")
public class AgentPosController {
    
    @AutoWired
    TokenService tokenService;
    
    @AutoWired
    AgentPosService service;
    
    @AutoWired
    AssignFinanceService assignService;
    
    @AutoWired
    KonsumenAggrementService konsumenAggreService;
    
    @RequestMapping(value = "/add", method = HttpMethod.POST)
    public Future<APIResult> add (@RequestBody AgentPos agentPos) {
        
        Future<APIResult> result = Future.future();
       
       service.add(agentPos)
               .setHandler(ret -> {
                   
                APIResult apiResult = new APIResult();
                
                if (ret.succeeded()) {
                    
                    apiResult.setResult(ret.result());
                } else {
                    apiResult.setErrorMsg("ZIPCODE_ALREADY_TAKEN");
                }
               
                result.complete(apiResult);
               });
        return result;
    }
    
    @RequestMapping(value = "/edit", method = HttpMethod.PUT)
    public Future<APIResult> edit (@RequestBody AgentPos agentPos) {
        
        Future<APIResult> result = Future.future();
        
        service.edit(agentPos)
                .setHandler(ret -> {
                    
                APIResult apiResult = new APIResult();
                
                if (ret.succeeded()) {
                    
                    apiResult.setResult(ret.result());
                } else {
                    apiResult.setErrorMsg("Fail to Edit");
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
    
   @RequestMapping(value = "/assign", method = HttpMethod.POST)
   public Future<APIResult> assignKOnsumen (@RequestBody AssignDTO assignDTO) {
       
       Future<APIResult> result = Future.future();
       service.assign(assignDTO.getListIdKonsumen(), assignDTO.getIdAgentPos())
               .setHandler(ret -> {
               
               APIResult apiResult = new APIResult();
                
                if (ret.succeeded()) {
                    
                    apiResult.setResult(ret.result());
                } else {
                    apiResult.setErrorMsg("Gagal Assign");
                }
               
                result.complete(apiResult);
               });
        return result;
   }
   
   @RequestMapping(value = "/assign/finance", method = HttpMethod.POST)
   public Future<APIResult> assignKOnsumenFinance (@RequestBody AssignDTO assignDTO) {
       
       Future<APIResult> result = Future.future();
       service.assignFinance(assignDTO.getListIdKonsumen(), assignDTO.getIdAgentPos())
               .setHandler(ret -> {
               
               APIResult apiResult = new APIResult();
                
                if (ret.succeeded()) {
                    
                    apiResult.setResult(ret.result());
                } else {
                    apiResult.setErrorMsg("Gagal Assign");
                }
               
                result.complete(apiResult);
               });
        return result;
   }
   
   @RequestMapping(value = "/listAssign")
    public Future<APIResult> listAssignded () {
        
        Future<APIResult> result = Future.future();
       
       service.listAssign()
               .setHandler(ret -> {
                   
                APIResult apiResult = new APIResult();
                
                if (ret.succeeded()) {
                    
                    apiResult.setResult(ret.result());
                } else {
                    apiResult.setErrorMsg("Gagal List Assignded");
                }
               
                result.complete(apiResult);
               });
        return result;
    }
    
    @RequestMapping(value = "/login", method = HttpMethod.POST)
    public Future<APIResult> login (@QueryParam("username") String username, @QueryParam("password") String password) {
        
        Future<APIResult> result = Future.future();
        
        service.login(username, password)
                .setHandler(ret -> {
                    
                    APIResult apiResult = new APIResult();
                    
                    if (ret.succeeded()) {
                        
                        LoginResult loginResult = new LoginResult();
                        loginResult.setAccessToken(tokenService.generateToken(ret.result().getIdAgentpos().toString()));
                        loginResult.setUser(ret.result());
                        
                        apiResult.setResult(loginResult);
                    } else {
                        apiResult.error(ret.cause());
                        result.complete(apiResult);
                    }
                    
                    result.complete(apiResult);
                });
        return result;
    }
    
    @RequestMapping(value = "/update/password", method = HttpMethod.PUT)
    public Future<APIResult> updatePass (@QueryParam("username") String username, @QueryParam("password") String password) {
        
        Future<APIResult> result = Future.future();
        
        service.updatePass(username, password)
                .setHandler(ret -> {
                    
                    APIResult apiResult = new APIResult();
                    
                    if (ret.succeeded()) {
                        apiResult.setResult(ret.result());
                    } else {
                        apiResult.error(ret.cause());
                        result.complete(apiResult);
                    }
                    
                    result.complete(apiResult);
                });
        return result;
    }
    
    
    @RequestMapping(value = "/search")
    public Future<APIResult> search (@QueryParam("districtid") Integer districtid) {
        
        Future<APIResult> result = Future.future();
       
       service.search(districtid)
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
    
    @RequestMapping(value = "/otp", method = HttpMethod.POST)
    public Future<APIResult> otp (@RequestBody OTP otp) {
        
        Future<APIResult> result = Future.future();
        
        service.sendOTP(otp)
                .setHandler(ret -> {
                    
                    APIResult aPIResult = new APIResult();
                    
                    if (ret.succeeded()) {
                        
                        aPIResult.setResult(ret.result());
                        
                    } else {
                        aPIResult.setErrorMsg("Failed to send otp!");
                    }
                    
                    result.complete(aPIResult);
                    
                });
        return result;
    }
    
    @RequestMapping(value = "/validate/otp", method = HttpMethod.POST)
    public Future<APIResult> ValidateOtp (@RequestBody OTP otp) {
        
        Future<APIResult> result = Future.future();
        
        service.validateOTP(otp)
                .setHandler(ret -> {
                    
                    APIResult aPIResult = new APIResult();
                    
                    if (ret.succeeded()) {
                        
                        aPIResult.setResult(ret.result());
                        
                    } else {
                        aPIResult.setErrorMsg("Failed to send otp!");
                    }
                    
                    result.complete(aPIResult);
                    
                });
        return result;
    }
    
    @RequestMapping("/reportsenddebitur")
    public Future<APIResult> reportSendDebitur(@QueryParam("idAgent") int idAgent) {
        
        Future<APIResult> result = Future.future();
        
        assignService.reportSendDebitur(idAgent)
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
    
    @RequestMapping("/reportproductivity")
    public Future<APIResult> reportProductivity(@QueryParam("idAgent") Integer idAgent,
                                                @QueryParam("time") Integer time,
                                                @QueryParam("param") String param) {
        
        Future<APIResult> result = Future.future();
        
        assignService.reportProductivity(idAgent, time, param)
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
    
    @RequestMapping("/listpending")
    public Future<APIResult> listPending(@QueryParam("idAgent") int idAgent) {
        
        Future<APIResult> result = Future.future();
        
        assignService.listPending(idAgent)
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
    
    @RequestMapping("/listdebitur")
    public Future<APIResult> listDebitur(@QueryParam ("userId") int idAgent) {
        
        Future<APIResult> result = Future.future();
        
        konsumenAggreService.listDebiturAgentPos(idAgent)
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
    
    @RequestMapping("/byid/:id")
    public Future<APIResult> getById (@PathParam ("id") int idAgentPos) {
        
        Future<APIResult> result = Future.future();
        
        service.getById(idAgentPos)
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
