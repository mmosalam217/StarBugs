package com.starbugs.workspace.API;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.starbugs.workspace.DTO.AppDTO;
import com.starbugs.workspace.Exception.EntityExistsException;
import com.starbugs.workspace.Exception.EntityNotFoundException;
import com.starbugs.workspace.Model.AppComponent;
import com.starbugs.workspace.Model.Application;
import com.starbugs.workspace.Service.ApplicationService;

@RestController()
public class ApplicationController {

	@Autowired
	private ApplicationService applicationService;
	
	@PostMapping(value = "/projects/{project_id}/applications")
	public ResponseEntity<?> addApp(@RequestBody AppDTO app, @PathVariable("project_id") UUID project_id) throws EntityNotFoundException, EntityExistsException{
		
			Application application = applicationService.addApplicationToProject(app, project_id);
			return new ResponseEntity<>(application, HttpStatus.ACCEPTED);
	}
	
	// Update Application with ID...
	@RequestMapping(value = "/projects/applications/{application_id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateApplication(@PathVariable("application_id") UUID app_id, @RequestBody Application updates) throws EntityNotFoundException{
			Application updatedApp = applicationService.updateApp(app_id, updates);
			return new ResponseEntity<>(updatedApp, HttpStatus.ACCEPTED);
	}
	
	
	// Delete Application with ID...
		@RequestMapping(value = "/projects/applications/{application_id}", method = RequestMethod.DELETE)
		public ResponseEntity<?> deleteApplication(@PathVariable("application_id") UUID app_id) throws EntityNotFoundException{
			 	applicationService.deleteAppById(app_id);
			 	return ResponseEntity.ok("Application Deleted Successfully.");
	}
		
		// Add new component to an existing application..
		@RequestMapping(value = "/projects/applications/{application_id}/components", method = RequestMethod.POST)
		public ResponseEntity<?> addComponent(@PathVariable("application_id") UUID application_id, @RequestBody AppComponent componentDetails) throws EntityExistsException, EntityNotFoundException{
				Application updatedApplication = applicationService.addComponent(application_id, componentDetails);
				return ResponseEntity.ok(updatedApplication);
			
		}
		
		// Update a component of an existing application..
		@RequestMapping(value = "/projects/applications/{app_id}/components/{component_id}", method = RequestMethod.PUT)
		public ResponseEntity<?> updateComponent(@PathVariable("app_id") UUID app_id,
												 @PathVariable("component_id") UUID component_id,
												 @RequestBody AppComponent updates) throws EntityExistsException, EntityNotFoundException
		{
				AppComponent updatedComponent = applicationService.updateComponent(app_id, component_id, updates);
				return ResponseEntity.ok(updatedComponent);
			
		}
		
		// Delete a component by ID
		@RequestMapping(value = "/projects/applications/components/{component_id}/delete-component", method = RequestMethod.DELETE)
		public ResponseEntity<?> deleteComponent(@PathVariable("component_id") UUID component_id) throws EntityNotFoundException
		{
				applicationService.deleteComponent(component_id);
				return ResponseEntity.ok("Component Deleted Successfully.");
			
		}
}
