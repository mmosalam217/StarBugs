package com.starbugs.workspace.Dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.starbugs.workspace.Model.Application;
import com.starbugs.workspace.Model.Project;


@Repository
public interface ApplicationRepository extends JpaRepository<Application, UUID>{
	Optional<Application> findByProjectAndName(Project project, String name);
	List<Application> findByProject(Project project);
	void deleteByProject(Project project);

	
}
