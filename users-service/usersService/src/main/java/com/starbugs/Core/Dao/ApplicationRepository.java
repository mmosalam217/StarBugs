package com.starbugs.Core.Dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.starbugs.Core.Model.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, UUID>{
	Application findByName(String name);

	
}
