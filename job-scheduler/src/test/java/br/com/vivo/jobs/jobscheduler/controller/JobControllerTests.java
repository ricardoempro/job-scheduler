package br.com.vivo.jobs.jobscheduler.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.vivo.jobs.jobscheduler.dto.JobDto;
import br.com.vivo.jobs.jobscheduler.util.TestUtil;

@SpringBootTest
@AutoConfigureMockMvc
public class JobControllerTests {
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	void createSchedule_ReturnStatus200() throws Exception {	 
		
		List<JobDto> jobDtolist = new ArrayList<JobDto>();
		
		jobDtolist.add(TestUtil.createJobDto(1L, "Importação de arquivos de fundos", LocalDateTime.parse("2019-11-10 12:00:00", TestUtil.formatter), "2 horas"));
		jobDtolist.add(TestUtil.createJobDto(2L, "Importação de dados da Base Legada", LocalDateTime.parse("2019-11-11 12:00:00", TestUtil.formatter), "4 horas"));
		jobDtolist.add(TestUtil.createJobDto(3L, "Importação de dados de integração", LocalDateTime.parse("2019-11-11 08:00:00", TestUtil.formatter), "6 horas"));		
		jobDtolist.add(TestUtil.createJobDto(4L, "Importação de csv", LocalDateTime.parse("2019-11-11 08:00:00", TestUtil.formatter), "5 horas 30 min"));		
		jobDtolist.add(TestUtil.createJobDto(5L, "Importação de dados de logs", LocalDateTime.parse("2019-11-11 07:00:00", TestUtil.formatter), "6 horas 30 min"));
		jobDtolist.add(TestUtil.createJobDto(6L, "Limpeza de dados de logs", LocalDateTime.parse("2019-11-11 17:00:00", TestUtil.formatter), "40 min"));
	  
	   mockMvc.perform(post("/api/v1/jobs")
	        .contentType("application/json")
	        .content(objectMapper.writeValueAsString(TestUtil.createJobListDtoValid( jobDtolist, "2019-11-10 09:00:00 até 2019-11-11 20:00:00"))))
	        .andExpect(status().isOk());
	}
	
	@Test
	void createScheduleRearrange_ReturnStatus200() throws Exception {	 
		
		List<JobDto> jobDtolist = new ArrayList<JobDto>();
		
		jobDtolist.add(TestUtil.createJobDto(1L, "Importação de arquivos de fundos", LocalDateTime.parse("2019-11-10 12:00:00", TestUtil.formatter), "2 horas"));
		jobDtolist.add(TestUtil.createJobDto(2L, "Importação de dados da Base Legada", LocalDateTime.parse("2019-11-11 12:00:00", TestUtil.formatter), "4 horas"));
		jobDtolist.add(TestUtil.createJobDto(3L, "Importação de dados de integração", LocalDateTime.parse("2019-11-11 08:00:00", TestUtil.formatter), "6 horas"));		
		jobDtolist.add(TestUtil.createJobDto(4L, "Importação de csv", LocalDateTime.parse("2019-11-11 07:00:00", TestUtil.formatter), "3 horas 30 min"));		
		jobDtolist.add(TestUtil.createJobDto(5L, "Importação de dados de logs", LocalDateTime.parse("2019-11-11 08:00:00", TestUtil.formatter), "5 horas"));
		jobDtolist.add(TestUtil.createJobDto(6L, "Importação de dados pendente", LocalDateTime.parse("2019-11-11 09:00:00", TestUtil.formatter), "7 horas"));
	  
	   mockMvc.perform(post("/api/v1/jobs")
	        .contentType("application/json")
	        .content(objectMapper.writeValueAsString(TestUtil.createJobListDtoValid( jobDtolist, "2019-11-10 09:00:00 até 2019-11-11 12:00:00"))))
	        .andExpect(status().isOk());
	}

}
