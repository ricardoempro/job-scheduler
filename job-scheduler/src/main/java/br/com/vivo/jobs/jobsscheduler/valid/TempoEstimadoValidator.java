package br.com.vivo.jobs.jobsscheduler.valid;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TempoEstimadoValidator implements ConstraintValidator<TempoEstimadoValid, String>{
	
	private static final Pattern PATTERN_HORA = Pattern.compile("([0-9]+) horas?");
	private static final Pattern PATTERN_HORAMINUTO = Pattern.compile("([0-9]+) horas? ([0-9]+) min");
	private static final Pattern PATTERN_APENASMINUTO = Pattern.compile("([0-9]+) min");
	 
    @Override
    public void initialize(TempoEstimadoValid constraintAnnotation) {
        constraintAnnotation.value();
    }
 
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
    	Matcher matcherHora = PATTERN_HORA.matcher(value);
		Matcher matcherHoraMinuto = PATTERN_HORAMINUTO.matcher(value);
		Matcher matcherApenasMin = PATTERN_APENASMINUTO.matcher(value);
		
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
