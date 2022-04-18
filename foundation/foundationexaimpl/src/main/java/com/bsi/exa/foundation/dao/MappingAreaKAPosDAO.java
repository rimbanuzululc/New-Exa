/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.dao;

import com.bsi.exa.foundation.dto.MappingAreaKAPos;
import io.starlight.db.CommonDAO;
import io.starlight.db.DAO;
import io.vertx.core.Future;
import java.util.List;

/**
 *
 * @author hp
 */
@DAO(config = "db")
public class MappingAreaKAPosDAO extends CommonDAO {
    
    public Future<MappingAreaKAPos> add (MappingAreaKAPos area) {
        
        Future<MappingAreaKAPos> result = Future.future();
        
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
    
    public Future<MappingAreaKAPos> update (MappingAreaKAPos area) {
        
        Future<MappingAreaKAPos> result = Future.future();
        
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
    
    public Future<MappingAreaKAPos> getById (int id) {
        
        MappingAreaKAPos areaKapos = new MappingAreaKAPos();
        areaKapos.setIdMappingArea(id);

        return super.selectOne(areaKapos);
        
    }
    
    public Future<List<MappingAreaKAPos>> list () {
        return queryScript("listMappingArea", MappingAreaKAPos.class);
    }
    
}
