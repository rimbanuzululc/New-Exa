/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.dao;

import com.bsi.exa.foundation.dto.MappingAreaAdminPos;
import io.starlight.db.CommonDAO;
import io.starlight.db.DAO;
import io.vertx.core.Future;

/**
 *
 * @author hp
 */
@DAO(config = "db")
public class MappingAreaAdminPosDAO extends CommonDAO {
    
     public Future<MappingAreaAdminPos> add (MappingAreaAdminPos area) {
        
        Future<MappingAreaAdminPos> result = Future.future();
        
        insert(area)
                .setHandler(ret -> {
                    
                    if (ret.succeeded()) {
                            result.complete(ret.result());
                    } else {
                            result.complete(ret.result());
                    }
                
                });
        return result;
    }
     
     public Future<MappingAreaAdminPos> update (MappingAreaAdminPos area) {
        
        Future<MappingAreaAdminPos> result = Future.future();
        
        update(area)
                .setHandler(ret -> {
                    
                    if (ret.succeeded()) {
                            result.complete(ret.result());
                    } else {
                            result.complete(ret.result());
                    }
                
                });
        return result;
    }
     
    public Future<MappingAreaAdminPos> getById (int id) {
        
        MappingAreaAdminPos areaAdmin = new MappingAreaAdminPos();
        areaAdmin.setIdMappingAreaAdmin(id);
        
        return super.selectOne(areaAdmin);
    }
    
}
