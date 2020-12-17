package com.starbugs.Core.Model;

import java.util.ArrayList;
import java.util.List;
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
@Table(name = "projects")
public class Project {
	
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name ="id", updatable = false, nullable = false)
	@Type(type="uuid-char")
	private UUID id;
	
	@Column
	private String name;
	
	@ManyToOne()
	@JoinColumn(name = "company_id")
	private Company company;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "project_id")
	private List<Application> applications = new ArrayList<>();
	
	private String businessDomain;
	
	public Project() {
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



	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Project(UUID id, String name, Company company, String businessDomain) {
		this.id = id;
		this.name = name;
		this.businessDomain = businessDomain;
	
	
	}

	
	public Project(UUID id, String name, Company company, List<Application> applications, String businessDomain) {
	
		this.id = id;
		this.name = name;
		this.company = company;
		this.applications = applications;
		this.businessDomain = businessDomain;
	}

	public String getBusinessDomain() {
		return businessDomain;
	}

	public void setBusinessDomain(String businessDomain) {
		this.businessDomain = businessDomain;
	}
	
	public void addApplication(Application app) {
		this.applications.add(app);
	}

	public void removeApplication(Application app) {
		this.applications.remove(app);
	}

	public List<Application> getApplications() {
		return applications;
	}

	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}
	
}
