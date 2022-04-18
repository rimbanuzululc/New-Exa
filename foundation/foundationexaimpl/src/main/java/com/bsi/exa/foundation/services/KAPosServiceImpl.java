/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.services;

import com.bsi.exa.foundation.dao.KAPosDAO;
import com.bsi.exa.foundation.dto.ConfirmKAPos;
import com.bsi.exa.foundation.service.KAPosService;
import io.starlight.AutoWired;
import io.starlight.Service;
import io.vertx.core.Future;
import java.util.List;

/**
 *
 * @author aldy.kurniawan
 */
@Service
public class KAPosServiceImpl implements KAPosService{
    
    @AutoWired
    KAPosDAO dao;
    
    @Override
    public Future<ConfirmKAPos> submit(ConfirmKAPos confirm) {
        return dao.submit(confirm);
    }

    @Override
    public Future<List<ConfirmKAPos>> reportVerifikasi(String userId) {
        return dao.reportVerifikasi(userId);
    }

    @Override
    public Future<List<ConfirmKAPos>> reportVerifikasiForAdmin(String userId) {
        return dao.reportVerifikasiforAdmin(userId);
    }
    
    @Override
    public Future<List<ConfirmKAPos>> reportAllVerifikasi() {
        return dao.reportAllVerifikasi();
    }
}
