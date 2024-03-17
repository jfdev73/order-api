package com.miranda.orderapi.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.miranda.orderapi.exceptions.GeneralServiceException;
import com.miranda.orderapi.exceptions.NoDataFoundException;
import com.miranda.orderapi.exceptions.ValidateServiceException;
import com.miranda.orderapi.utils.WrapperResponse;

import lombok.extern.slf4j.Slf4j;

//Esta clase va a definir como deben ser retornados los errores al usuario
@Slf4j
@ControllerAdvice
public class ErrorHandlerConfig extends ResponseEntityExceptionHandler {
	
	private WrapperResponse<?> response;
	
	public ErrorHandlerConfig() {
		this.response = new WrapperResponse<>();
		
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> all(Exception e, WebRequest request) {
		log.error(e.getMessage(),e);
		response.setMessage("Internal Server Error");
		
		//WrapperResponse<Void> response = new WrapperResponse<>("Internal Server Error", null);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ValidateServiceException.class)
	public ResponseEntity<?> validateServiceException(ValidateServiceException e, WebRequest request) {
		response.setMessage(e.getMessage());
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoDataFoundException.class)
	public ResponseEntity<?> noDataFoundException(NoDataFoundException e, WebRequest request) {
		response.setMessage(e.getMessage());
		
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(GeneralServiceException.class)
	public ResponseEntity<?> generalServiceException(GeneralServiceException e, WebRequest request) {
		response.setMessage("Internal Server Error");
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

}
