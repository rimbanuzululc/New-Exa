/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.dao;

import com.bsi.exa.foundation.CodedException;
import com.bsi.exa.foundation.Errors;
import com.bsi.exa.foundation.dto.AgentPos;
import com.bsi.exa.foundation.dto.Assign;
import com.bsi.exa.foundation.dto.AssignFinance;
import com.bsi.exa.foundation.dto.Konsumen;
import com.bsi.exa.foundation.dto.KonsumenAggrement;
import com.bsi.exa.foundation.dto.Setting;
import com.bsi.exa.foundation.dto.OTP;
import io.starlight.AutoWired;
import io.starlight.db.CommonDAO;
import io.starlight.db.DAO;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author hp
 */
@DAO(config = "db")
public class AgentPosDAO extends CommonDAO {
    
    @AutoWired
    KonsumenDAO konsumenDAO;
    
    @AutoWired
    KonsumenAggreDAO aggreDAO;
    
    @AutoWired
    AssignDAO assignDAO;
    
    @AutoWired
    AssignFinanceDAO financeDAO;
    
    public Future<AgentPos> add (AgentPos agentPos) {
        
        Future<AgentPos> result = Future.future();
        
        queryScriptSingleWithParam("getByZipcode", AgentPos.class , "zipcode", agentPos.getZipcode())
                .setHandler(ret -> {
                    
                    if (ret.result() != null){
                        result.complete(null);
                    } else {
                        insert(agentPos)
                                .setHandler(ret2 ->{
                                    result.complete(agentPos);
                                });
                    }
                });
        
        return result;
    }
    
    public Future<AgentPos> edit (AgentPos agentPos) {
        
        Future<AgentPos> result = Future.future();
        
        super.update(agentPos).setHandler(ret -> {
            if(ret.succeeded()){
                
                checkName(agentPos.getIdAgentpos())
                        .setHandler(ret2 -> {
                            
                            agentPos.setCityName(ret2.result().getCityName());
                            agentPos.setCountryName(ret2.result().getCountryName());
                            agentPos.setStateName(ret2.result().getStateName());
                            agentPos.setDistrictName(ret2.result().getDistrictName());
                            agentPos.setSubDistrictName(ret2.result().getSubDistrictName());
                            
                            result.complete(agentPos);
                        });
                
            }else{
                result.fail(ret.cause());
            }
        });
        
        return result;
    }
    
    public Future<List<AgentPos>> list () {
        
        return queryScript("list", AgentPos.class);
    }
    
    public Future<AgentPos> getById(int id){
        
        Future<AgentPos> result = Future.future();
        
        queryScriptWihtParam("getById", AgentPos.class, "id", id)
            .setHandler(ret -> {
                
                if (ret.result() != null && ret.succeeded()) {
                    
                    result.complete(ret.result().get(0));
                } else {
                    result.fail(ret.cause());
                }   
                
            });
        
        return result;
    }
    
    public Future<AgentPos> update (AgentPos agentPos) {
        
        return 
                super.upsert(agentPos)
                .compose(ret -> {
                    
                    return Future.succeededFuture(ret);
                });
    }
     
    public Future<Assign> assignKonsumen (Integer idkonsumen, Integer idAgent) {
        
        Assign assign = new Assign();
        Future<Assign> result = Future.future();
        
         konsumenDAO.getById(idkonsumen)
                .compose(konsumen -> {
                    
                    if (konsumen.get(0).getIdKonsumen() != null) {
                        
                        Date date = new Date();
                        assign.setIdKonsumen(idkonsumen);
                        assign.setCardNo(konsumen.get(0).getCardNo());
                        assign.setRef_No(konsumen.get(0).getRef_No());
                        assign.setAssignDate(date);
                        assign.setIdAgentpos(idAgent);
                    } 
                    
                    return assignDAO.add(assign);
                })
                .setHandler(ret -> {

                    if (ret.result() != null) {

                        result.complete(ret.result());
                    } else {
                        result.complete();
                    }
                    
                });
        
        return result;
    }
    
