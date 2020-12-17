package com.starbugs.workspace.Exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice()
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(value = {EntityExistsException.class})
	public ResponseEntity<?> handleEntityExistsException(Throwable e){
		return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST.value(), e.getLocalizedMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = {EntityNotFoundException.class})
	public ResponseEntity<?> handleEntityNotFoundException(Throwable e){
		return new ResponseEntity<>(new ApiError(HttpStatus.NOT_FOUND.value(), e.getLocalizedMessage(), LocalDateTime.now()), HttpStatus.NOT_FOUND);
	}
	
}
