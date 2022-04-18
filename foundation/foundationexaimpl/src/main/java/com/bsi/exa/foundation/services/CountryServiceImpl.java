/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.services;

import com.bsi.exa.foundation.dao.CountryDAO;
import com.bsi.exa.foundation.dto.Country;
import com.bsi.exa.foundation.service.CountryService;
import io.starlight.AutoWired;
import io.starlight.Service;
import io.vertx.core.Future;
import java.util.List;

/**
 *
 * @author Aldy Kurniawan S
 */

@Service
public class CountryServiceImpl implements CountryService{

    @AutoWired
    protected CountryDAO dao;
    
    
    @Override
    public Future<List<Country>> listAll() {
        
        return dao.listAll();
    }
    
    
}
