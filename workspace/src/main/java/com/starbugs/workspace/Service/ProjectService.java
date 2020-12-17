package com.starbugs.workspace.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.starbugs.workspace.DTO.NewProjectDTO;
import com.starbugs.workspace.DTO.ProjectDTO;
import com.starbugs.workspace.Dao.ProjectRepository;
import com.starbugs.workspace.Dao.WorkspaceRepository;
import com.starbugs.workspace.Exception.EntityExistsException;
import com.starbugs.workspace.Exception.EntityNotFoundException;
import com.starbugs.workspace.Model.Application;
import com.starbugs.workspace.Model.Project;
import com.starbugs.workspace.Model.Workspace;



@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private WorkspaceRepository workspaceRepository;
	
	@Autowired
	private ApplicationService applicationService;
	

	public ProjectService(ProjectRepository projectRepository, WorkspaceRepository workspaceRepository) {
		this.projectRepository = projectRepository;
		this.workspaceRepository = workspaceRepository;
		}

	public ProjectDTO getProjectDTOById(UUID id) throws EntityNotFoundException{
		Project project = projectRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Project with id " + id.toString() + " not found."));
		return this.createProjectDTO(project);
	}
	
	public Project getProjectById(UUID id) throws EntityNotFoundException {
		return projectRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Project with id " + id.toString() + " not found."));
	}
	public List<ProjectDTO> getByWorkspaceId(UUID Workspace_id) throws EntityNotFoundException{
		Workspace workspace = this.workspaceRepository.findById(Workspace_id).orElseThrow(()-> new EntityNotFoundException("Workspace not found"));
		List<Project> projects = this.projectRepository.findByWorkspace(workspace);
		List<ProjectDTO> projectDTOs = new ArrayList<>();
		projects.stream().forEach(proj->{
			ProjectDTO projectDTO = this.createProjectDTO(proj);
			projectDTOs.add(projectDTO);
		});
		return projectDTOs;
	}
	
	public List<Project> getByWorkspace(Workspace workspace) throws EntityNotFoundException{
		List<Project> projects = this.projectRepository.findByWorkspace(workspace);
		return projects;
	}
	
	public List<ProjectDTO> getByClient(UUID client_id) throws EntityNotFoundException{
		Workspace workspace = this.workspaceRepository.findByClientId(client_id).orElseThrow(()-> new EntityNotFoundException("Workspace not found"));
		List<Project> projects = this.projectRepository.findByWorkspace(workspace);
		List<ProjectDTO> projectDTOs = new ArrayList<>();
		projects.stream().forEach(proj->{
			ProjectDTO projectDTO = this.createProjectDTO(proj);
			projectDTOs.add(projectDTO);
		});
		return projectDTOs;
	}
	
	// Add a new Project...
	public Project addProject( UUID client_id,  NewProjectDTO projectRequest) throws EntityNotFoundException, EntityExistsException {

		Workspace workspace = workspaceRepository.findByClientId(client_id).orElseThrow(()-> new EntityNotFoundException("Workspace Not Found"));
		// Check if a project with the same name already exists...
		Optional<Project> existingProjectWithSameName = projectRepository.findByNameAndWorkspace(projectRequest.getName(), workspace);
		if(existingProjectWithSameName.isPresent()) throw new EntityExistsException("There is already a project with the same name at your company");
		Project project = new Project();
		project.setName(projectRequest.getName());
		project.setBusinessDomain(projectRequest.getDomain());	
		project.setWorkspace(workspace);
	
		return this.projectRepository.save(project);
	}
	
	
	// Delete a Project...
	public void deleteProject(UUID project_id) throws Exception {

		projectRepository.deleteById(project_id);
		
	}
	
	// Delete Project by Workspace...
	public void deleteProjectByWorkspace(Workspace workspace) {
		this.projectRepository.deleteByWorkspace(workspace);
	}
	
	public Project changeProjectName(UUID project_id, String newName) throws Exception{

		Project proj = this.getProjectById(project_id);
		proj.setName(newName);
		Project updatedProject = projectRepository.save(proj);
		return updatedProject;
		
		
	}
	
	public Project changeProjectDomain(UUID project_id, String newDomain) throws Exception{

		Project proj =  this.getProjectById(project_id);
		proj.setBusinessDomain(newDomain);
		Project updatedProject = projectRepository.save(proj);
		return updatedProject;
		
		}
		
	public ProjectDTO createProjectDTO(Project project) {
		ProjectDTO projectDTO = new ProjectDTO();
		List<Application> applications = this.applicationService.findByProject(project);
		projectDTO.setApplications(applications);
		projectDTO.setId(project.getId());
		projectDTO.setName(project.getName());
		return projectDTO;
	}
	
	
	
}
