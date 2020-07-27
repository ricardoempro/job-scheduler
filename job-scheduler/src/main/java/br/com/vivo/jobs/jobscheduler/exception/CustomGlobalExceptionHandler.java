package br.com.vivo.jobs.jobscheduler.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
	 
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		CustomErrorResponse errorResponse = new CustomErrorResponse(LocalDateTime.now(), "Não é possível processar o JSON enviado!", ex.getLocalizedMessage());
		
		return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	 
	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
		
		CustomErrorResponse errorResponse = new CustomErrorResponse(LocalDateTime.now(), "Ocorreu um erro!", ex.getLocalizedMessage());

	    return new ResponseEntity<Object>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
