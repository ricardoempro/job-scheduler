package br.com.vivo.jobs.jobscheduler.exception;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CustomErrorResponse {
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp;
    private String mensage;
    private String error;
    
    public CustomErrorResponse() {}
    
    public CustomErrorResponse(LocalDateTime timestamp, String mensage, String error) {
    	this.timestamp = timestamp;
    	this.mensage = mensage;
    	this.error = error;
    }
    
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getMensage() {
		return mensage;
	}

	public void setMensage(String mensage) {
		this.mensage = mensage;
	}

	public String getError() {
		return error;
	}
	
	public void setError(String error) {
		this.error = error;
	} 
    
}
