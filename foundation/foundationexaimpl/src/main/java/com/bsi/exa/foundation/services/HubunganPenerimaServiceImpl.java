/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.services;

import com.bsi.exa.foundation.dao.HubunganPenerimaDAO;
import com.bsi.exa.foundation.dto.HubunganPenerima;
import com.bsi.exa.foundation.service.HubunganPenerimaService;
import io.starlight.AutoWired;
import io.starlight.Service;
import io.vertx.core.Future;

/**
 *
 * @author hp
 */
@Service
public class HubunganPenerimaServiceImpl implements HubunganPenerimaService{ 
    
    @AutoWired
    HubunganPenerimaDAO dao;

    @Override
    public Future<HubunganPenerima> getByCode(String code) {
        return dao.getByCode(code);
    }
    
}
