package com.miranda.orderapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.miranda.orderapi.security.RestAuthenticationEntryPoint;
import com.miranda.orderapi.security.TokenAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public TokenAuthenticationFilter createTokenAuthenticationFilter() {
		return new TokenAuthenticationFilter();
	}
	
	@Bean
	public PasswordEncoder createPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain (HttpSecurity http) throws Exception{
	http
		.cors()
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
	.csrf()
		.disable()
	.formLogin()
		.disable()
	.httpBasic()
		.disable()
	.exceptionHandling()
		.authenticationEntryPoint(new RestAuthenticationEntryPoint())
		.and()
	.authorizeHttpRequests()
		.antMatchers("/",
				"/error",
				"/favicon.ico",
				"/**/*.png",
				"/**/*.gif",
				"/**/*.svg",
				"/**/*.jpg",
				"/**/*.html",
				"/**/*.css",
				"/**/*.js",
				"/**/*.woff2"
				).permitAll()
		.antMatchers(
                "/v2/api-docs",
                "/webjars/**",
                 "/swagger-resources",
                 "/swagger-resources/**",
                 "/swagger-ui",
                "/swagger-resources/configuration/ui", 
                "/swagger-resources/configuration/security"
                )
				
		.permitAll()
		.antMatchers(
				"/login",
				"/signup"
				)
		.permitAll()
		.anyRequest()
			.authenticated();
	http.addFilterBefore(createTokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	return http.build();
	
	}
	
	

}
