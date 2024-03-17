package com.miranda.orderapi.utils;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WrapperResponse <T>{
	
	private boolean ok = false;
	
	private String message;
	
	private T body;
	
	
	/*
	 * public WrapperResponse(boolean ok, T body) { this.message = "success";
	 * this.ok = ok; this.body = body; }
	 */
	
	public WrapperResponse(T body) {
		this.message = "success";
		this.ok = true;
		this.body = body;
	}
	
	public ResponseEntity<WrapperResponse<T>> createResponse(HttpStatus status){
		return new ResponseEntity<>(this, status);
		
	}
	
	public ResponseEntity<WrapperResponse<T>> createResponse(){
		return new ResponseEntity<>(this, HttpStatus.OK);
		
	}

	
	
	



	
	
	
}
