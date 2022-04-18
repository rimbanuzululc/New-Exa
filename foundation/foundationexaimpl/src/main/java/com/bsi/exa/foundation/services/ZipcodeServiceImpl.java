/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.services;

import com.bsi.exa.foundation.dao.ZipcodeDAO;
import com.bsi.exa.foundation.dto.Zipcode;
import com.bsi.exa.foundation.service.ZipcodeService;
import io.starlight.AutoWired;
import io.starlight.Service;
import io.vertx.core.Future;
import java.util.List;

/**
 *
 * @author hp
 */
@Service
public class ZipcodeServiceImpl implements ZipcodeService{
    
    @AutoWired
    ZipcodeDAO dao;

    @Override
    public Future<List<Zipcode>> listZipcodeByKecamatan(String code) {
        
        return dao.listByKec(code);
    }
    
}
