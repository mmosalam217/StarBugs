package com.starbugs.workspace.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.starbugs.workspace.DTO.AppDTO;
import com.starbugs.workspace.Dao.AppComponentRepository;
import com.starbugs.workspace.Dao.ApplicationRepository;
import com.starbugs.workspace.Exception.EntityExistsException;
import com.starbugs.workspace.Exception.EntityNotFoundException;
import com.starbugs.workspace.Model.AppComponent;
import com.starbugs.workspace.Model.AppVersion;
import com.starbugs.workspace.Model.Application;
import com.starbugs.workspace.Model.Project;

@Service
public class ApplicationService {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ApplicationRepository appRepository;
	
	@Autowired
	private AppComponentRepository componentRepository;
	
	
	public Application addApplicationToProject(AppDTO appReq, UUID project_id) throws EntityNotFoundException, EntityExistsException{

		// Check if the application already exists in the project..
		Project proj = projectService.getProjectById(project_id);
		Optional<Application> app = this.appRepository.findByProjectAndName(proj, appReq.getApp().getName());
		
		if(app.isPresent()) throw new EntityExistsException("Your Project already contains an app with the same name");
		

		AppVersion version = new AppVersion();
		version.setName(appReq.getVersion().getName());
		version.setStatus(appReq.getVersion().getStatus());
		version.setDate(new Date());
		
		appReq.getApp().addVersion(version);
		appReq.getApp().setProject(proj);
		return this.appRepository.save(appReq.getApp());
		
		
	}
	
	public Application getById(UUID app_id) throws EntityNotFoundException {
		Application application = this.appRepository.findById(app_id)
				.orElseThrow(()-> new EntityNotFoundException("Application with id: " + app_id.toString() + "not found."));
		return application;
	}
	
	public List<Application> findByProject(Project project){
		return this.appRepository.findByProject(project);
	}
	
	// Delete Single App
	public void deleteAppById(UUID id){
		this.appRepository.deleteById(id);
		
	}
	
	// Delete By Project...
	public void deleteApplicationByProject(Project project) {
		this.appRepository.deleteByProject(project);
	}
	
	// update app name
	public Application updateApp(UUID id, Application updates) throws EntityNotFoundException {
		Application app = this.getById(id);
		app.setName(updates.getName());
		return this.appRepository.save(app);
	}
	
	
	// Add Component to an application...
		public Application addComponent(UUID app_id, AppComponent component) throws EntityExistsException, EntityNotFoundException {

			Application app = this.getById(app_id);
			
			// Check if the component already exists...
			List<AppComponent> existingComp = app.getComponents().stream()
					.filter(comp-> comp.getName().equals(component.getName())).collect(Collectors.toList());
			if(existingComp.size() > 0) throw new EntityExistsException("A Component already exists with the same name");
			app.addComponent(component);
			return appRepository.save(app);
		}
		
		// Get Single Component...
		public AppComponent findComponentById(UUID comp_id) throws EntityNotFoundException {
			return componentRepository.findById(comp_id).orElseThrow(()->  new EntityNotFoundException("Component with id: " + comp_id.toString() + " Not Found"));
			
		}
		
		// Get Components of an application...
		public List<AppComponent> getAppComponents(UUID app_id){
			List<AppComponent> components = componentRepository.getAppComponents(app_id.toString());
			return components;
		}
		
		// Update Component...
		public AppComponent updateComponent(UUID app_id, UUID component_id, AppComponent componentUpdates) throws EntityExistsException, EntityNotFoundException {
			Optional<AppComponent> componentWithSameName = componentRepository.getComponentByNameAndApp(componentUpdates.getName(), app_id.toString());
			if(componentWithSameName.isPresent()) throw new EntityExistsException("Component already exists with the same name");
			AppComponent component = componentRepository.findById(component_id)
					.orElseThrow(()-> new EntityNotFoundException("Component with id: " + component_id.toString() + "not found."));
			component.setName(componentUpdates.getName());
			component.setStatus(componentUpdates.getStatus());
			return componentRepository.save(component);
		}
		
		// Delete Component....
		public void deleteComponent(UUID component_id) throws EntityNotFoundException {
			AppComponent comp = componentRepository.findById(component_id).orElseThrow(() -> new EntityNotFoundException("Component Not Found with id: " + component_id.toString()));
			componentRepository.delete(comp);
		}
	

}
