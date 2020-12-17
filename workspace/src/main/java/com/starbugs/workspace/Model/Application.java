package com.starbugs.workspace.Model;


import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;







@Entity
@Table(name = "applications")
public class Application {
	
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name ="id", updatable = false, nullable = false)
	@Type(type="uuid-char")
	private UUID id;
	
	@Column(name = "name")
	private String name;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Project project;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "application_id")
	private Set<AppVersion> versions = new HashSet<AppVersion>();
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "app_component_id")
	private Set<AppComponent> components = new HashSet<AppComponent>();
	

	
	public Application() {
	}
	
	public Application(UUID id, String name, Project project, Set<AppVersion> versions, Set<AppComponent> components) {
		this.id = id;
		this.name = name;
		this.project = project;
		this.versions = versions;
		this.components = components;
	
	}

	public UUID getId() {
		return id;
	}


	public void setId(UUID id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}


	public Set<AppVersion> getVersions() {
		return versions;
	}


	public void setVersions(Set<AppVersion> versions) {
		this.versions = versions;
	}


	public Set<AppComponent> getComponents() {
		return components;
	}


	public void setComponents(Set<AppComponent> components) {
		this.components = components;
	}

	public void addVersion(AppVersion version) {
		this.getVersions().add(version);
	}
	
	

	public void addComponent(AppComponent component) {
		this.components.add(component);
	}

	public void removeComponent(AppComponent component) {
		this.components.remove(component);
	}
	

	
	

}

