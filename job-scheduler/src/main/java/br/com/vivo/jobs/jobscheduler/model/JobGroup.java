package br.com.vivo.jobs.jobscheduler.model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class JobGroup {
	
	private LocalDateTime inicio;
	private LocalDateTime fim;
	private LocalTime tempoOcioso;
	private List<Job> jobList;
	
	public JobGroup(LocalDateTime inicio, LocalDateTime fim, int totalHoras) {
		this.inicio = inicio;
		this.fim = fim;
		this.tempoOcioso = LocalTime.of(totalHoras, 0);
		this.jobList = new ArrayList<Job>();
		
	}

	public LocalDateTime getInicio() {
		return inicio;
	}
	
	public void setInicio(LocalDateTime inicio) {
		this.inicio = inicio;
	}
	
	public LocalDateTime getFim() {
		return fim;
	}
	
	public void setFim(LocalDateTime fim) {
		this.fim = fim;
	}
	
	public LocalTime getTempoOcioso() {
		return tempoOcioso;
	}
	
	public void setTempoOcioso(LocalTime tempoOcioso) {
		this.tempoOcioso = tempoOcioso;
	}
	
	public List<Job> getJobList() {
		return jobList;
	}
	
	public void setJobList(List<Job> jobList) {
		this.jobList = jobList;
	}

}
