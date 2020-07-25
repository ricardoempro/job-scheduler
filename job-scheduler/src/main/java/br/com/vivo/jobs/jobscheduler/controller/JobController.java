package br.com.vivo.jobs.jobscheduler.controller;

import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vivo.jobs.jobscheduler.dto.JobDto;
import br.com.vivo.jobs.jobscheduler.mapper.JobMapper;
import br.com.vivo.jobs.jobscheduler.model.Job;

@RestController
@RequestMapping(value = "/api/v1/jobs")
public class JobController {
	
	@GetMapping
	public Job get() throws Exception {
		LocalDateTime data = LocalDateTime.now();
		LocalTime hora = LocalTime.now();
		return new Job(1L, "Teste", data, hora);
	}
	
	@PostMapping
	public Job post(@Valid @RequestBody final JobDto jobDto) throws Exception {
		return JobMapper.dtoToModelObject(jobDto);
	}

}
