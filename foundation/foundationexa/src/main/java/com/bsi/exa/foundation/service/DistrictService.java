/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.service;

import com.bsi.exa.foundation.dto.District;
import io.vertx.core.Future;
import java.util.List;

/**
 *
 * @author hp
 */
public interface DistrictService {
    
    Future<List<District>> listDistrictByCity (int cityid);
    Future<List<District>> listDistrictByKodeArea (String kode);
    
}
