package com.miranda.orderapi.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.miranda.orderapi.converter.UserConverter;
import com.miranda.orderapi.dto.LoginRequestDTO;
import com.miranda.orderapi.dto.LoginResponseDTO;
import com.miranda.orderapi.entity.User;
import com.miranda.orderapi.exceptions.*;
import com.miranda.orderapi.repository.UserRepository;
import com.miranda.orderapi.validator.UserValidator;

import java.security.Key;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

	
	  @Value("${jwt.password}") 
	  private String jwtSecret;
	 
	Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

	private UserRepository repository;

	private UserConverter converter;
	
	private PasswordEncoder passwordEncoder;

	public UserService(UserRepository repository, UserConverter converter,
			PasswordEncoder passwordEncoder) {
		this.repository = repository;
		this.converter = converter;
		this.passwordEncoder = passwordEncoder;

	}

	public User createUser(User user) {
		try {
			UserValidator.signup(user);
			Optional<User> userFind = repository.findByUserName(user.getUserName());

			if (userFind.isPresent())
				throw new ValidateServiceException("El nombre de usuario ya existe");
			String encoder =passwordEncoder.encode(user.getPassword());
			user.setPassword(encoder);
			return repository.save(user);

		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException();
		}
	}

	public LoginResponseDTO login(LoginRequestDTO request) {
		try {
			User user = repository.findByUserName(request.getUsername())
					.orElseThrow(() -> new ValidateServiceException("Usuario o password incorrectos"));
			
			if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) 
					throw new ValidateServiceException("Usuario o password incorrectos");
				/*
				 * if (!user.getPassword().equals(request.getPassword())) throw new
				 * ValidateServiceException("Usuario o password incorrectos");
				 */

			String token = createToken(user);
			
			
			return new LoginResponseDTO(converter.toDTO(user), token);

		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException();
		}

	}

	private String createToken(User user) {
		Date now = new Date();
		LocalDateTime expiryDateLDT = LocalDateTime.now();
		expiryDateLDT = expiryDateLDT.plusHours(1);
		Date expireDate = Date.from(expiryDateLDT.atZone(ZoneId.systemDefault()).toInstant());

		return Jwts.builder()
				.setSubject(user.getUserName())
				.setIssuedAt(now)
				.setExpiration(expireDate)
				.signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
				//.signWith(key)
				.compact();
				//.signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
				
	}
	
	
	public boolean validateToken(String token) {
		boolean isValid = false;
		try {
			Claims claims = Jwts.parserBuilder()
	                .setSigningKey(jwtSecret.getBytes())
	                .build()
	                .parseClaimsJws(token)
	                .getBody();
			if(!claims.isEmpty()) isValid = true;
			
			
		} catch (ExpiredJwtException e) {
			log.error(e.getMessage());
		}
		catch (MalformedJwtException e) {
			log.error(e.getMessage());
		}
		catch (JwtException e) {
			log.error(e.getMessage());
		}
		return isValid;
		
	}

	public String getUsernameFromToken(String token) {
		try {
			Claims claims = Jwts.parserBuilder()
	                .setSigningKey(jwtSecret.getBytes())
	                .build()
	                .parseClaimsJws(token)
	                .getBody();
			return claims.getSubject();
			
		} catch (Exception e ) {
			log.error(e.getMessage(), e);
			throw new ValidateServiceException("Invalid Token");
		
		}
		
	}
	
	

}
