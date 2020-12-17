package com.starbugs.Core.API;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.starbugs.Core.DTO.ResetPasswordConfirmationResponse;
import com.starbugs.Core.DTO.ResetPasswordRequest;
import com.starbugs.Core.DTO.StarBugsWorkspace;
import com.starbugs.Core.DTO.WorkspaceResponse;
import com.starbugs.Core.Exception.ClientNotFoundException;
import com.starbugs.Core.Exception.ResetPasswordMatchException;
import com.starbugs.Core.Exception.ResetPasswordTokenExpiredException;
import com.starbugs.Core.Exception.ResetPasswordTokenNotFoundException;
import com.starbugs.Core.Exception.UserNotFoundException;
import com.starbugs.Core.Model.AppUser;
import com.starbugs.Core.Model.StarBugsClient;
import com.starbugs.Core.Service.ClientService;
import com.starbugs.Core.Service.UsersService;

@RestController
public class UserController {

	@Autowired
	private UsersService usersService;
	
	@Autowired
	private ClientService clientService;
	

	public UserController(UsersService usersService, ClientService clientService) {
		this.usersService = usersService;
		this.clientService = clientService;
	}
	
	
	


	@RequestMapping("/users/{id}")
	public AppUser getUserById(@PathVariable("id")  UUID id) {
		return usersService.getUserById(id);
	}
	
	@RequestMapping(value = "/users/reset-password", method = RequestMethod.PUT)
	public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest, @RequestParam("token") String token) throws ResetPasswordTokenNotFoundException,
		ResetPasswordTokenExpiredException, ResetPasswordMatchException, UserNotFoundException{
		
			 usersService.resetPassword(resetPasswordRequest, token);
			 return new ResponseEntity<>(new ResetPasswordConfirmationResponse(HttpStatus.ACCEPTED.value(), "Password reseted successfully", HttpStatus.ACCEPTED), HttpStatus.ACCEPTED);
		
		
	}
	
	@RequestMapping(value = "/users/workspace/{client_id}", method = RequestMethod.GET)
	public ResponseEntity<?> loadUserWorkspace(@PathVariable("client_id") UUID client_id) throws ClientNotFoundException{
	
			StarBugsClient client = clientService.getClientById(client_id);
			return new ResponseEntity<>(new WorkspaceResponse(HttpStatus.ACCEPTED.value(), "Workspace Loaded Successfully",
										HttpStatus.ACCEPTED, new StarBugsWorkspace(client.getId(), client.getCompany(), client.getProjects())), HttpStatus.ACCEPTED);
	
	}
	
	@RequestMapping(value = "/users/client/{client_id}/employees", method = RequestMethod.GET)
	public ResponseEntity<?> getClientUsers(@PathVariable("client_id") UUID id){
		List<AppUser> users = this.usersService.getUsersByClient(id);
		return new ResponseEntity<>(users, HttpStatus.ACCEPTED);
	}
	

}
