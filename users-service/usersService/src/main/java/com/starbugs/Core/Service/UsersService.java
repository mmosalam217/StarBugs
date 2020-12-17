package com.starbugs.Core.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.starbugs.Core.Config.JwtProvider;
import com.starbugs.Core.Config.StarBugsRoles;
import com.starbugs.Core.DTO.ClientDTO;
import com.starbugs.Core.DTO.ResetPasswordRequest;
import com.starbugs.Core.DTO.SharedUserDetails;
import com.starbugs.Core.DTO.UserDto;
import com.starbugs.Core.Dao.ClientRepository;
import com.starbugs.Core.Dao.SubscriptionsRepository;
import com.starbugs.Core.Dao.UsersRepository;
import com.starbugs.Core.Exception.BadTokenException;
import com.starbugs.Core.Exception.ClientNotFoundException;
import com.starbugs.Core.Exception.EmailVerificationException;
import com.starbugs.Core.Exception.ResetPasswordMatchException;
import com.starbugs.Core.Exception.ResetPasswordTokenExpiredException;
import com.starbugs.Core.Exception.ResetPasswordTokenNotFoundException;
import com.starbugs.Core.Exception.TechnicalException;
import com.starbugs.Core.Exception.UserNotFoundException;
import com.starbugs.Core.Exception.VerificationTokenExpiredException;
import com.starbugs.Core.Exception.VerificationTokenInsertionException;
import com.starbugs.Core.Exception.VerificationTokenNotFoundException;
import com.starbugs.Core.Model.AppUser;
import com.starbugs.Core.Model.Company;
import com.starbugs.Core.Model.StarBugsClient;
import com.starbugs.Core.Model.StarbugsSubscription;
import com.starbugs.Core.Model.VerificationMailDetails;
import com.starbugs.Core.Model.VerificationToken;

@Service
public class UsersService {
	@Value("${client.user.defaultPassword}")
	private String defaultPassword;
	
	private final String VERIFICATION_EMAIL_TOPIC = "Send_Verification_Email";
	

	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private SubscriptionsRepository subscriptionsRepository;
	
	@Autowired
	private WorkspaceCreatorService workspaceCreatorService;
	
	@Autowired
	private MailProducer mailProducer;
	
	@Autowired
	private PasswordEncoder encoder;
	

	@Autowired
	private VerificationTokenService verificationTokenService;
	
	@Autowired
	private JwtProvider jwtUtils;
	
	public UsersService(UsersRepository usersRepository,
			VerificationTokenService verificationTokenService, ClientRepository clientRepository,
			SubscriptionsRepository subscriptionsRepository,
			JwtProvider jwtUtils, MailProducer mailProducer) {
		this.usersRepository = usersRepository;
		this.verificationTokenService = verificationTokenService;
		this.subscriptionsRepository = subscriptionsRepository;
		this.clientRepository = clientRepository;
		this.jwtUtils = jwtUtils;
		this.mailProducer = mailProducer;
	
	}
	
	// GET LIST OF ALL USERS...
	public List<AppUser> getAllUsers(){
		return usersRepository.findAll();
	}
	
	// GET SINGLE USER BY ID...
	public AppUser getUserById(UUID id) {
		return usersRepository.getOne(id);
	}
	
	// GET UserDTO
	
