package com.starbugs.Core.API;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.starbugs.Core.Model.AppComponent;
import com.starbugs.Core.Model.Application;
import com.starbugs.Core.Service.ApplicationService;

@RestController
public class ApplicationController {

	@Autowired
	private ApplicationService appService;
	
	public ApplicationController() {
	}
	

	
	

	
	// Get Single Application...
	@RequestMapping(value = "/project/applications", method = RequestMethod.GET)
	public ResponseEntity<?> getApplication(@RequestParam("app_id") UUID app_id){
		try {
			Application app = appService.getAppById(app_id);
			return ResponseEntity.ok(app);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		}
	}
	

	
	// Get Single Component by ID...
	@RequestMapping(value = "/project/applications/component/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getComponent(@PathVariable("id") UUID id){
		AppComponent component = null;
		try {
			component = appService.findComponentById(id);
			
			return ResponseEntity.ok(component);
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getLocalizedMessage());
		}
	
	}

	// Get component's list of an existing application..
	@RequestMapping(value = "/project/application/{id}/components", method = RequestMethod.GET)
	public List<AppComponent> getAppComponents(@PathVariable("id") UUID id){
		List<AppComponent> components = appService.getAppComponents(id);
		return components;
	}
	


}

