package com.starbugs.Core.API;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.starbugs.Core.Model.Project;
import com.starbugs.Core.Service.ProjectService;

@RestController
public class ProjectController {

	@Autowired
	private ProjectService projectService;
	
	public ProjectController(ProjectService projectService) {
		this.projectService = projectService;
	}
	

	
	@RequestMapping(value="/company/projects/{id}")
	public ResponseEntity<?> getProject(@PathVariable("id") UUID id) {
			try {
				Project proj =  projectService.getProjectById(id);
				return ResponseEntity.ok(proj);
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.badRequest().body(e.getLocalizedMessage());
			}
	}
	


	

	
	

}
