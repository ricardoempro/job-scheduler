package br.com.vivo.jobs.jobscheduler.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import br.com.vivo.jobs.jobscheduler.model.JanelaExecucao;

/**
* Mapper que utiliza o texto (String) recebido na requisição feita para o endpoint da controller "JobController" em um objeto "JobListDto"
* para criar um objeto "JanelaExecucao" contendo as datas de início e fim.
* 
* @see {@link br.com.vivo.jobs.jobscheduler.dto.JobListDto}
* @see {@link br.com.vivo.jobs.jobscheduler.controller.JobController}
* @see {@link br.com.vivo.jobs.jobscheduler.model.JanelaExecucao}
* @author Ricardo Neves
* 
*/

public class JanelaExecucaoMapper {

	public static JanelaExecucao StringToJanelaExecucao(String janelaExecucao) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		return new JanelaExecucao(LocalDateTime.parse(janelaExecucao.substring(0, 19), formatter),
				LocalDateTime.parse(janelaExecucao.substring(24, 43), formatter));

	}

}
