package br.com.vivo.jobs.jobscheduler.valid;

import java.util.regex.Matcher;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.vivo.jobs.jobscheduler.mapper.JobMapper;

/**
* Classe que contém a validação da annotation @TempoEstimadoValid que valida o texto proveniente do campo tempoEstimado no objeto JobDto.
* 
* @see {@link br.com.vivo.jobs.jobscheduler.dto.JobDto}
* @author Ricardo Neves
* 
*/

public class TempoEstimadoValidator implements ConstraintValidator<TempoEstimadoValid, String>{
	
    @Override
    public void initialize(TempoEstimadoValid constraintAnnotation) {
        constraintAnnotation.value();
    }
 
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
    	Matcher matcherHora = JobMapper.PATTERN_HORA.matcher(value);
		Matcher matcherHoraMinuto = JobMapper.PATTERN_HORAMINUTO.matcher(value);
		Matcher matcherApenasMin = JobMapper.PATTERN_APENASMINUTO.matcher(value);
		
		if (matcherHora.matches() && matcherHora.groupCount() == 1) {
			return true;
		}

		if (matcherHoraMinuto.matches() && matcherHoraMinuto.groupCount() == 2) {			
			return true;
		}
		
		if (matcherApenasMin.matches() && matcherApenasMin.groupCount() == 1) {
			return true;
		}

		return false;

    }

}
