package com.starbugs.Core.API;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.starbugs.Core.DTO.AddAppRequest;
import com.starbugs.Core.DTO.AddProjectResponse;
import com.starbugs.Core.DTO.DeleteProjectResponse;
import com.starbugs.Core.DTO.NewUserDTO;
import com.starbugs.Core.DTO.ProjectRequest;
import com.starbugs.Core.DTO.UpdateProjectResponse;
import com.starbugs.Core.DTO.UserEnrolledResponse;
import com.starbugs.Core.Exception.BadTokenException;
import com.starbugs.Core.Exception.ClientNotFoundException;
import com.starbugs.Core.Exception.UsernameConflictException;
import com.starbugs.Core.Exception.VerificationTokenInsertionException;
import com.starbugs.Core.Model.AppComponent;
import com.starbugs.Core.Model.AppUser;
import com.starbugs.Core.Model.Application;
import com.starbugs.Core.Model.Company;
import com.starbugs.Core.Model.Project;
import com.starbugs.Core.Service.ApplicationService;
import com.starbugs.Core.Service.ClientService;
import com.starbugs.Core.Service.CompanyService;
import com.starbugs.Core.Service.ProjectService;

@RestController
public class ClientController {
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private ProjectService projectService;
	

	@Autowired
	private ApplicationService appService;
	
	
	public ClientController() {
	}
	

	public ClientController(ClientService clientService, ProjectService projectService, ApplicationService appService) {

		this.clientService = clientService;
		this.projectService = projectService;
		this.appService = appService;
		
	}

	
	/*
	 * CLIENT EMPLOYEES OPERATIONS
	 */

	@PostMapping("/client/{id}/add-employee")
	public ResponseEntity<?> enrollUserToCompany(@PathVariable("id") UUID id, @RequestBody NewUserDTO user) throws JsonProcessingException, UsernameConflictException, ClientNotFoundException, VerificationTokenInsertionException, BadTokenException{
	

			clientService.enrollUserToCompany(id, user);
		
			return new ResponseEntity<>(new UserEnrolledResponse(HttpStatus.ACCEPTED.value(), "User Added Successfully. An Email has been sent to reset his password", HttpStatus.ACCEPTED), HttpStatus.ACCEPTED);

		
	}
	
	
	@RequestMapping(value = "/client/{id}/employees", method = RequestMethod.GET)
	public List<AppUser> getListOfClientEmployees(@PathVariable("id") UUID client_id) throws IOException{
	
		List<AppUser> employees = clientService.getUsersByClient(client_id);
		return employees;
	}
	

