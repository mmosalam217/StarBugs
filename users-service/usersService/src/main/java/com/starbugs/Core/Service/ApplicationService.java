package com.starbugs.Core.Service;

import java.util.Date;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.starbugs.Core.DTO.AddAppRequest;
import com.starbugs.Core.Dao.AppComponentRepository;
import com.starbugs.Core.Dao.ApplicationRepository;
import com.starbugs.Core.Dao.ProjectRepository;
import com.starbugs.Core.Model.AppComponent;
import com.starbugs.Core.Model.Application;
import com.starbugs.Core.Model.Project;
import com.starbugs.Core.Model.Version;


@Service
public class ApplicationService {
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private ApplicationRepository appRepository;
	

	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private AppComponentRepository componentRepository;
	
	
	
	public ApplicationService() {
	}
	
	public Application addApplicationToProject(AddAppRequest appReq, UUID project_id) throws Exception{

		// Check if the application already exists in the project..
		Project proj = projectService.getProjectById(project_id);
		List<Application> apps = proj.getApplications().stream()
				.filter((app)-> app.getName().equals(appReq.getApp().getName())).collect(Collectors.toList());
		
		if(apps.size() > 0) throw new Exception("Your Project already contains an app with the same name");
		

		Version version = new Version();
		version.setName(appReq.getVersion().getName());
		version.setStatus(appReq.getVersion().getStatus());
		version.setDate(new Date());
		
		appReq.getApp().addVersion(version);
		
		proj.addApplication(appReq.getApp());
		Project updatedProj = projectRepository.save(proj);
		return updatedProj.getApplications().stream()
				.filter(app-> app.getName().equals(appReq.getApp().getName())).collect(Collectors.toList()).get(0);
		
	}
	

	// Get single application
	public Application getAppById(UUID id) throws Exception{
		Application app = appRepository.getOne(id);
		if(app == null) throw new Exception("Application Not Found");
		return app;
	}
	
	// Delete Single App
	public void deleteAppById(UUID project_id, UUID id) throws Exception{

		Application app = null;
		try {
			app = this.getAppById(id);
		} catch (Exception e) {
			throw new Exception(e.getLocalizedMessage());
		}
		
		Project project = projectRepository.findById(project_id).orElseThrow(()-> new Exception("Project Not Found!"));
	
		project.removeApplication(app);
		projectRepository.deleteById(id);
		
	}
	
	// Update app info, for now just the name cuz there are no other properties..
	public Application updateAppInformation(UUID id, Application updates) throws Exception{

		Application app = null;
		try {
			 app = this.getAppById(id);
			 app.setName(updates.getName());
		} catch (Exception e) {
			throw new Exception(e.getLocalizedMessage());
		}
		
		return appRepository.save(app);

	}
	
	// Add Component to an application...
	public Application addComponent(UUID app_id, AppComponent component) throws Exception{

		Application app = appRepository.getOne(app_id);
		
		// Check if the component already exists...
		AppComponent appComp = componentRepository.getComponentByNameAndApp(component.getName(), app_id.toString());
		if(appComp != null) throw new Exception("Component already exists");
		
		app.addComponent(component);
		return appRepository.save(app);
	}
	
	// Get Single Component...
	public AppComponent findComponentById(UUID comp_id) throws Exception {
		return componentRepository.findById(comp_id).orElseThrow(()->  new Exception("Component with id: " + comp_id.toString() + " Not Found"));
		
	}
	
	// Get Components of an application...
	public List<AppComponent> getAppComponents(UUID app_id){
		List<AppComponent> components = componentRepository.getAppComponents(app_id.toString());
		return components;
	}
	
	// Update Component...
	public AppComponent updateComponent(UUID app_id, UUID component_id, AppComponent componentUpdates) throws Exception {
		AppComponent componentWithSameName = componentRepository.getComponentByNameAndApp(componentUpdates.getName(), app_id.toString());
		if(componentWithSameName != null) throw new Exception("Component already exists with the same name");
		AppComponent component = componentRepository.getOne(component_id);
		if(component == null) throw new Exception("Component Not Found");
		component.setName(componentUpdates.getName());
		component.setStatus(componentUpdates.getStatus());
		return componentRepository.save(component);
	}
	
	// Delete Component....
	public void deleteComponent(UUID component_id) throws Exception{
		AppComponent comp = componentRepository.findById(component_id).orElseThrow(() -> new Exception("Component Not Found"));
		componentRepository.delete(comp);
	}
	


}
