package br.com.vivo.jobs.jobscheduler.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.com.vivo.jobs.jobscheduler.model.JanelaExecucao;
import br.com.vivo.jobs.jobscheduler.model.Job;
import br.com.vivo.jobs.jobscheduler.model.JobGroup;

@Service
public class JobService {
	
	public List<List<Job>> createScheduleList(List<Job> jobList, JanelaExecucao janelaExecucao) {
		
		List<List<Job>> scheduleList = new ArrayList<List<Job>>();
		
		Long totalJobsGroup = janelaExecucao.getDataHoraInicio().until(janelaExecucao.getDataHoraFim(), ChronoUnit.HOURS) / 8;
		Long horasRestantes = janelaExecucao.getDataHoraInicio().until(janelaExecucao.getDataHoraFim(), ChronoUnit.HOURS) % 8;
		
		Map<LocalDateTime, JobGroup> mapGroup = createJobGroups(janelaExecucao.getDataHoraInicio(), totalJobsGroup, horasRestantes);
		
		System.out.println(mapGroup);
		
		return scheduleList;
		
	}
	
	private Map<LocalDateTime, JobGroup> createJobGroups(LocalDateTime inicio, Long totalJobsGroup, Long horasRestantes) {
		
		Map<LocalDateTime, JobGroup> mapGroup = new HashMap<LocalDateTime, JobGroup>();
		
		for(int i = 1; i <= totalJobsGroup; i++) {
			mapGroup.put(inicio.plusHours(8 * i), new JobGroup(inicio.plusHours(8 * (i - 1)), inicio.plusHours(8 * i), 8));
		}
		
		mapGroup.put(inicio.plusHours(8 * totalJobsGroup).plusHours(horasRestantes),
				new JobGroup(inicio.plusHours(8 * totalJobsGroup), inicio.plusHours(8 * totalJobsGroup).plusHours(horasRestantes), Integer.valueOf(horasRestantes.toString())));
		
		return mapGroup;
		
	}

}