	/*COMPANY OPERATIONS*/
	@RequestMapping(value = "/client/{client_id}/company/{company_id}/update-company", method = RequestMethod.PUT)
	public ResponseEntity<?> updateCompany(@PathVariable("company_id") UUID company_id, @RequestBody Company updates){
		try {
			Company updatedCompany = companyService.updateCompany(company_id, updates);
			return ResponseEntity.ok(updatedCompany);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getLocalizedMessage());
		}
	}
	
	/*
	 * CLIENT PROJECTS OPERATIONS
	 * 
	 */
	@RequestMapping(value="/client/company/{id}/projects")
	public ResponseEntity<?> getProjects(@PathVariable("id") UUID id) {
			try {
				List<Project> projs =  this.clientService.getCompanyProjects(id);
				return ResponseEntity.ok(projs);
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.badRequest().body(e.getLocalizedMessage());
			}
	}
	
	@PostMapping("/client/{client_id}/add-project")
	public ResponseEntity<?> addProject(@PathVariable("client_id") UUID client_id, @RequestBody ProjectRequest projectRequest){
		 
		try {
			List<Project> projects = projectService.addProject(client_id, projectRequest);
			return  ResponseEntity.ok(new AddProjectResponse(projects, "Project Added Successfully."));
		} catch (Exception e) {
			return  ResponseEntity.badRequest().body(new AddProjectResponse(null, e.getLocalizedMessage()));
		}
		
	}
	
	@RequestMapping(value="/client/{client_id}/projects/{id}/change-name", method = RequestMethod.PUT)
	public ResponseEntity<?> changeProjectName(@PathVariable("id") UUID project_id, @RequestParam(name = "newName") String newName){
		
		try {
			Project updatedProject = projectService.changeProjectName(project_id, newName);
			return ResponseEntity.ok(new UpdateProjectResponse("Project-Name updated successfully.", updatedProject));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new UpdateProjectResponse("Failed to update project-name", null));
		}
	}
	
	@RequestMapping(value="/client/{client_id}/projects/{id}/change-domain", method = RequestMethod.PUT)
	public ResponseEntity<?> changeProjectDomain(@PathVariable("id") UUID project_id, @RequestParam(name = "newDomain") String newDomain){
		
		try {
			Project updatedProject = projectService.changeProjectDomain(project_id, newDomain);
			return ResponseEntity.ok(new UpdateProjectResponse("Project-domain updated successfully.", updatedProject));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new UpdateProjectResponse("Failed to update project-domain", null));
		}
	}
	
	
	@RequestMapping(value="/client/{client_id}/project/{project_id}/delete-project", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteProject(@PathVariable("project_id") UUID id){
		try {
			projectService.deleteProject(id);
			return new ResponseEntity<>(new DeleteProjectResponse(HttpStatus.NO_CONTENT.value(), "Project Deleted"), HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getLocalizedMessage());
		}
	}
	
	/*
	 * CLIENT APPLICATIONS OPERATIONS
	 * */
	
	// Add a new application to a project...
	@RequestMapping(value= "/client/{client_id}/project/{project_id}/add-app", method = RequestMethod.POST)
	public ResponseEntity<?> addApp(@RequestBody AddAppRequest appReq, @PathVariable("project_id") UUID project_id){
		
		try {
			Application proj = appService.addApplicationToProject(appReq, project_id);
			return ResponseEntity.ok(proj);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getLocalizedMessage());
		}
	}
	

	// Update Application with ID...
	@RequestMapping(value = "/client/{client_id}/applications/{application_id}/update-app", method = RequestMethod.PUT)
	public ResponseEntity<?> updateApplication(@PathVariable("application_id") UUID app_id, @RequestBody Application updates){
		try {
			Application updatedApp = appService.updateAppInformation(app_id, updates);
			return ResponseEntity.ok(updatedApp);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getLocalizedMessage());
		}
	}
	
	// Delete Application By ID.....
	@RequestMapping(value="/client/{client_id}/project/{project_id}/applications/{application_id}/delete-app", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteApplication(@PathVariable("project_id") UUID project_id, @PathVariable("application_id") UUID application_id){
		try {
			appService.deleteAppById(project_id, application_id);
			return ResponseEntity.ok("Application Deleted Successfully.");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getLocalizedMessage());
		}
	}
	
	/*
	 * CLIENT APPLICATION COMPONENTS OPERATIONS
	 * 
	 * */
	
	// Add new component to an existing application..
	@RequestMapping(value = "/client/{client_id}/applications/{application_id}/add-component", method = RequestMethod.POST)
	public ResponseEntity<?> addComponent(@PathVariable("application_id") UUID project_id, @PathVariable("application_id") UUID application_id, @RequestBody AppComponent componentDetails){
		try {
			Application updatedApplication = appService.addComponent(application_id, componentDetails);
			return ResponseEntity.ok(updatedApplication);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getLocalizedMessage());
		}
	}
	
	
	
	
	// Update a component of an existing application..
	@RequestMapping(value = "/client/{client_id}/application/{app_id}/components/{component_id}/update-component", method = RequestMethod.PUT)
	public ResponseEntity<?> updateComponent(@PathVariable("app_id") UUID app_id,
											 @PathVariable("component_id") UUID component_id,
											 @RequestBody AppComponent updates)
	{
		try {
			AppComponent updatedComponent = appService.updateComponent(app_id, component_id, updates);
			return ResponseEntity.ok(updatedComponent);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getLocalizedMessage());
		}
	}
	
	
	// Delete a component by ID
	// @Param project_id is required to verify delete permission..
	@RequestMapping(value = "/client/{client_id}/application/components/{component_id}/delete-component", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteComponent(@PathVariable("component_id") UUID component_id)
	{
		try {
			appService.deleteComponent(component_id);
			return ResponseEntity.ok("Component Deleted Successfully.");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "/client/{client_id}/unsubscribe", method = RequestMethod.DELETE)
	public ResponseEntity<?> unsubscribe(@PathVariable("client_id") UUID client_id){
		try {
			long clientDeletedUsers = clientService.unsubscribe(client_id);
			return ResponseEntity.ok("Suscription Deleted Susccessfully. Number of deleted Client Employees is: " + clientDeletedUsers);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getLocalizedMessage());
		}
	}
}
