package com.example.employee.exceptionHandler;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<String> noSuchElementException(NoSuchElementException ex){
		String message = ex.getMessage();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
	}
}
