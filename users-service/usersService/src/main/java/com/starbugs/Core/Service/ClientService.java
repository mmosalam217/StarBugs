package com.starbugs.Core.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.starbugs.Core.Config.JwtProvider;
import com.starbugs.Core.Config.StarBugsRoles;
import com.starbugs.Core.DTO.NewUserDTO;
import com.starbugs.Core.DTO.SharedUserDetails;
import com.starbugs.Core.Dao.ClientRepository;
import com.starbugs.Core.Dao.CompanyRepository;
import com.starbugs.Core.Dao.ProjectRepository;
import com.starbugs.Core.Dao.UsersRepository;
import com.starbugs.Core.Exception.BadTokenException;
import com.starbugs.Core.Exception.ClientNotFoundException;
import com.starbugs.Core.Exception.UsernameConflictException;
import com.starbugs.Core.Exception.VerificationTokenInsertionException;
import com.starbugs.Core.Model.AppUser;
import com.starbugs.Core.Model.Company;
import com.starbugs.Core.Model.Project;
import com.starbugs.Core.Model.StarBugsClient;
import com.starbugs.Core.Model.VerificationMailDetails;
import com.starbugs.Core.Model.VerificationToken;

@Service
public class ClientService {

	private final String RESET_PASSWORD_EMAIL_TOPIC = "Reset_Password_Email";
	
	@Value("${client.user.defaultPassword}")
	private String defaultPassword;
	

	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private WorkspaceRemovalService workspaceRemovalService;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private MailProducer mailProducer;
	
	@Autowired
	private PasswordEncoder encoder;
	

	@Autowired
	private VerificationTokenService verificationTokenService;
	
	@Autowired
	private JwtProvider jwtUtils;
	

	
	public ClientService() {
	}


	public StarBugsClient getClientById(UUID id) throws  ClientNotFoundException {
		return clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException("Client not found with id: " + id.toString()));
	}
	
	// Add employee to your company..
	@Transactional
	public void enrollUserToCompany(UUID client_id, NewUserDTO userRequest) throws UsernameConflictException, ClientNotFoundException,
																				VerificationTokenInsertionException, BadTokenException, JsonProcessingException {

			AppUser clientEmp = this.createEmployee(client_id, userRequest);
			
			// Create userDto from the employee..
			SharedUserDetails userDetails = new SharedUserDetails(clientEmp.getId(), clientEmp.getClient().getId(),
						clientEmp.getUsername(), clientEmp.getAuthorities());
			
			// generate verification token and save in into DB...
			VerificationToken verificationToken = this.createToken(userDetails);
			
			// send verification email...
			this.sendVerificationEmail(verificationToken, clientEmp);
	}
	

	
	public AppUser createEmployee(UUID client_id, NewUserDTO userRequest) throws UsernameConflictException, ClientNotFoundException {
		//Check if the user already exists...
		Optional<AppUser> existingUser = usersRepository.findByUsername(userRequest.getUsername());
		
		if(existingUser.isPresent()) 
			throw new UsernameConflictException("Invalid Email, please user another email");
			
			StarBugsClient client = clientRepository.findById(client_id).orElseThrow(()-> new ClientNotFoundException("Client Not Found!"));
			AppUser user = new AppUser();
			user.setPassword(encoder.encode(defaultPassword));
			user.setDisplayName( "@" + userRequest.getFirstName() + "." + userRequest.getLastName());
			user.setClient(client);
			user.setFirstName(userRequest.getFirstName());
			user.setLastName(userRequest.getLastName());
			user.setUsername(userRequest.getUsername());
			user.setUserType("E");
			user.setCredentialsNonExpired(false);
			user.setEnabled(false);
			// set authorities...
			String[] authorities = userRequest.getRole() == "Admin"? StarBugsRoles.getStarBugsClientAdminUserRoles() : StarBugsRoles.getStarBugsEmployeeRoles();
			for(String auth: authorities) {
				user.addAuthority(auth);
			}
			return usersRepository.save(user);
	}
	
	
	public VerificationToken createToken(SharedUserDetails userDetails) throws VerificationTokenInsertionException {

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
	
	
	public void sendVerificationEmail(VerificationToken verificationToken, AppUser clientEmp) throws JsonProcessingException, BadTokenException {
		
		if(verificationToken != null && !verificationToken.getToken().isEmpty()) {
			VerificationMailDetails mailDetails = new VerificationMailDetails();
			mailDetails.setEmail(clientEmp.getUsername());
			mailDetails.setUsername(clientEmp.getFirstName() + " " + clientEmp.getLastName());
			mailDetails.setToken(verificationToken.getToken());
			ObjectMapper mapper = new ObjectMapper();
			String details = mapper.writeValueAsString(mailDetails);
			mailProducer.send(RESET_PASSWORD_EMAIL_TOPIC, details);
		}else {
			throw new BadTokenException("VerificationToken is invalid");
		}
	}
	
	// GET Company Members...
	public List<AppUser> getUsersByClient(UUID client_id){
		List<AppUser> employees = usersRepository.findByClient(client_id.toString());
		return employees;
	}
	
	// DELETE Users from company profile...
	
	public void deleteEmployee(UUID employee_id) throws Exception {
		// Get Employee to be deleted...
		AppUser employee = usersRepository.findById(employee_id).orElseThrow(()-> new Exception("User Not Found"));
		usersRepository.delete(employee);
	}
	
	// Get List of company Projects...
	public List<Project> getCompanyProjects(UUID company_id){
		Company company = companyRepository.getOne(company_id);
		return projectRepository.findByCompany(company);
	}
	
	// Set User as A Client Admin...
	public void assignAdmin(UUID client_id, UUID assignee_id) throws Exception {		
		AppUser assignee = usersRepository.findById(assignee_id).orElseThrow(() -> new Exception("Client Not Found!"));
		assignee.addAuthority("SB_CLIENT_ADMIN_X004");
		usersRepository.save(assignee);
	
	}
	
	
	// Unsubscribe From the Whole Service..
	public long unsubscribe(UUID client_id) throws Exception {

		StarBugsClient client = clientRepository.findById(client_id).orElseThrow(()-> new ClientNotFoundException("CLIENT_NOT_FOUND"));
		this.workspaceRemovalService.deleteWorkspace(client.getId());
		long numberOfDeletedEmployees = usersRepository.deleteByClient(client);
		
		clientRepository.delete(client);
		
		return numberOfDeletedEmployees;
		
	}
	
}
