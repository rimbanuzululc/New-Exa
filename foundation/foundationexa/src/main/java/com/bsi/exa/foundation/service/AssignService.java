/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.service;

import com.bsi.exa.foundation.dto.Assign;
import io.vertx.core.Future;

/**
 *
 * @author hp
 */
public interface AssignService {
    
    Future<Assign> add (Assign assign);
    
}
