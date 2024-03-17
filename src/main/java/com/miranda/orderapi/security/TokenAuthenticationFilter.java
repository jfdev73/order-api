package com.miranda.orderapi.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.miranda.orderapi.entity.User;
import com.miranda.orderapi.exceptions.NoDataFoundException;
import com.miranda.orderapi.repository.UserRepository;
import com.miranda.orderapi.service.UserService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private UserService service;
	
	@Autowired
	private UserRepository repository;
	
	public TokenAuthenticationFilter(UserService service, UserRepository repository) {
		this.service = service;
		this.repository = repository;
	}
	
	public TokenAuthenticationFilter() {
		
	}
	
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			String jwt = getJwtFromRequest(request);

			if (StringUtils.hasText(jwt) && service.validateToken(jwt)) {
				String username = service.getUsernameFromToken(jwt);

				User user = repository.findByUserName(username)
						.orElseThrow(() -> new NoDataFoundException("No existe el usuario"));
				UserPrincipal principal = UserPrincipal.create(user);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal,
						null, principal.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authentication);
			}

		} catch (Exception e) {
			log.error("Error al autenticar al usuario",e);
		}
		
		filterChain.doFilter(request, response);

	}

	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
			return bearerToken.substring(7, bearerToken.length());
		return null;
	}

}
