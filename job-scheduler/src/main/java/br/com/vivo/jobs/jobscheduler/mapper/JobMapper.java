package br.com.vivo.jobs.jobscheduler.mapper;

import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.vivo.jobs.jobscheduler.dto.JobDto;
import br.com.vivo.jobs.jobscheduler.model.Job;

public class JobMapper {

	public static final Pattern PATTERN_HORA = Pattern.compile("([0-9]+) horas?");
	public static final Pattern PATTERN_HORAMINUTO = Pattern.compile("([0-9]+) horas? ([0-9]+) min");
	public static final Pattern PATTERN_APENASMINUTO = Pattern.compile("([0-9]+) min");

	public static Job dtoToModelObject(JobDto jobDto) {
		return new Job(jobDto.getId(), jobDto.getDescricao(), jobDto.getDataMaximaConclusao(),
				criaTempoExtimado(jobDto.getTempoEstimadoStr()));
	}

	private static LocalTime criaTempoExtimado(String tempoExtimadoStr) {
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

		return LocalTime.of(horas, minutos, 0);
	}

}
