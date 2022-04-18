/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.dto;

import java.io.Serializable;

/**
 *
 * @author hp
 */
public class UploadFailureDTO implements Serializable {
    
    private static final long serialVersionUID = 4813864870872178540L;
	
	private Integer row;
	private Object object;
	private String errorMessage;
	
	public Integer getRow() {
		return row;
	}
	public void setRow(Integer row) {
		this.row = row;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
    
}
