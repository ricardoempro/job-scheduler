package br.com.vivo.jobs.jobscheduler.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler{
		
	 @Override
	   protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
	                 HttpHeaders headers, HttpStatus status, WebRequest request) {
		 
		 List<CustomErrorResponse> errors = new ArrayList<CustomErrorResponse>();
		 
		 List<String> validationList = ex.getBindingResult().getFieldErrors().stream().map(fieldError->fieldError.getDefaultMessage()).collect(Collectors.toList());
		 
		 validationList.forEach(message -> {
			errors.add(new CustomErrorResponse(LocalDateTime.now(), message, ex.getMessage()));		 
		 });
		 
		 
		 return new ResponseEntity<>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
	   }
}
