/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.dao;

import com.bsi.exa.foundation.dto.ConfirmAgentPos;
import io.starlight.db.CommonDAO;
import io.starlight.db.DAO;
import io.vertx.core.Future;
import java.util.List;

/**
 *
 * @author hp
 */
@DAO
public class ConfirmAgentPosDAO extends CommonDAO{
    
    public Future<List<ConfirmAgentPos>> list (String code) {
      return queryScriptWihtParam("list", ConfirmAgentPos.class, "cod", code);
    }
    
    public Future<ConfirmAgentPos> getByCode (String code) {
      return queryScriptSingleWithParam("getByCode", ConfirmAgentPos.class, "code", code);
    }
    
}
