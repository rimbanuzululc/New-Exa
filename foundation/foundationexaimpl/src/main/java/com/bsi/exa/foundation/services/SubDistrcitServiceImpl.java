/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.services;

import com.bsi.exa.foundation.dao.SubDistrcitDAO;
import com.bsi.exa.foundation.dto.SubDistrict;
import com.bsi.exa.foundation.service.SubDistrcitService;
import io.starlight.AutoWired;
import io.starlight.Service;
import io.vertx.core.Future;
import java.util.List;

/**
 *
 * @author hp
 */
@Service
public class SubDistrcitServiceImpl implements SubDistrcitService{
    
    @AutoWired
    SubDistrcitDAO dao;

    @Override
    public Future<List<SubDistrict>> listSubDistrictByDistrict(int districtid) {
        return dao.listByDistrict(districtid);
    }
    
}
