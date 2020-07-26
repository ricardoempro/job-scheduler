package br.com.vivo.jobs.jobscheduler.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.vivo.jobs.jobscheduler.model.JanelaExecucao;
import br.com.vivo.jobs.jobscheduler.util.TestUtil;

@SpringBootTest
public class JanelaExecucaoMapperTests {
	
	private final String janelaExecucaotx = "2019-11-10 09:00:00 até 2019-11-11 12:00:00";

	@Test
	public void deveCriarJanelaExecucao() {
		JanelaExecucao janela = JanelaExecucaoMapper.StringtoJanelaExecucao(janelaExecucaotx);
		
		LocalDateTime inicio = LocalDateTime.parse(janelaExecucaotx.substring(0, 19), TestUtil.formatter);
		LocalDateTime fim = LocalDateTime.parse(janelaExecucaotx.substring(24, 43), TestUtil.formatter);
		
		assertNotNull(janela);
		assertEquals(janela.getDataHoraInicio().toString(), inicio.toString());
		assertEquals(janela.getDataHoraFim().toString(), fim.toString());
	}
}
