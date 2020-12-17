package com.starbugs.Mailer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ResetPasswordConsumer {

	private final String RESET_PASSWORD_MAIL_TOPIC = "Reset_Password_Email";
	
	@Autowired
	private MailSenderService mailer;
	
	public ResetPasswordConsumer() {
	}

	
	@KafkaListener(topics = RESET_PASSWORD_MAIL_TOPIC, groupId = "user-credentials")
	public void consume(String details) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			VerificationMailDetails mailDetails = mapper.readValue(details, VerificationMailDetails.class);
			mailer.sendResetPasswordEmail(mailDetails);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
