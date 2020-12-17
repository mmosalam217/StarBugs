package com.starbugs.Core.Exception;

import java.time.LocalDateTime;

import javax.persistence.EntityExistsException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import feign.FeignException;

@ControllerAdvice()
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(value = {EmailVerificationException.class})
	public ResponseEntity<?> handleEmailVerificationException(Throwable e){
	return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = {VerificationTokenExpiredException.class})
	public ResponseEntity<?> handleVerificationTokenExpiredException(Throwable e){
	return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
	}
	
	
	
	@ExceptionHandler(value = {VerificationTokenNotFoundException.class})
	public ResponseEntity<?> handleVerificationTokenNotFoundException(Throwable e){
	return new ResponseEntity<>(new ApiError(HttpStatus.NOT_FOUND.value(), e.getMessage(), LocalDateTime.now()), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = {VerificationTokenInsertionException.class})
	public ResponseEntity<?> handleVerificationTokenInsertionException(Throwable e){
	return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = {BadTokenException.class})
	public ResponseEntity<?> handleBadTokenException(Throwable e){
	return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = {ClientNotFoundException.class})
	public ResponseEntity<?> handleClientNotFoundException(Throwable e){
	return new ResponseEntity<>(new ApiError(HttpStatus.NOT_FOUND.value(), e.getMessage(), LocalDateTime.now()), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = {ResetPasswordMatchException.class})
	public ResponseEntity<?> handleResetPasswordMatchException(Throwable e){
	return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = {ResetPasswordTokenExpiredException.class})
	public ResponseEntity<?> handleResetPasswordTokenExpiredException(Throwable e){
	return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = {EntityExistsException.class})
	public ResponseEntity<?> handleEntityExistsException(Throwable e){
	return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = {ResetPasswordTokenNotFoundException.class})
	public ResponseEntity<?> handleResetPasswordTokenNotFoundException(Throwable e){
	return new ResponseEntity<>(new ApiError(HttpStatus.NOT_FOUND.value(), e.getMessage(), LocalDateTime.now()), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = TechnicalException.class)
	public ResponseEntity<?> handleTechnicalException(Throwable e){
		return new ResponseEntity<>(new ApiError(HttpStatus.SERVICE_UNAVAILABLE.value(), e.getMessage(), LocalDateTime.now()), HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	@ExceptionHandler(value = WorkspaceRemovalException.class)
	public ResponseEntity<?> handleWorkspaceRemovalException(Throwable e){
		return new ResponseEntity<>(new ApiError(HttpStatus.SERVICE_UNAVAILABLE.value(), e.getMessage(), LocalDateTime.now()), HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	@ExceptionHandler(value = FeignException.class)
	public ResponseEntity<?> handleFeignException(Throwable e){
		return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
	}
}
