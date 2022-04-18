/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.services;

import com.bsi.exa.foundation.dao.MappingAreaKAPosDAO;
import com.bsi.exa.foundation.dto.District;
import com.bsi.exa.foundation.dto.MappingAreaKAPos;
import io.starlight.AutoWired;
import io.starlight.Service;
import io.vertx.core.Future;
import java.util.List;
import com.bsi.exa.foundation.service.MappingAreaKAPosService;

/**
 *
 * @author hp
 */
@Service
public class MappingAreaKAPosServiceImpl2 implements MappingAreaKAPosService {

    @AutoWired
    MappingAreaKAPosDAO dao;
    
    @Override
    public Future<MappingAreaKAPos> add(MappingAreaKAPos area) {
        return dao.add(area);
    }
    
    @Override
    public Future<MappingAreaKAPos> update(MappingAreaKAPos area) {
        return dao.update(area);
    }
    
    @Override
    public Future<MappingAreaKAPos> getById(int id) {
        return dao.getById(id);
    }

    @Override
    public Future<Boolean> delete(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Future<List<MappingAreaKAPos>> list() {
        return dao.list();
    }

    @Override
    public Future<List<District>> listDistByArea(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
