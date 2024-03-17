package com.miranda.orderapi.converter;

import org.springframework.stereotype.Component;

import com.miranda.orderapi.dto.SignupRequestDTO;
import com.miranda.orderapi.dto.UserDTO;
import com.miranda.orderapi.entity.User;

@Component
public class UserConverter implements UtilConverter<User, UserDTO>{

	@Override
	public UserDTO toDTO(User entity) {
		if(entity ==null) return null;
		return UserDTO.builder()
				.id(entity.getId())
				.username(entity.getUserName())
				.build();
	}

	@Override
	public User toEntity(UserDTO dto) {
		if(dto ==null) return null;
		return User.builder()
				.id(dto.getId())
				.userName(dto.getUsername())
				.build();
	}
	
	public User signup(SignupRequestDTO dto) {
		if(dto ==null) return null;
		return User.builder()
				.userName(dto.getUsername())
				.password(dto.getPassword())
				.build();
		
	}
	
	

}
