/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.service;

import com.bsi.exa.foundation.dto.Konsumen;
import io.vertx.core.Future;
import java.util.List;

/**
 *
 * @author hp
 */
public interface KonsumenService {
    
    Future<Konsumen> add (Konsumen konsumen);
    Future<List<Konsumen>> list ();
    Future<List<Konsumen>> search (String param, String filter, int page);
    
    Future<List<Konsumen>> getById (int id);
}
