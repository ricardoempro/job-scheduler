package br.com.vivo.jobs.jobscheduler.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vivo.jobs.jobscheduler.dto.JobDto;
import br.com.vivo.jobs.jobscheduler.dto.JobListDto;
import br.com.vivo.jobs.jobscheduler.mapper.JanelaExecucaoMapper;
import br.com.vivo.jobs.jobscheduler.mapper.JobMapper;
import br.com.vivo.jobs.jobscheduler.model.Job;
import br.com.vivo.jobs.jobscheduler.service.JobService;

@RestController
@RequestMapping(value = "/api/v1/jobs")
public class JobController {
	
	@Autowired
	private JobService jobService;
	
	@GetMapping
	public Job get() throws Exception {
		LocalDateTime data = LocalDateTime.now();
		return new Job(1L, "Teste", data, new BigDecimal("2.5"));
	}
	
	@PostMapping("/mapper-test")
	public Job post(@Valid @RequestBody final JobDto jobDto) throws Exception {
		return JobMapper.dtoToModelObject(jobDto);
	}
	
	@PostMapping
	public List<List<Long>> postListJobs(@Valid @RequestBody final JobListDto jobDtoList) throws Exception {
		
		List<Job> jobList = new ArrayList<Job>();
		
		jobDtoList.getJobDtoList().forEach(jobDto -> {
			jobList.add(JobMapper.dtoToModelObject(jobDto));
		});
		
		return jobService.createScheduleList(jobList, JanelaExecucaoMapper.StringToJanelaExecucao(jobDtoList.getJanelaExecucao()));
	}

}
