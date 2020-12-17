package com.starbugs.Mailer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class VerificationMailConsumer {


	private final String VERIFICATION_EMAIL_TOPIC = "Send_Verification_Email";
	
	@Autowired
	private MailSenderService verificationMailSender;
	
	public VerificationMailConsumer() {
	}
	
	
	
	@KafkaListener(topics = VERIFICATION_EMAIL_TOPIC, groupId = "client-verification")
	public void consume(String details) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			VerificationMailDetails mailDetails = mapper.readValue(details, VerificationMailDetails.class);
			verificationMailSender.sendVerificationEmail(mailDetails);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	
	
}
