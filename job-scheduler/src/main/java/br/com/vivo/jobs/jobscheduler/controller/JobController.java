package br.com.vivo.jobs.jobscheduler.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vivo.jobs.jobscheduler.dto.JobListDto;
import br.com.vivo.jobs.jobscheduler.mapper.JanelaExecucaoMapper;
import br.com.vivo.jobs.jobscheduler.mapper.JobMapper;
import br.com.vivo.jobs.jobscheduler.model.Job;
import br.com.vivo.jobs.jobscheduler.service.JobService;

/**
* JobController é a classe que contém o endpoint com funcionalidades especifica para Jobs.
* 
* @author Ricardo Neves
* 
*/

@RestController
@RequestMapping(value = "/api/v1/jobs")
public class JobController {
	
	@Autowired
	private JobService jobService;

	/**
	 * Endpoint utilizado para organizar os jobs dentro dos grupos de 8h que são executados na janela de execução.
	 * 
	 * @param jobDtoList recebe um objeto JobListDto
	 * @return Array de Arrays contendo os ids dos jobs nos grupos em ordem de execução. 
	 * @see {@link br.com.vivo.jobs.jobscheduler.dto.JobListDto}
	 */	
	@PostMapping
	public List<List<Long>> postScheduleListJobs(@Valid @RequestBody final JobListDto jobDtoList) throws Exception {
		
		List<Job> jobList = new ArrayList<Job>();
		
		jobDtoList.getJobDtoList().forEach(jobDto -> {
			jobList.add(JobMapper.dtoToModelObject(jobDto));
		});
		
		return jobService.createScheduleList(jobList, JanelaExecucaoMapper.StringToJanelaExecucao(jobDtoList.getJanelaExecucao()));
	}

}
