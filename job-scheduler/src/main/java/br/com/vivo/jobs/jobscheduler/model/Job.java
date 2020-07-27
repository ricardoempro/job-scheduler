package br.com.vivo.jobs.jobscheduler.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
* Classe que representa um job com seus atributos.
* 
* @author Ricardo Neves
* 
*/

public class Job {
	
	private Long id;
	private String descricao;
	private LocalDateTime dataMaximaConclusao;
	private BigDecimal tempoEstimado;
	
	public Job() {}
	    
	public Job(Long id, String descricao, LocalDateTime dataMaximaConclusao, BigDecimal tempoEstimado) {
		this.id = id;
		this.descricao = descricao;
	    this.dataMaximaConclusao = dataMaximaConclusao;
	    this.tempoEstimado = tempoEstimado;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public LocalDateTime getDataMaximaConclusao() {
		return dataMaximaConclusao;
	}
	
	public void setDataMaximaConclusao(LocalDateTime dataMaximaConclusao) {
		this.dataMaximaConclusao = dataMaximaConclusao;
	}
	
	public BigDecimal getTempoEstimado() {
		return tempoEstimado;
	}
	
	public void setTempoEstimado(BigDecimal tempoEstimado) {
		this.tempoEstimado = tempoEstimado;
	}	

}
