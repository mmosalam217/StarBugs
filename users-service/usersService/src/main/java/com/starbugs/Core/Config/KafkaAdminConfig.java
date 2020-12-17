package com.starbugs.Core.Config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaAdminConfig {

	@Value("${spring.kafka.producer.bootstrap-servers}")
	private String bootstrapServer;
	
	public KafkaAdminConfig() {
	}
	
	@Bean
	public KafkaAdmin kafkaAdmin() {
		Map<String, Object> config = new HashMap<String, Object>();
		config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		return new KafkaAdmin(config);
		
	}
	
	@Bean()
	public NewTopic produceVerificationMail() {
		return new NewTopic("Send_Verification_Email", 1, (short) 1);
	}
	
	@Bean()
	public NewTopic resetPasswordEmail() {
		return new NewTopic("Reset_Password_Email", 1, (short) 1);
	}

}
