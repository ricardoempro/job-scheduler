package br.com.vivo.jobs.jobscheduler.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.vivo.jobs.jobscheduler.model.JanelaExecucao;
import br.com.vivo.jobs.jobscheduler.model.Job;
import br.com.vivo.jobs.jobscheduler.util.TestUtil;

@SpringBootTest
public class JobServiceTests {
	
	@Autowired
	JobService jobService;
	
	@Test
	public void createScheduleFromJobList() {
		List<Long> list;		
		List<Job> jobList = new ArrayList<Job>();
				
		jobList.add(TestUtil.createJob(1L, "Importação de arquivos de fundos", LocalDateTime.parse("2019-11-10 12:00:00", TestUtil.formatter), BigDecimal.valueOf(2)));
		jobList.add(TestUtil.createJob(2L, "Importação de dados da Base Legada", LocalDateTime.parse("2019-11-11 12:00:00", TestUtil.formatter), BigDecimal.valueOf(4)));		
		jobList.add(TestUtil.createJob(3L, "Importação de dados de integração", LocalDateTime.parse("2019-11-11 08:00:00", TestUtil.formatter), BigDecimal.valueOf(6)));
		jobList.add(TestUtil.createJob(4L, "Importação de csv", LocalDateTime.parse("2019-11-11 07:00:00", TestUtil.formatter), BigDecimal.valueOf(3.5)));
		jobList.add(TestUtil.createJob(5L, "Importação de dados de logs", LocalDateTime.parse("2019-11-11 08:00:00", TestUtil.formatter), BigDecimal.valueOf(5.5)));
		
		JanelaExecucao janelaExecucao = new JanelaExecucao(LocalDateTime.parse("2019-11-10 09:00:00", TestUtil.formatter), LocalDateTime.parse("2019-11-11 12:00:00", TestUtil.formatter));
		
		List<List<Long>> expectedList = new ArrayList<List<Long>>();
		list = new ArrayList<Long>();
		list.add(1L);
		list.add(3L);
		expectedList.add(list);
		list = new ArrayList<Long>();
		list.add(4L);
		list.add(2L);
		expectedList.add(list);
		list = new ArrayList<Long>();
		list.add(5L);
		expectedList.add(list);
		
		List<List<Long>> resultList = jobService.createScheduleList(jobList, janelaExecucao);
		
		assertNotNull(resultList);
		assertEquals(expectedList, resultList);		
	}
	
	@Test
	public void createScheduleFromJobList_WithPendingJob() {
		List<Long> list;		
		List<Job> jobList = new ArrayList<Job>();
				
		jobList.add(TestUtil.createJob(1L, "Importação de arquivos de fundos", LocalDateTime.parse("2019-11-10 12:00:00", TestUtil.formatter), BigDecimal.valueOf(2)));
		jobList.add(TestUtil.createJob(2L, "Importação de dados da Base Legada", LocalDateTime.parse("2019-11-11 09:00:00", TestUtil.formatter), BigDecimal.valueOf(4)));		
		jobList.add(TestUtil.createJob(3L, "Importação de dados de integração", LocalDateTime.parse("2019-11-11 08:00:00", TestUtil.formatter), BigDecimal.valueOf(6)));
		jobList.add(TestUtil.createJob(4L, "Importação de csv", LocalDateTime.parse("2019-11-11 07:00:00", TestUtil.formatter), BigDecimal.valueOf(3.5)));
		jobList.add(TestUtil.createJob(5L, "Importação de dados de logs", LocalDateTime.parse("2019-11-11 08:00:00", TestUtil.formatter), BigDecimal.valueOf(5.5)));
		//Job que ficará pendente por não ter espaço na janela de execução
		jobList.add(TestUtil.createJob(6L, "Importação de dados xml", LocalDateTime.parse("2019-11-11 09:00:00", TestUtil.formatter), BigDecimal.valueOf(4))); 
		
		JanelaExecucao janelaExecucao = new JanelaExecucao(LocalDateTime.parse("2019-11-10 09:00:00", TestUtil.formatter), LocalDateTime.parse("2019-11-11 12:00:00", TestUtil.formatter));
		
		List<List<Long>> expectedList = new ArrayList<List<Long>>();
		list = new ArrayList<Long>();
		list.add(1L);
		list.add(3L);
		expectedList.add(list);
		list = new ArrayList<Long>();
		list.add(4L);
		list.add(2L);
		expectedList.add(list);
		list = new ArrayList<Long>();
		list.add(5L);
		expectedList.add(list);
		
		List<List<Long>> resultList = jobService.createScheduleList(jobList, janelaExecucao);
		
		assertNotNull(resultList);
		assertEquals(expectedList, resultList);		
	}

}
