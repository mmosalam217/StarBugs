package com.starbugs.workspace.Dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.starbugs.workspace.Model.Project;
import com.starbugs.workspace.Model.Workspace;


@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID>{
	List<Project> findByWorkspace(Workspace workspace);
	Optional<Project> findByNameAndWorkspace(String name, Workspace workspace);
	void deleteByWorkspace(Workspace workspace);
}
