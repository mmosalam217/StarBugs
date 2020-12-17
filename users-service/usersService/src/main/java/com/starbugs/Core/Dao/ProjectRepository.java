package com.starbugs.Core.Dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.starbugs.Core.Model.Company;
import com.starbugs.Core.Model.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID>{
	
	List<Project> findByCompany(Company company);
	Project findByNameAndCompany(String name, Company company);
}
