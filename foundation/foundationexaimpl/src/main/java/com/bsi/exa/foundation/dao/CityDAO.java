/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.dao;

import com.bsi.exa.foundation.dto.City;
import io.starlight.db.CommonDAO;
import io.starlight.db.DAO;
import io.vertx.core.Future;
import java.util.List;

/**
 *
 * @author hp
 */
@DAO(config = "db")
public class CityDAO extends CommonDAO{
    
    public Future<List<City>> listByState (int id) {
        
        return queryScriptWihtParam("listByState", City.class, "id", id);
    }
   
}