	public UserDto getUserDTO(String username) {
		AppUser user = usersRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("USERNAME WRONG"));
		return new UserDto(user.getId(), user.getUsername(),
				user.getFirstName(), user.getLastName(), user.getDisplayName(), user.getClient().getId(), user.getAuthorities());
	}
	// GET USER BY EMAIL....
	
	public AppUser getUserByUsername(String email) {
		return usersRepository.findByUsername(email).orElseThrow(()-> new UsernameNotFoundException("USERNAME WRONG"));
	}
	
	// UPDATE USER...
	public void updateUser(UUID user_id, AppUser updates) throws Exception {
		AppUser user = usersRepository.findById(user_id).orElseThrow(()-> new Exception("User Not Found!"));
		user.setFirstName(updates.getFirstName());
		user.setLastName(updates.getLastName());
		user.setUsername(updates.getUsername());
		usersRepository.save(user);
	}
	
	// DELETE USER BY ID....
	public void removeUser(AppUser user) {
		
		usersRepository.delete(user);
		
		
	}
	
	
	// ADD USER AS CLIENT....
	@Transactional
	public StarBugsClient addClient(AppUser user, Company company, UUID subscription_id) throws ClientNotFoundException,
	VerificationTokenInsertionException, BadTokenException, TechnicalException   {
		// Check if email of root user exists...
		Optional<AppUser> existingUser = usersRepository.findByUsername(user.getUsername());
		
		if(existingUser.isPresent()) throw new EntityExistsException("Invalid Email, please user another email");
		// Create Client & Company Details....
			StarBugsClient newClient = this.createClient(company, subscription_id);	
	
		// Create workspace for the client...
			ClientDTO clientDTO = new ClientDTO(newClient.getId(), newClient.getCompany().getName());
			this.workspaceCreatorService.createWorkspace(clientDTO);
		
		// Add Root User Details.....
			AppUser rootUser = this.createRootUser(user, newClient);
			
		// Create userDto from the employee..
			SharedUserDetails userDetails = new SharedUserDetails(rootUser.getId(),
								rootUser.getClient().getId(), rootUser.getUsername(), rootUser.getAuthorities());
		// generate verification token and save in into db...
			VerificationToken verificationToken = this.createToken(userDetails);
		// Check if the token is generated and persist it into the db..
			this.sendVerificationEmail(verificationToken, rootUser);
		
			return newClient;	
	}
	
	
	// Create client...
	private StarBugsClient createClient(Company company, UUID subscription_id) throws ClientNotFoundException {
		StarBugsClient client = new StarBugsClient();
		client.setCompany(company);
		// Find Subscription and add it to the client....
		StarbugsSubscription subscription = subscriptionsRepository.findById(subscription_id).orElseThrow(()-> new ClientNotFoundException("Error finding Subscription"));
		client.setSubscription(subscription);
		client.setStatus("Pending");
		return this.clientRepository.save(client);
	}
	
	
	// Create root user...
	private AppUser createRootUser(AppUser user, StarBugsClient newClient) {
		user.setPassword(encoder.encode(user.getPassword()));
		String[] roles = StarBugsRoles.getStarBugsClientRootUserRoles();

		for( String role: roles) {
			user.addAuthority(role);
		}
		user.setDisplayName("@" + user.getFirstName() + " " + user.getLastName());
		user.setUserType("C");
		user.setClient(newClient);
		
		return this.usersRepository.save(user);
	}
	
	// Create Verification token...
	private VerificationToken createToken(SharedUserDetails userDetails) throws VerificationTokenInsertionException {

		VerificationToken verificationToken = null;
		try {
			String token = jwtUtils.generateToken(userDetails);
			verificationToken = new VerificationToken();
			verificationToken.setUser(userDetails.getId());
			verificationToken.setToken(token);
			verificationToken = verificationTokenService.insertToken(verificationToken);
			
		}catch(Exception e) {
			throw new VerificationTokenInsertionException(e.getLocalizedMessage());
		}
		return verificationToken;
		
	}
	
	
	// Send Verification Mail for client registration..
	public void sendVerificationEmail(VerificationToken verificationToken, AppUser rootUser) throws BadTokenException, TechnicalException {
		
		if(verificationToken != null && !verificationToken.getToken().isEmpty()) {
			VerificationMailDetails mailDetails = new VerificationMailDetails();
			mailDetails.setEmail(rootUser.getUsername());
			mailDetails.setUsername(rootUser.getFirstName() + " " + rootUser.getLastName());
			mailDetails.setToken(verificationToken.getToken());
			ObjectMapper mapper = new ObjectMapper();
			String details = null;
			try {
				details = mapper.writeValueAsString(mailDetails);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				throw new TechnicalException(e.getLocalizedMessage());
			}
			mailProducer.send(VERIFICATION_EMAIL_TOPIC, details);
		}else {
			throw new BadTokenException("VerificationToken is invalid");
		}
	}
	
	// ACTIVATE USER....
	@Transactional
	public void activateUser(String token) throws Exception, VerificationTokenExpiredException,
																	EmailVerificationException, 
																	VerificationTokenNotFoundException{
		if(token != null) {
			
			Boolean isExpired = jwtUtils.isExpired(token);
			VerificationToken tokenDetails = verificationTokenService.getTokenByTokenString(token);
			if(!isExpired && tokenDetails != null) {
				AppUser user = this.getUserById(tokenDetails.getUser());
				if(!user.isEnabled()) {
				user.setEnabled(true); 
				user.setAccountNonExpired(true);
				user.setAccountNonLocked(true);
				user.setCredentialsNonExpired(true);
				usersRepository.save(user);
				
				// Change Client Registration Status...
				StarBugsClient client = clientRepository.findById(user.getClient().getId()).orElseThrow(()-> new Exception("CLIENT_NOT_FOUND"));
				client.setStatus("Completed");
				clientRepository.save(client);
				}else {
					throw new EmailVerificationException("Email is already verified!");

				}
			}else {
				throw new VerificationTokenExpiredException("Token Expired!");

			}
		}else {
			throw new VerificationTokenNotFoundException("Token is missing!");
	
		}
	}
	
	

	

	

	

	
	// Reset password...
	@Transactional
	public void resetPassword(ResetPasswordRequest resetPasswordRequest, String token) throws ResetPasswordTokenNotFoundException,
																					ResetPasswordTokenExpiredException,
																					ResetPasswordMatchException, 
																					UserNotFoundException{
		// Check if the token is there..
		if(token == null  || token == "") {
			throw new ResetPasswordTokenNotFoundException("No Token Provided");
		}
		
		VerificationToken tokenDetails = verificationTokenService.getTokenByTokenString(token);
		boolean isExpired = jwtUtils.isExpired(token) || tokenDetails.isExpired();

		// Check if the token is not expired
		if(isExpired) throw new ResetPasswordTokenExpiredException("Reset-Token is Expired!");
		if(tokenDetails == null || tokenDetails.getToken() == "") throw new ResetPasswordTokenNotFoundException("No Entry Found for this token!");
		
		// Check if password inputs are matched otherwise proceed..
		if (!resetPasswordRequest.getNewPassword().equals(resetPasswordRequest.getNewPasswordConfirmation())) {
			throw new ResetPasswordMatchException("Password and Password-Confirmation are not matched!");
		}else {
			AppUser user = this.getUserById(tokenDetails.getUser());
			if(user == null) {
				throw new UserNotFoundException("User Not Found!");
			}else {
				user.setPassword(encoder.encode(resetPasswordRequest.getNewPassword()));
				user.setAccountNonExpired(true);
				user.setAccountNonLocked(true);
				user.setCredentialsNonExpired(true);
				user.setEnabled(true);
				
				usersRepository.save(user);
				tokenDetails.setExpired(true);
				verificationTokenService.updateToken(tokenDetails);
				
			}

		}
		
		
	}

	public List<AppUser> getUsersByClient(UUID id) {
		return this.usersRepository.findByClient(id.toString());
	}
	

	
	
}
