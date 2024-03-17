package com.miranda.orderapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miranda.orderapi.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	public Optional<User> findByUserName(String username);

}
