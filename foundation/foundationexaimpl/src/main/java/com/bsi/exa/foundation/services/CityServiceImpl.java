/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.services;

import com.bsi.exa.foundation.dao.CityDAO;
import com.bsi.exa.foundation.dto.City;
import com.bsi.exa.foundation.service.CityService;
import io.starlight.AutoWired;
import io.starlight.Service;
import io.vertx.core.Future;
import java.util.List;

/**
 *
 * @author hp
 */
@Service
public class CityServiceImpl implements CityService {

    @AutoWired
    CityDAO dao;
        
    @Override
    public Future<List<City>> listCityByState(int stateId) {
        return dao.listByState(stateId);
    }
    
}
