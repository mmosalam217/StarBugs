package com.starbugs.Core.Dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.starbugs.Core.Model.VerificationToken;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, UUID> {
	VerificationToken findByUser(UUID id);
	VerificationToken findByToken(String token);
}
