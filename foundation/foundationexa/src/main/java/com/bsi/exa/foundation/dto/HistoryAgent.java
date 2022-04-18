/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.starlight.db.AutoKey;
import io.starlight.db.Table;
import java.util.Date;

/**
 *
 * @author hp
 */
@Table("somase_historyagent")
public class HistoryAgent {
    
    @AutoKey 
    protected Integer idHistoryagent;
    
    protected Integer idAgentpos;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    protected Date assignDate;
    
    protected String remark;

    public Integer getIdHistoryagent() {
        return idHistoryagent;
    }

    public void setIdHistoryagent(Integer idHistoryagent) {
        this.idHistoryagent = idHistoryagent;
    }

    public Integer getIdAgentpos() {
        return idAgentpos;
    }

    public void setIdAgentpos(Integer idAgentpos) {
        this.idAgentpos = idAgentpos;
    }

    public Date getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(Date assignDate) {
        this.assignDate = assignDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
}
