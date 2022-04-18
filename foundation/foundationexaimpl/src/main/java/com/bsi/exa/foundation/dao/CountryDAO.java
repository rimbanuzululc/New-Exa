/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.dao;

import com.bsi.exa.foundation.dto.Country;
import io.starlight.db.CommonDAO;
import io.starlight.db.DAO;
import io.vertx.core.Future;
import java.util.List;

/**
 *
 * @author Aldy Kurniawan S
 */

@DAO(config = "db")
public class CountryDAO extends CommonDAO{
    
    public Future<List<Country>> listAll () {
        return queryScript("listAll", Country.class);
    }
}
