package com.starbugs.Core.Service;

import java.util.UUID;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.starbugs.Core.Dao.VerificationTokenRepository;
import com.starbugs.Core.Model.VerificationToken;

@Service
public class VerificationTokenService {

	Logger logger = Logger.getLogger(VerificationTokenService.class);
	@Autowired
	private VerificationTokenRepository verificationTokenRepository;
	
	
	public VerificationTokenService() {
	}
	
	public VerificationToken getTokenByUserID(UUID id) {
		return verificationTokenRepository.findByUser(id);
	}
	

	public VerificationToken getTokenByTokenString(String token) {
		return verificationTokenRepository.findByToken(token);
	}
	
	public VerificationToken insertToken(VerificationToken token) {
		return verificationTokenRepository.save(token);
	}
	
	public VerificationToken updateToken(VerificationToken tokenDetails) {
		return verificationTokenRepository.save(tokenDetails);
	}
	
}
