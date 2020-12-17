package com.starbugs.Core.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.starbugs.Core.DTO.ProjectRequest;
import com.starbugs.Core.Dao.ClientRepository;
import com.starbugs.Core.Dao.ProjectRepository;
import com.starbugs.Core.Model.Project;
import com.starbugs.Core.Model.StarBugsClient;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	
	@Autowired
	private ClientRepository clientRepository;
	

	public ProjectService(ProjectRepository projectRepository, ClientRepository clientRepository) {
		this.projectRepository = projectRepository;
		this.clientRepository = clientRepository;
		}

	public Project getProjectById(UUID id) throws Exception{
		return projectRepository.findById(id).orElseThrow(()-> new Exception("Project with id " + id.toString() + " not found."));
	}
	

	
	
	// Add a new Project...
	public List<Project> addProject( UUID client_id,  ProjectRequest projectRequest) throws Exception {

		StarBugsClient client = clientRepository.findById(client_id).orElseThrow(()-> new Exception("Client Not Found"));
		// Check if a project with the same name already exists...
		Project existingProjectWithSameName = projectRepository.findByNameAndCompany(projectRequest.getName(), client.getCompany());
		if(existingProjectWithSameName != null) throw new Exception("There is already a project with the same name at your company");
		Project project = new Project();
		project.setName(projectRequest.getName());
		project.setCompany(client.getCompany());
		project.setBusinessDomain(projectRequest.getDomain());	
		
		client.addProject(project);
		StarBugsClient updatedClient = clientRepository.save(client);
		return updatedClient.getProjects();
	}
	
	
	// Delete a Project...
	public void deleteProject(UUID project_id) throws Exception {

		projectRepository.deleteById(project_id);
		
	}
	
	public Project changeProjectName(UUID project_id, String newName) throws Exception{

		Project proj = this.getProjectById(project_id);
		proj.setName(newName);
		Project updatedProject = projectRepository.save(proj);
		return updatedProject;
		
		
	}
	
	public Project changeProjectDomain(UUID project_id, String newDomain) throws Exception{

		Project proj = this.getProjectById(project_id);
		proj.setBusinessDomain(newDomain);
		Project updatedProject = projectRepository.save(proj);
		return updatedProject;
		
		}
		
	
	
	
	
}
