package br.com.vivo.jobs.jobscheduler.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import br.com.vivo.jobs.jobscheduler.dto.JobDto;
import br.com.vivo.jobs.jobscheduler.dto.JobListDto;

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
	
	public static JobListDto createJobListDtoValid(List<JobDto> jobDtolist, String janela) {
		
		JobListDto jobListDto = new JobListDto();
		
		jobListDto.setJanelaExecucao(janela);		
		jobListDto.setJobDtoList(jobDtolist);
		
		return jobListDto;
		
	}

}
