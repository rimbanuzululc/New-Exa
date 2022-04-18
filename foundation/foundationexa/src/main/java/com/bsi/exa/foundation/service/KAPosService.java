/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.service;

import com.bsi.exa.foundation.dto.ConfirmKAPos;
import io.vertx.core.Future;
import java.util.List;

/**
 *
 * @author aldy.kurniawan
 */
public interface KAPosService {
    
    Future<ConfirmKAPos> submit(ConfirmKAPos confirm);
    Future<List<ConfirmKAPos>> reportVerifikasi (String userId);
    Future<List<ConfirmKAPos>> reportVerifikasiForAdmin (String userId);
    Future<List<ConfirmKAPos>> reportAllVerifikasi();
}
