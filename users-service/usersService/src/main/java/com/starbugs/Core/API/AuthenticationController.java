package com.starbugs.Core.API;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.starbugs.Core.Config.JwtProvider;
import com.starbugs.Core.DTO.JWTRequest;
import com.starbugs.Core.DTO.JWTResponse;
import com.starbugs.Core.DTO.SharedUserDetails;
import com.starbugs.Core.DTO.UserDto;
import com.starbugs.Core.Service.UsersService;



@RestController
public class AuthenticationController {

	@Autowired
	private DaoAuthenticationProvider authenticationProvider;
	
	@Autowired
	private UsersService usersService;

	
	
	@Autowired
	private JwtProvider jwt;
	
	public AuthenticationController(DaoAuthenticationProvider authenticationProvider, JwtProvider jwt) {
		this.authenticationProvider = authenticationProvider;
		this.jwt = jwt;
	}
	
	public AuthenticationController() {
	}
	
	public void authenticate(String email, String password) throws Exception{
		try {
		Authentication auth =	authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		SecurityContextHolder.getContext().setAuthentication(auth);
		}catch(LockedException e) {
			e.printStackTrace();
			throw new Exception("USER LOCKED!", e);
		}catch(DisabledException e) {
			e.printStackTrace();
			throw new Exception("USER DISABLED!", e);
		}catch(BadCredentialsException e) {
			e.printStackTrace();
			throw new Exception("BAD CREDENTIALS!", e);

		}
	}
	
	@RequestMapping(value = "/users/auth/login", method = RequestMethod.POST)
	public ResponseEntity<?> login (@RequestBody JWTRequest request) throws Exception{
		try {
			
			authenticate(request.getEmail(), request.getPassword());
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			
		if(auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS")))
			throw new Exception("Wrong Username or Password");
		
			// Send Access Token if everything is valid....
			UserDto userDto = this.usersService.getUserDTO(auth.getName());

			String token = jwt.generateToken(new SharedUserDetails(userDto.getId(),
					userDto.getClientId(), userDto.getUsername(), userDto.getAuthorities()));
			
			return ResponseEntity.ok(new JWTResponse(token, userDto));
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getLocalizedMessage());
		}
		
		
	}
	
	

}
