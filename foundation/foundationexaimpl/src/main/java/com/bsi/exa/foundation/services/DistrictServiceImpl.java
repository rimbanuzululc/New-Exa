/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.services;

import com.bsi.exa.foundation.dao.DistrictDAO;
import com.bsi.exa.foundation.dto.District;
import com.bsi.exa.foundation.service.DistrictService;
import io.starlight.AutoWired;
import io.starlight.Service;
import io.vertx.core.Future;
import java.util.List;

/**
 *
 * @author hp
 */
@Service
public class DistrictServiceImpl implements DistrictService{
    
    @AutoWired
    DistrictDAO dao;

    @Override
    public Future<List<District>> listDistrictByCity(int cityid) {
        return dao.listByCity(cityid);
    }

    @Override
    public Future<List<District>> listDistrictByKodeArea(String kode) {
        return dao.listDistrictByKodeArea(kode);
    }
    
}
