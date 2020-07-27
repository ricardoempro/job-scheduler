package br.com.vivo.jobs.jobscheduler.mapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.vivo.jobs.jobscheduler.dto.JobDto;
import br.com.vivo.jobs.jobscheduler.model.Job;

/**
* Mapper que utiliza o DTO JobDto, recebido em uma lista (List<Job>) na requisição feita para o endpoint da controller "JobController", em um objeto "JobListDto"
* e processa o tempo estimado, provido em texto, para um objeto BigDecimal que representará o tempo informado.
* 
* @see {@link br.com.vivo.jobs.jobscheduler.dto.JobListDto}
* @see {@link br.com.vivo.jobs.jobscheduler.controller.JobController}
* @see {@link br.com.vivo.jobs.jobscheduler.model.Job}
* @author Ricardo Neves
* 
*/

public class JobMapper {

	public static final Pattern PATTERN_HORA = Pattern.compile("([0-9]+) horas?");
	public static final Pattern PATTERN_HORAMINUTO = Pattern.compile("([0-9]+) horas? ([0-9]+) min");
	public static final Pattern PATTERN_APENASMINUTO = Pattern.compile("([0-9]+) min");

	public static Job dtoToModelObject(JobDto jobDto) {
		return new Job(jobDto.getId(), jobDto.getDescricao(), jobDto.getDataMaximaConclusao(),
				criaTempoExtimado(jobDto.getTempoEstimadoStr()));
	}

	private static BigDecimal criaTempoExtimado(String tempoExtimadoStr) {
		Matcher matcherHora = PATTERN_HORA.matcher(tempoExtimadoStr);
		Matcher matcherHoraMinuto = PATTERN_HORAMINUTO.matcher(tempoExtimadoStr);
		Matcher matcherApenasMin = PATTERN_APENASMINUTO.matcher(tempoExtimadoStr);
		int horas = 0;
		int minutos = 0;

		if (matcherHora.matches() && matcherHora.groupCount() == 1) {
			horas = Integer.parseInt(matcherHora.group(1).trim());
		}

		if (matcherHoraMinuto.matches() && matcherHoraMinuto.groupCount() == 2) {
			horas = Integer.parseInt(matcherHoraMinuto.group(1).trim());
			minutos = Integer.parseInt(matcherHoraMinuto.group(2).trim());
		}
		
		if (matcherApenasMin.matches() && matcherApenasMin.groupCount() == 1) {
			minutos = Integer.parseInt(matcherApenasMin.group(1).trim());
		}
		

		return new BigDecimal(Double.valueOf(horas) + (Double.valueOf(minutos)/60)).setScale(2,RoundingMode.UP);
	}

}
