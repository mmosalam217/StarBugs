package com.starbugs.workspace.Dao;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.starbugs.workspace.Model.Workspace;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, UUID>{
	
	Optional<Workspace> findByClientId(UUID client_id);

}
