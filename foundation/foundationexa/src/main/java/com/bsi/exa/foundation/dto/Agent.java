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
@Table("sli_agent")
public class Agent {
    
    @AutoKey
    private Integer idAgent;
    
    private Integer iddebitur;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    private Date date;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    private Date starttime;
    
    private Integer queue;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    private Date endtime;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    private Date agenttime;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    private Date talktime;
    
    private Integer duration;
    private Integer callerid;
    private String statusAgent;
    private String agent;
    private String extension;
    private String callType;

    public Integer getIdAgent() {
        return idAgent;
    }

    public void setIdAgent(Integer idAgent) {
        this.idAgent = idAgent;
    }

    public Integer getIddebitur() {
        return iddebitur;
    }

    public void setIddebitur(Integer iddebitur) {
        this.iddebitur = iddebitur;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Integer getQueue() {
        return queue;
    }

    public void setQueue(Integer queue) {
        this.queue = queue;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Date getAgenttime() {
        return agenttime;
    }

    public void setAgenttime(Date agenttime) {
        this.agenttime = agenttime;
    }

    public Date getTalktime() {
        return talktime;
    }

    public void setTalktime(Date talktime) {
        this.talktime = talktime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getCallerid() {
        return callerid;
    }

    public void setCallerid(Integer callerid) {
        this.callerid = callerid;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getStatusAgent() {
        return statusAgent;
    }

    public void setStatusAgent(String statusAgent) {
        this.statusAgent = statusAgent;
    }
    
    
}
