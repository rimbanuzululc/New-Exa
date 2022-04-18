/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.services;

import com.bsi.exa.foundation.dao.AssignDAO;
import com.bsi.exa.foundation.dto.Assign;
import com.bsi.exa.foundation.service.AssignService;
import io.starlight.AutoWired;
import io.starlight.Service;
import io.vertx.core.Future;

/**
 *
 * @author hp
 */
@Service
public class AssignServiceImpl implements AssignService{
    
    @AutoWired
    AssignDAO dao;
    
    @Override
    public Future<Assign> add(Assign assign) {
        return dao.add(assign);
    }
    
}
