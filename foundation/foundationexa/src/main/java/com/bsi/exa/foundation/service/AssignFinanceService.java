package com.bsi.exa.foundation.service;

import com.bsi.exa.foundation.dto.AssignFinance;
import com.bsi.exa.foundation.dto.ReportProductivity;
import io.vertx.core.Future;
import java.util.List;

/**
 *
 * @author hp
 */
public interface AssignFinanceService {
    
    Future<AssignFinance> update(AssignFinance assignFinance);
    Future<List<AssignFinance>> listDebitur();
    Future<List<AssignFinance>> listNoDebitur(String userId);
    Future<List<AssignFinance>> listNoDebiturAdmin(String userId);
    Future<List<AssignFinance>> listAllNoDebitur();
    Future<List<AssignFinance>> reportSendDebitur(int idAgent);
    Future<ReportProductivity> reportProductivity(Integer idAgent, Integer time, String param);
    Future<List<AssignFinance>> listPending(int idAgent);
    
    Future<List<AssignFinance>> reportSendDebiturAll (String userId);
    Future<List<AssignFinance>> reportSendPerDebitur (String userId, Integer agentId);
    Future<List<AssignFinance>> reportSendDebiturforAdmin (String userId);
    Future<List<AssignFinance>> reportAllSendDebitur();
    
}
