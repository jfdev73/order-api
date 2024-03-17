package com.miranda.orderapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.miranda.orderapi.converter.UserConverter;
import com.miranda.orderapi.dto.LoginRequestDTO;
import com.miranda.orderapi.dto.LoginResponseDTO;
import com.miranda.orderapi.dto.SignupRequestDTO;
import com.miranda.orderapi.dto.UserDTO;
import com.miranda.orderapi.entity.User;
import com.miranda.orderapi.service.UserService;
import com.miranda.orderapi.utils.WrapperResponse;

@RestController
public class UserController {
	
	private UserService service;
	
	private UserConverter converter;
	
	
	
	public UserController(UserService service,UserConverter converter) {
		this.service = service;
		this.converter = converter;
	}


	@PostMapping("/signup")
	public ResponseEntity<WrapperResponse<UserDTO>> signup(@RequestBody SignupRequestDTO request){
		User user  = service.createUser(converter.signup(request));
		 return new WrapperResponse<>(converter.toDTO(user)).createResponse();
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<WrapperResponse<LoginResponseDTO>> login (@RequestBody LoginRequestDTO request){
		LoginResponseDTO response = service.login(request);
		return new WrapperResponse<>(response).createResponse();
	}
	

}
