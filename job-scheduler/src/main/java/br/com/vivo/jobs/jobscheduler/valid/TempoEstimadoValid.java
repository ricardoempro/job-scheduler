package br.com.vivo.jobs.jobscheduler.valid;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
 

@Target({FIELD}) //A anotação apenas pode ser utilizada em fields
@Retention(RUNTIME) //Essa validação é apenas em tempo de execução
@Constraint(validatedBy = TempoEstimadoValidator.class)
public @interface TempoEstimadoValid {
	
	String message() default "Valor inválido";
	 
    Class<?>[] groups() default {};
 
    Class<? extends Payload>[] payload() default {};
 
    String value() default "";

}
