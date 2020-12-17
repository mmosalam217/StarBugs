package com.starbugs.workspace.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.starbugs.workspace.DTO.ClientDTO;
import com.starbugs.workspace.DTO.ProjectDTO;
import com.starbugs.workspace.DTO.WorkspaceDTO;
import com.starbugs.workspace.Dao.WorkspaceRepository;
import com.starbugs.workspace.Exception.EntityExistsException;
import com.starbugs.workspace.Exception.EntityNotFoundException;
import com.starbugs.workspace.Model.Application;
import com.starbugs.workspace.Model.Project;
import com.starbugs.workspace.Model.Workspace;

@Service
public class WorkspaceService {

	@Autowired
	private WorkspaceRepository workspaceRepository;
	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private ProjectService projectService;
	
	
	public Workspace create(ClientDTO client) throws EntityExistsException {
		Optional<Workspace> existing = this.workspaceRepository.findByClientId(client.getId());
		if(existing.isPresent()) throw new EntityExistsException("Workspace for client with id: " + client.getId().toString() + "already exists");
		Workspace workspace = new Workspace();
		workspace.setClientId(client.getId());
		workspace.setName(client.getName());
		return this.workspaceRepository.save(workspace);
	}
	
	public Workspace findByClient(UUID client_id) throws EntityNotFoundException {
		Workspace workspace = this.workspaceRepository.findByClientId(client_id)
		.orElseThrow(()-> new EntityNotFoundException("Workspace for client with id: " + client_id.toString() + "not found"));
		return workspace;
	}
	
	public Workspace findById(UUID id) throws EntityNotFoundException {
		Workspace workspace = this.workspaceRepository.findById(id)
		.orElseThrow(()-> new EntityNotFoundException("Workspace for client with id: " + id.toString() + "not found"));
		return workspace;
	}
	
	@Transactional
	public void deleteByClient(UUID client_id) throws EntityNotFoundException {
		// find workspace through client id...
		Workspace workspace = this.workspaceRepository.findByClientId(client_id)
			.orElseThrow(()-> new EntityNotFoundException("Workspace for client with id: " + client_id.toString() + "not found"));
		// Get all projects associated with that client's workspace..
		List<Project> projects = this.projectService.getByWorkspace(workspace);
		
		// Delete Applications of each project...
		projects.stream().forEach(proj->{
			this.applicationService.deleteApplicationByProject(proj);
		});
		
		// Delete all workspace projects...
		this.projectService.deleteProjectByWorkspace(workspace);
		
		// Delete tickets associated with that client....
		
		// finally delete the workspace itself...
		this.workspaceRepository.delete(workspace);
	}
	
	@Transactional
	public WorkspaceDTO loadWorkspace(UUID client_id) throws EntityNotFoundException {
		// Get Workspace with client_id...
		Workspace workspace = this.findByClient(client_id);
		// Get all client projects and applications to form the ProjectDTOs and inject them into the workspace..
		List<Project> projects = this.projectService.getByWorkspace(workspace);
		List<ProjectDTO> projectDTOs = new ArrayList<>();
		projects.stream().forEach(proj-> {
			List<Application> apps = this.applicationService.findByProject(proj);
			ProjectDTO projDTO = new ProjectDTO();
			projDTO.setId(proj.getId());
			projDTO.setName(proj.getName());
			projDTO.setApplications(apps);
			projectDTOs.add(projDTO);
		});
		
		ClientDTO client = new ClientDTO(workspace.getClientId(), workspace.getName());
		WorkspaceDTO workspaceDTO = new WorkspaceDTO();
		workspaceDTO.setClient(client);
		workspaceDTO.setProjects(projectDTOs);
		return workspaceDTO;
	}
}
