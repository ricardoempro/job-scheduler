package br.com.vivo.jobs.jobscheduler.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import br.com.vivo.jobs.jobscheduler.model.JanelaExecucao;

public class JanelaExecucaoMapper {

	public static JanelaExecucao StringtoJanelaExecucao(String janelaExecucao) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		return new JanelaExecucao(LocalDateTime.parse(janelaExecucao.substring(0, 19), formatter),
				LocalDateTime.parse(janelaExecucao.substring(24, 43), formatter));

	}

}
