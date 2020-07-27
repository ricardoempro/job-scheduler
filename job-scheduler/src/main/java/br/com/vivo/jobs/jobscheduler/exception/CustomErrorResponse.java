package br.com.vivo.jobs.jobscheduler.exception;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
* CustomErrorResponse é a classe customizada para padronização das respostas das exceptions que são retornadas pela API.
* 
* Contém um Time Stamp, uma mensagem customizada e a mensagem de error gerada pela exception.
* 
* @author Ricardo Neves
* 
*/

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
