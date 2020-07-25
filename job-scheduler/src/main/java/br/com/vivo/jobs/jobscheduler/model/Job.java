package br.com.vivo.jobs.jobscheduler.model;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Job {
	
	private Long id;
	private String descricao;
	private LocalDateTime dataMaximaConclusao;
	private LocalTime tempoEstimado;
	
	public Job() {}
	    
	public Job(Long id, String descricao, LocalDateTime dataMaximaConclusao, LocalTime tempoEstimado) {
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
	
	public LocalTime getTempoEstimado() {
		return tempoEstimado;
	}
	
	public void setTempoEstimado(LocalTime tempoEstimado) {
		this.tempoEstimado = tempoEstimado;
	}	

}
