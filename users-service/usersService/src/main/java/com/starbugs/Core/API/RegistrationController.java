package com.starbugs.Core.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.starbugs.Core.DTO.EmailVerificationResponse;
import com.starbugs.Core.DTO.RegistrationRequest;
import com.starbugs.Core.DTO.RegistrationResponse;
import com.starbugs.Core.Exception.BadTokenException;
import com.starbugs.Core.Exception.ClientNotFoundException;
import com.starbugs.Core.Exception.EmailVerificationException;
import com.starbugs.Core.Exception.TechnicalException;
import com.starbugs.Core.Exception.VerificationTokenExpiredException;
import com.starbugs.Core.Exception.VerificationTokenInsertionException;
import com.starbugs.Core.Exception.VerificationTokenNotFoundException;
import com.starbugs.Core.Model.StarBugsClient;
import com.starbugs.Core.Service.UsersService;

@RestController
public class RegistrationController {

	@Autowired
	private UsersService userService;
	
	public RegistrationController(UsersService userService) {
		this.userService = userService;
	}
	
	public RegistrationController() {
	}
	
	@PostMapping(value = "/subscribe")
	public ResponseEntity<?> registerClient(@RequestBody RegistrationRequest registrationRequest) throws ClientNotFoundException, VerificationTokenInsertionException,
																										 BadTokenException, TechnicalException{
		
		StarBugsClient newClient = userService.addClient(registrationRequest.getClientRootUser(), registrationRequest.getClientCompany(), registrationRequest.getSubscriptionId());
		return ResponseEntity.ok(new RegistrationResponse(newClient, "Client Added Successfully"));
		 
	}
	

	
	@PostMapping("/users/verify-email/{token}")
	public ResponseEntity<?> attempActivation(@PathVariable String token) throws VerificationTokenExpiredException,
																				 EmailVerificationException, 
																				 VerificationTokenNotFoundException, 
																				 Exception{
		userService.activateUser(token);
		return new ResponseEntity<EmailVerificationResponse>(new EmailVerificationResponse("Verification succeded!", HttpStatus.ACCEPTED.value()), HttpStatus.ACCEPTED);
		
	}
	
	
	
	
	
	
	

}
