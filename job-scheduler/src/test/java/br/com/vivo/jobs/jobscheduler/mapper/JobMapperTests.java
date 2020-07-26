package br.com.vivo.jobs.jobscheduler.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.vivo.jobs.jobscheduler.dto.JobDto;
import br.com.vivo.jobs.jobscheduler.model.Job;
import br.com.vivo.jobs.jobscheduler.util.TestUtil;

@SpringBootTest
public class JobMapperTests {
	
	@Test
	public void createJobTempoEstimadoHoraMinuto() {
		JobDto jobDto = TestUtil.createJobDto(1L, "Descrição teste", LocalDateTime.now(), "2 horas 30 min");
		Job job =  JobMapper.dtoToModelObject(jobDto);
		
		assertNotNull(job);
		assertEquals(job.getId(), jobDto.getId());
		assertEquals(job.getDescricao(), jobDto.getDescricao());
		assertEquals(job.getDataMaximaConclusao(), jobDto.getDataMaximaConclusao());
		assertEquals(job.getTempoEstimado(), BigDecimal.valueOf(2.50).setScale(2,RoundingMode.UP));
	}
	
	@Test
	public void createJobTempoEstimadoHora() {
		JobDto jobDto = TestUtil.createJobDto(1L, "Descrição teste", LocalDateTime.now(), "3 horas");
		Job job =  JobMapper.dtoToModelObject(jobDto);
		
		assertNotNull(job);
		assertEquals(job.getId(), jobDto.getId());
		assertEquals(job.getDescricao(), jobDto.getDescricao());
		assertEquals(job.getDataMaximaConclusao(), jobDto.getDataMaximaConclusao());
		assertEquals(job.getTempoEstimado(), BigDecimal.valueOf(3.00).setScale(2,RoundingMode.UP));
	}
	
	@Test
	public void createJobTempoEstimadoMinuto() {
		JobDto jobDto = TestUtil.createJobDto(1L, "Descrição teste", LocalDateTime.now(), "35 min");
		Job job =  JobMapper.dtoToModelObject(jobDto);
		
		assertNotNull(job);
		assertEquals(job.getId(), jobDto.getId());
		assertEquals(job.getDescricao(), jobDto.getDescricao());
		assertEquals(job.getDataMaximaConclusao(), jobDto.getDataMaximaConclusao());
		assertEquals(job.getTempoEstimado(), BigDecimal.valueOf(0.59).setScale(2,RoundingMode.UP));
	}

}
