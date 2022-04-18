/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.dto;

import java.util.List;

/**
 *
 * @author hp
 */
public class DetailDebitur {
    
    protected KonsumenAggrement konsumen;
    
    protected List<ConfirmAgentPos> statusDebitur;
    protected List<ConfirmAgentPos> statusUnit;
    protected List<ConfirmAgentPos> kesimpulan;


    public KonsumenAggrement getKonsumen() {
        return konsumen;
    }

    public void setKonsumen(KonsumenAggrement konsumen) {
        this.konsumen = konsumen;
    }

    public List<ConfirmAgentPos> getStatusDebitur() {
        return statusDebitur;
    }

    public void setStatusDebitur(List<ConfirmAgentPos> statusDebitur) {
        this.statusDebitur = statusDebitur;
    }

    public List<ConfirmAgentPos> getStatusUnit() {
        return statusUnit;
    }

    public void setStatusUnit(List<ConfirmAgentPos> statusUnit) {
        this.statusUnit = statusUnit;
    }

    public List<ConfirmAgentPos> getKesimpulan() {
        return kesimpulan;
    }

    public void setKesimpulan(List<ConfirmAgentPos> kesimpulan) {
        this.kesimpulan = kesimpulan;
    }
    
}
