package com.starbugs.Core.Dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.starbugs.Core.Model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID>{
	
	Company findByName(String name);
	

}
