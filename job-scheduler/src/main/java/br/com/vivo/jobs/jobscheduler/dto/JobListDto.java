package br.com.vivo.jobs.jobscheduler.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
* Data Transfer Object(DTO) para tranferência da lista de Jobs que serão processados pela API juntamente com a janela de execução.
* 
* @author Ricardo Neves
* 
*/

public class JobListDto {
	
	@JsonProperty("Janela de execução")
	@NotEmpty(message = "A janela de execução não pode ser vazia ou nula!")
	@Size(min = 43, max = 43, message = "eve ser informada no formato: "
			+ "\"yyyy-MM-dd HH:mm:ss até yyyy-MM-dd HH:mm:ss\" !")
	private String janelaExecucao;
	
	@JsonProperty("Lista de jobs")
	@Valid
	private List<JobDto> jobDtoList;

	public List<JobDto> getJobDtoList() {
		return jobDtoList;
	}

	public void setJobDtoList(List<JobDto> jobDtoList) {
		this.jobDtoList = jobDtoList;
	}

	public String getJanelaExecucao() {
		return janelaExecucao;
	}

	public void setJanelaExecucao(String janelaExecucao) {
		this.janelaExecucao = janelaExecucao;
	}
	
}