    public Future<AssignFinance> assignKonsumenFinance (Integer idkonsumen, Integer idAgent) {
        
        AssignFinance assignFinance = new AssignFinance();
        Future<AssignFinance> result = Future.future();
        
         aggreDAO.getById(idkonsumen)
                .compose(konsumen -> {
                    
                    if (konsumen.get(0).getKonsumenId() != null) {
                        
                        Date date = new Date();
                        assignFinance.setIdAgentPos(idAgent);
                        assignFinance.setKonsumenId(idkonsumen);
                        assignFinance.setNoAggrement(konsumen.get(0).getNoAggrement());
                        
                        if (konsumen.get(0).getType() == null) {
                            assignFinance.setType("SOMASI1");
                            konsumen.get(0).setType("SOMASI1");
                        } else {
                            assignFinance.setType("SOMASI2");
                            konsumen.get(0).setType("SOMASI2");
                        }
                        
                        assignFinance.setAssign_date(date);
                    } 
                    
                    return aggreDAO.update(konsumen.get(0));
                })
                .compose(update -> {
                    
                    return financeDAO.add(assignFinance);
                    
                })
                .setHandler(ret -> {

                    if (ret.result() != null) {

                        result.complete(ret.result());
                    } else {
                        result.complete();
                    }
                    
                });
        
        return result;
    }
    
    public Future<List<Assign>> listAssign () {
        
        return queryScript("listAssign", Assign.class);
    }
    
    public Future<AgentPos> getByUsername (String username) {
        Future<AgentPos> result = Future.future();
        queryScriptWihtParam("getByUsername", AgentPos.class , "username", username)
                .setHandler(ret -> {
                    
                    if (ret.succeeded() && ret.result() != null) {
                        result.complete(ret.result().get(0));
                    } else {
                        result.complete(ret.result().get(0));
                    }
                    
                });
        return result;
    }
    
     public Future<Setting> getByCode(String code){
        
        Future<Setting> result = Future.future();
         
        queryScriptWihtParam("searchByCode", Setting.class, "code", code)
            .setHandler(ret -> {

                if (ret.succeeded()) {
                    
                    if (ret.result().size() > 0) 
                        result.complete(ret.result().get(0));
                    else
                        result.fail(new Exception("Setting not found : " + code));
                }
                else
                    result.fail(ret.cause());
            });
        
        return result;
    }
     
    public Future<List<AgentPos>> search (int districtid) {
        
        Future<List<AgentPos>> result = Future.future();
        
        queryScriptWihtParam("search", AgentPos.class, "districtid", districtid)
            .setHandler(ret -> {
            
                if (ret.result().size() > 0) {
                    
                    result.complete(ret.result());
                } else {
                    result.complete(null);
                }
            
            });
        
        return result;
    }
    
    public Future<AgentPos> getByZipcode (String zipcode) {
        Future<AgentPos> result = Future.future();
        queryScriptSingleWithParam("getByZipcode", AgentPos.class , "zipcode", zipcode)
                .setHandler(ret -> {
                    
                    if (ret.succeeded() && ret.result() != null) {
                        System.out.println("Masuk sukses");
                        result.complete(ret.result());
                    } else {
                        result.fail("Zipcode tidak ditemukan");
                    }
                    
                });
        return result;
    }
    
    public Future<OTP> sendOtp (OTP otp) {
        
        Future<OTP> result = Future.future();
        
        queryScriptWihtParam("validOtp", OTP.class, "id", otp.getIdAgentpos(), "no", otp.getNoDebitur())
                .setHandler(ret ->{
                    
                    if (ret.result() == null || ret.result().isEmpty()) {
                        insert(otp)
                            .setHandler(ret3 -> {

                                if (ret3.succeeded())
                                    result.complete(ret3.result());
                                else
                                    result.fail(ret3.cause());
                            });
                        
                    } else {
                        
                        OTP otpp = new OTP();
                        otpp.setOtpId(ret.result().get(0).getOtpId());
                        otpp.setIdAgentpos(otp.getIdAgentpos());
                        otpp.setNoDebitur(otp.getNoDebitur());
                        otpp.setOtp(otp.getOtp());
                        
                        update(otpp)
                            .setHandler(ret2 -> {

                                 if (ret2.succeeded())
                                     result.complete(otpp);
                                 else
                                     result.fail(ret2.cause());
                             });
                    }
                
                });
        return result;
    }
    
    public Future<OTP> validateOTP (OTP otp) {
        
        Future<OTP> result = Future.future();
        Integer id = otp.getIdAgentpos();
        String no = otp.getNoDebitur();
        
        queryScriptWihtParam("validOtp", OTP.class, "id", id, "no", no)
                .setHandler(ret ->{
                    
                    if (ret.result().size() > 0) {
                        result.complete(ret.result().get(0));
                        
                    } else {
                        result.fail(ret.cause());
                    }
                
                });
        return result;
    }
    
    public Future<AgentPos> checkName (Integer agent) {
        
        Future<AgentPos> result = Future.future();
        queryScriptWihtParam("checkName", AgentPos.class, "id",agent)
                .setHandler(ret ->{
                    
                    if (ret.result().size() > 0) {
                        result.complete(ret.result().get(0));
                        
                    } else {
                        result.fail(ret.cause());
                    }
                
                });
        return result;
    }
    
}
