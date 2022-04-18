/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.dao;

import com.bsi.exa.foundation.dto.Agent;
import io.starlight.db.CommonDAO;
import io.starlight.db.DAO;
import io.vertx.core.Future;
import java.util.List;

/**
 *
 * @author hp
 */
@DAO(config = "db")
public class AgentDAO extends CommonDAO{
    
    public Future<Agent> add (Agent agent) {
        Future<Agent> result = Future.future();
        
        insert(agent)
                .setHandler(ret -> {
                
                    if (ret.succeeded()) {
                        result.complete(agent);
                    } else {
                        result.fail(ret.cause());
                    }
               });
        return result; 
    }
    
    public Future<List<Agent>> getAgentbyIdDebitur (int idDebitur) {
        return queryScriptWihtParam("getAgentbyIdDebitur", Agent.class, "idDebitur", idDebitur);
    }
    
    public Future<List<Agent>> listAgent() {
        return 
                queryScript("listAgent", Agent.class)
                .compose(ret -> {
                    
                    return Future.succeededFuture(ret);
    
                });
    }
    
}
