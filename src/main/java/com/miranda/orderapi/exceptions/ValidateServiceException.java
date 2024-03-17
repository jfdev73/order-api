package com.miranda.orderapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ValidateServiceException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ValidateServiceException() {
		super();
		
	}

	public ValidateServiceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public ValidateServiceException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public ValidateServiceException(String message) {
		super(message);
		
	}

	public ValidateServiceException(Throwable cause) {
		super(cause);
		
	}

}
