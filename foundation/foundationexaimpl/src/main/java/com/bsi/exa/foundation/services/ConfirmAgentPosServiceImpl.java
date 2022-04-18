/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.services;

import com.bsi.exa.foundation.dao.ConfirmAgentPosDAO;
import com.bsi.exa.foundation.dto.ConfirmAgentPos;
import com.bsi.exa.foundation.service.ConfirmAgentPosService;
import io.starlight.AutoWired;
import io.starlight.Service;
import io.vertx.core.Future;
import java.util.List;

/**
 *
 * @author hp
 */
@Service
public class ConfirmAgentPosServiceImpl  implements ConfirmAgentPosService{

    @AutoWired
    ConfirmAgentPosDAO capdao;
    
    @Override
    public Future<List<ConfirmAgentPos>> list (String code) {
        return capdao.list(code);
    }
    
}
