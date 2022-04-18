/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.services;

import com.bsi.exa.foundation.dao.StatusKAPosDAO;
import com.bsi.exa.foundation.dto.StatusKAPos;
import com.bsi.exa.foundation.service.StatusKAPosService;
import io.starlight.AutoWired;
import io.starlight.Service;
import io.vertx.core.Future;
import java.util.List;

/**
 *
 * @author hp
 */
@Service
public class StatusKAPosServiceImpl implements StatusKAPosService{

    @AutoWired
    StatusKAPosDAO kAPosDAO;
    
    @Override
    public Future<StatusKAPos> getByCode(String code) {
        return kAPosDAO.getByCode(code);
    }
    
    @Override
    public Future<List<StatusKAPos>> listAll() {
         return kAPosDAO.listAll();
    }
    
}
