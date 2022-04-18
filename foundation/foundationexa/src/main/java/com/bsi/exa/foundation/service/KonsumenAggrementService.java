/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.service;

import com.bsi.exa.foundation.dto.DetailDebitur;
import com.bsi.exa.foundation.dto.KonsumenAggrement;
import com.bsi.exa.foundation.dto.ListSomasi;
import io.vertx.core.Future;
import java.util.List;

/**
 *
 * @author hp
 */

public interface KonsumenAggrementService {
    
    Future<KonsumenAggrement> add (KonsumenAggrement aggremnt);
    Future<KonsumenAggrement> update (KonsumenAggrement aggremnt);
    Future<KonsumenAggrement> getById (int id);
    
    Future<List<KonsumenAggrement>> list ();
    
    Future<List<KonsumenAggrement>> search (String param, String value, String startDate, String endDate, int page);
    
    Future<DetailDebitur> getByNoAggrement (String no);
    
    Future<List<ListSomasi>> listSomasi (int idAgent);
    
    Future<List<ListSomasi>> listSomasiKA (int idAgent);
    
    Future<List<KonsumenAggrement>> historyKA (int idAgent);
    
    Future<List<KonsumenAggrement>> history (int idAgent);
    
    Future<List<KonsumenAggrement>> listUnCompleted ();
    
    Future<List<KonsumenAggrement>> listDebiturAdmin (String userId);
    
    Future<List<KonsumenAggrement>> listDebiturAgentPos (int idAgent);
    
    Future<List<KonsumenAggrement>> listAllDebitur();
}
