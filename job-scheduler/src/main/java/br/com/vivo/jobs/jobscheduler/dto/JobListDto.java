package br.com.vivo.jobs.jobscheduler.dto;

import java.util.List;

import javax.validation.Valid;

public class JobListDto {
	
	@Valid
	private List<JobDto> jobDtoList;

	public List<JobDto> getJobDtoList() {
		return jobDtoList;
	}

	public void setJobDtoList(List<JobDto> jobDtoList) {
		this.jobDtoList = jobDtoList;
	}
	
}
