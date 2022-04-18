/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.dto;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author hp
 */
public class UploadResultDTO implements Serializable {
    
    private static final long serialVersionUID = 8821566789697001812L;
	
	private String stagingID;
	
	private int totalRow = 0;
	private int success = 0;
	private int failed = 0;
	
	private List<UploadFailureDTO> failures;

	public int getTotalRow() {
		return totalRow;
	}

	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
	}

	public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}

	public int getFailed() {
		return failed;
	}

	public void setFailed(int failed) {
		this.failed = failed;
	}

	public List<UploadFailureDTO> getFailures() {
		return failures;
	}

	public void setFailures(List<UploadFailureDTO> failures) {
		this.failures = failures;
	}

	public String getStagingID() {
		return stagingID;
	}

	public void setStagingID(String stagingID) {
		this.stagingID = stagingID;
	}
        
        
        public void addTotalRow(){
            totalRow++;
        }
        
        public void addSuccess(){
            success++;
            System.out.println("success :  "+success);
        }
        
        public void addFailed(){
            failed++;
        }
    
}
