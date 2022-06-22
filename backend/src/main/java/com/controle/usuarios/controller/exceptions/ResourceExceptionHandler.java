package com.controle.usuarios.controller.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.controle.usuarios.service.exceptions.DatabaseException;
import com.controle.usuarios.service.exceptions.ServiceException;



@RestControllerAdvice
public class ResourceExceptionHandler {
	@ExceptionHandler(ServiceException.class)
	public ResponseEntity<StandardError> resourceNotFound(ServiceException e, HttpServletRequest request) {
		String error = "Resource not found";
		HttpStatus status =  HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI()) ;
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request) {
		String error = "Resource not found";
		HttpStatus status =  HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI()) ;
		return ResponseEntity.status(status).body(err);
	}
}
