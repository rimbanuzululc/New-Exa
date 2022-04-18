/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.services;

import com.bsi.exa.foundation.dao.KonsumenDAO;
import com.bsi.exa.foundation.dao.UploadFinanceDAO;
import com.bsi.exa.foundation.dao.UploadKonsumenDAO;
import com.bsi.exa.foundation.dto.UploadResultDTO;
import com.bsi.exa.foundation.service.UploadKonsumenService;
import io.starlight.AutoWired;
import io.starlight.Service;
import io.vertx.core.Future;

/**
 *
 * @author hp
 */
@Service
public class UploadKonsumenServiceImpl implements UploadKonsumenService{
    
    @AutoWired
    UploadKonsumenDAO dao;
    
    @AutoWired
    UploadFinanceDAO financeDAO;

    @Override
    public Future<UploadResultDTO> getFileExcel(String file) {
        return dao.getFileExcel(file);
    }

    @Override
    public Future<UploadResultDTO> getFileExcelFinance(String file) {
        return financeDAO.getFileExcel(file);
    }
    
}
