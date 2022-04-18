/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.services;

import com.bsi.exa.foundation.dao.HistoryDownloadDAO;
import com.bsi.exa.foundation.dto.HistoryDownload;
import com.bsi.exa.foundation.service.HistoryDownloadService;
import io.starlight.AutoWired;
import io.starlight.Service;
import io.vertx.core.Future;

/**
 *
 * @author hp
 */
@Service
public class HistoryDownloadServiceImpl implements HistoryDownloadService{
    
    @AutoWired
    HistoryDownloadDAO dao; 

    @Override
    public Future<HistoryDownload> add(HistoryDownload historyDownload) {
        return dao.add(historyDownload);
    }

    @Override
    public Future<HistoryDownload> update(HistoryDownload historyDownload) {
        return dao.update(historyDownload); 
    }
    
}
