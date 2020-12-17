package com.starbugs.Core.Dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.starbugs.Core.Model.AppUser;
import com.starbugs.Core.Model.StarBugsClient;

@Repository
public interface UsersRepository extends JpaRepository<AppUser, UUID>{
	
	Optional<AppUser> findByUsername(String username); // username is Company_Email
	
	@Query(value = "SELECT * FROM users WHERE client_id =?1", nativeQuery = true)
	List<AppUser> findByClient(String client_id);
	
	@Transactional
	long deleteByClient(StarBugsClient client);
}
