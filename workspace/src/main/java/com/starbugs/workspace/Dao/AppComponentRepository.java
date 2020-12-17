package com.starbugs.workspace.Dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.starbugs.workspace.Model.AppComponent;


@Repository
public interface AppComponentRepository extends JpaRepository<AppComponent, UUID>{
	
	@Query(value ="select * FROM components c where c.name =?1 and c.app_component_id =?2 LIMIT 1", nativeQuery = true)
	Optional<AppComponent> getComponentByNameAndApp(String name, String app_id);
	
	@Query(value ="select * FROM components c where c.app_component_id =?1", nativeQuery = true)
	List<AppComponent> getAppComponents(String app_id);
}
