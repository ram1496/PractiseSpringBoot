package com.casestudy.pharma.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(PharmaException.class)
	public ResponseEntity<Map<String,String>> handlePharmaException(PharmaException ex){
		Map<String,String> errorResponse = new HashMap<>();
		errorResponse.put("errorCode", ex.getErrorCode());
		errorResponse.put("errorMessage", ex.getMessage());
		
		return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String,String>> handleGenericException(Exception ex){
		Map<String,String> errorResponse = new HashMap<>();
		errorResponse.put("errorCode", "500");
		errorResponse.put("errorMessage", ex.getMessage());
		
		return new ResponseEntity<>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR);

	}
	

}
