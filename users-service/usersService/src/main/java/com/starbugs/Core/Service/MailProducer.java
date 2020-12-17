package com.starbugs.Core.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MailProducer {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	

	public MailProducer() {
	}

	public void send(String topic, String details) {
		kafkaTemplate.send(topic, details);
	}
	
}
