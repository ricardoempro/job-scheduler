package br.com.vivo.jobs.jobscheduler.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import br.com.vivo.jobs.jobscheduler.dto.JobDto;
import br.com.vivo.jobs.jobscheduler.dto.JobListDto;
import br.com.vivo.jobs.jobscheduler.model.Job;

public class TestUtil {
	
	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	public static JobDto createJobDto(Long id, String descricao, LocalDateTime datamax, String tempoEstimado) {
		
		JobDto jobDto = new JobDto();
		
		jobDto.setId(id);
		jobDto.setDescricao(descricao);
		jobDto.setDataMaximaConclusao(datamax);
		jobDto.setTempoEstimadoStr(tempoEstimado);
		
		return jobDto;
	}
	
	public static Job createJob(Long id, String descricao, LocalDateTime datamax, BigDecimal tempoEstimado) {
		
		Job job = new Job();
		
		job.setId(id);
		job.setDescricao(descricao);
		job.setDataMaximaConclusao(datamax);
		job.setTempoEstimado(tempoEstimado.setScale(2,RoundingMode.UP));
		
		return job;		
	}
	
	public static JobListDto createJobListDtoValid(List<JobDto> jobDtolist, String janela) {
		
		JobListDto jobListDto = new JobListDto();
		
		jobListDto.setJanelaExecucao(janela);		
		jobListDto.setJobDtoList(jobDtolist);
		
		return jobListDto;		
	}

}
