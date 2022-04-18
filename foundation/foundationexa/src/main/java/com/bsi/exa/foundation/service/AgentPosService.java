/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.service;

import com.bsi.exa.foundation.dto.AgentPos;
import com.bsi.exa.foundation.dto.Assign;
import com.bsi.exa.foundation.dto.Konsumen;
import com.bsi.exa.foundation.dto.OTP;
import com.bsi.exa.foundation.dto.UpdatePassword;
import com.sun.org.apache.bcel.internal.generic.AALOAD;
import io.vertx.core.Future;
import java.util.List;
import java.util.Map;

/**
 *
 * @author hp
 */
public interface AgentPosService {
    
    Future<AgentPos> add (AgentPos agentPos);
    Future<List<AgentPos>> list ();
    Future<Boolean> assign (List<Integer> listIdKonsumen, int idAgent);
    Future<Boolean> assignFinance (List<Integer> listKonsumenId, int idAgent);
    Future<Assign> listAssign();
    Future<AgentPos> login (String username, String password);
    
    Future<UpdatePassword> updatePass (String username, String password);
    
    Future<String> generatePDFFromTempate(String templateId, Map<String, Object> variable);
    
    Future<List<AgentPos>> search (int id);
    
    Future<AgentPos> edit (AgentPos agentPos);
    Future<AgentPos> getById (int idAgentPos);
    
    Future<OTP> sendOTP (OTP otp);
    
    Future<Boolean> validateOTP (OTP otp);
    
}
