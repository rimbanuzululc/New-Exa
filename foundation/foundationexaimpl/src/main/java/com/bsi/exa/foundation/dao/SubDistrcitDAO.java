/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.dao;

import com.bsi.exa.foundation.dto.SubDistrict;
import io.starlight.db.CommonDAO;
import io.starlight.db.DAO;
import io.vertx.core.Future;
import java.util.List;

/**
 *
 * @author hp
 */
@DAO
public class SubDistrcitDAO extends CommonDAO{
    
    public Future<List<SubDistrict>> listByDistrict (int id) {
        
        return queryScriptWihtParam("listByDistrict", SubDistrict.class, "id", id);
    }
    
}
