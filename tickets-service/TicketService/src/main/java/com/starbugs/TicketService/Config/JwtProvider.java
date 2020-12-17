package com.starbugs.TicketService.Config;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.starbugs.TicketService.DTO.SharedUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtProvider {

	@Value("${jwt.secret}")
	private String secret;
	
	private final long JWT_TOKEN_VALIDITY = 24 * 60 * 60;
	

	
	public JwtProvider() {
	}
	
	
	public String generateToken(SharedUserDetails userDetails) {
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("user", userDetails.toString());
		return doGenerateToken(claims, userDetails.getUsername());
		
	}
	
	private String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	}
	
	private Claims getClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
	
	private <T> T getClaim(String token, Function<Claims, T> resolve) {
		Claims claims = getClaimsFromToken(token);
		return resolve.apply(claims);
		
	}
	
	public boolean isExpired(String token) {
		Date exp = getClaim(token, Claims::getExpiration);
		return exp.before(new Date(System.currentTimeMillis()));
	}
	
	public String getUsernameFromToken(String token) {
		return getClaim(token, Claims::getSubject);
		
	}
	

	public UserDetails getUserDetailsFromToken(String token) {
		ObjectMapper mapper = new ObjectMapper();
		
				
		  SharedUserDetails user=null;
		try {
			user = mapper.readValue(getClaimsFromToken(token).get("user").toString(), SharedUserDetails.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		// build Authorities
		ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		user.getAuthorities().stream().forEach(auth -> authorities.add(new SimpleGrantedAuthority(auth.getAuthority())));
		return new User(user.getUsername(), "PLACEHOLDER", authorities);
		
	}
	
	public boolean validateToken(String token, UserDetails userDetails) {
		
		return (!isExpired(token) && userDetails.getUsername().equals(getUsernameFromToken(token)));

	}
	

	

}
