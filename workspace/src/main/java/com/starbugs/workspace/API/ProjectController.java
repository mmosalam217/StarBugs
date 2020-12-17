package com.starbugs.workspace.API;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.starbugs.workspace.DTO.NewProjectDTO;
import com.starbugs.workspace.DTO.ProjectDTO;
import com.starbugs.workspace.Exception.EntityExistsException;
import com.starbugs.workspace.Exception.EntityNotFoundException;
import com.starbugs.workspace.Model.Project;
import com.starbugs.workspace.Service.ProjectService;

@RestController()
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	
	@GetMapping("/projects/{id}")
	public ResponseEntity<?> getProject(@PathVariable("id") UUID id) throws EntityNotFoundException, EntityExistsException{
			ProjectDTO project = this.projectService.getProjectDTOById(id);
			return new ResponseEntity<>(project, HttpStatus.ACCEPTED);
		
	}
	
	//Get Projects by client...
	@GetMapping("/projects")
	public ResponseEntity<?> getClientProjects(@RequestParam("client_id") UUID client_id) throws EntityNotFoundException, EntityExistsException{
		 
		List<ProjectDTO> projects = this.projectService.getByClient(client_id);
		return new ResponseEntity<>(projects, HttpStatus.ACCEPTED);
	
	}
	
	@PostMapping("/projects")
	public ResponseEntity<?> addProject(@RequestParam("client_id") UUID client_id, @RequestBody NewProjectDTO projectRequest) throws EntityNotFoundException, EntityExistsException{
		 
			Project project = this.projectService.addProject(client_id, projectRequest);
			return new ResponseEntity<>(project, HttpStatus.ACCEPTED);
		
	}
	
	@PutMapping("/projects/{id}/change-name")
	public ResponseEntity<?> updateProjectName(@PathVariable("id") UUID id, @RequestParam("name") String name) throws Exception{
		 
		Project project = this.projectService.changeProjectName(id, name);
		return new ResponseEntity<>(project, HttpStatus.ACCEPTED);
	
	}
	
	@PutMapping("/projects/{id}/change-domain")
	public ResponseEntity<?> updateProjectDomain(@PathVariable("id") UUID id, @RequestParam("domain") String domain) throws Exception{
		 
		Project project = this.projectService.changeProjectDomain(id, domain);
		return new ResponseEntity<>(project, HttpStatus.ACCEPTED);
	
	}
	
	@DeleteMapping("/projects/{id}")
	public ResponseEntity<?> deleteProject(@PathVariable("id") UUID id) throws Exception{
		this.projectService.deleteProject(id);
		return ResponseEntity.noContent().build();
	
	}
}
