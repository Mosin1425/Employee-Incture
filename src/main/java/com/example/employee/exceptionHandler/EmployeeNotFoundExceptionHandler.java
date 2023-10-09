package com.example.employee.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EmployeeNotFoundExceptionHandler {
	
	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<Object> handleEmployeeNotFoundException(EmployeeNotFoundException employeeNotFoundException){
		EmployeeException employeeException = new EmployeeException(
				employeeNotFoundException.getMessage()
				, employeeNotFoundException.getCause()
				, HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<Object>(employeeException, HttpStatus.NOT_FOUND);
	}
}
