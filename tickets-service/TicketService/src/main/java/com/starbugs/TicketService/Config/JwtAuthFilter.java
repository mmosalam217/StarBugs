package com.starbugs.TicketService.Config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;




@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	@Autowired
	private JwtProvider jwt;

	
	public JwtAuthFilter() {}
	
	public JwtAuthFilter(JwtProvider jwt) {
		this.jwt = jwt;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = null; 
		String email= null;
		String authorizationHeader = request.getHeader("Authorization");
		
		if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			token = authorizationHeader.substring(7);
			email = jwt.getUsernameFromToken(token);
			// Check if there is an existing authentication session or if the email null..
			if(email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				
				// Get the company from DB and pack it into a userDetails object..
				UserDetails userDetails = jwt.getUserDetailsFromToken(token);
				
				// Check if the token still valid...
				if(jwt.validateToken(token, userDetails)) {
					// If everything is fine, create authentication session of type "UsernamePasswordAuthenticationToken"..
					// UsernamePasswordAuthenticationToken(userDetails, credentials, Collection authorities)
					// should be used with trusted AuthenticationManager or AuthenticationProvider implementations
					// because its sets the isAuthenticated as true..
					UsernamePasswordAuthenticationToken usernamePasswordToken =
							new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					
					usernamePasswordToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					// After configuring the authentication object, set the authentication object manually in the context...
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordToken);
				}else { 	System.out.println("Invalid token");}
			}else {
				System.out.println("NULLLL");
			}
			
		}else {
			System.out.println("No Header");
		}
		
	
		
		filterChain.doFilter(request, response);

	}
}
