/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.services;

import com.bsi.exa.foundation.dao.MappingAreaAdminPosDAO;
import com.bsi.exa.foundation.dto.MappingAreaAdminPos;
import com.bsi.exa.foundation.service.MappingAreaAdminPosService;
import io.starlight.AutoWired;
import io.starlight.Service;
import io.vertx.core.Future;

/**
 *
 * @author hp
 */
@Service
public class MappingAreaAdminPosServiceImpl implements MappingAreaAdminPosService{
    
    @AutoWired
    MappingAreaAdminPosDAO adminPosDAO;

    @Override
    public Future<MappingAreaAdminPos> add(MappingAreaAdminPos adminPos) {
        return adminPosDAO.add(adminPos);
    }
    
    @Override
    public Future<MappingAreaAdminPos> update(MappingAreaAdminPos adminPos) {
        return adminPosDAO.update(adminPos);
    }
    
    @Override
    public Future<MappingAreaAdminPos> getById(int id) {
        return adminPosDAO.getById(id);
    }
    
}
