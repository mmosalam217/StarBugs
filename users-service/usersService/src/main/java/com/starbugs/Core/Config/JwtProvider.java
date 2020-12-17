package com.starbugs.Core.Config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.starbugs.Core.DTO.SharedUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtProvider {

	@Value("${jwt.secret}")
	private String secret;
	
	private final long JWT_TOKEN_VALIDITY = 6 * 60 * 60;
	

	
	public JwtProvider() {
	}
	
	
	public String generateToken(SharedUserDetails userDetails) {
		Map<String, Object> claims = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		String details = null;
		try {
			 details = mapper.writeValueAsString(userDetails);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		claims.put("user", details);
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
	
	public boolean validateToken(String token, UserDetails userDetails) {
		
		return (!isExpired(token) && userDetails.getUsername().equals(getUsernameFromToken(token)));

	}
	

	

}
