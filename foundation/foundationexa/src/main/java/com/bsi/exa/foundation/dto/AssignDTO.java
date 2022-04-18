
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 *
 * @author hp
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssignDTO {
    
    protected List<Integer> listIdKonsumen;
    protected Integer idAgentPos;

    

    public Integer getIdAgentPos() {
        return idAgentPos;
    }

    public void setIdAgentPos(Integer idAgentPos) {
        this.idAgentPos = idAgentPos;
    }

    public List<Integer> getListIdKonsumen() {
        return listIdKonsumen;
    }

    public void setListIdKonsumen(List<Integer> listIdKonsumen) {
        this.listIdKonsumen = listIdKonsumen;
    }

}
