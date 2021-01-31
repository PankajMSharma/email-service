package com.emailservice.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.emailservice.model.KafkaEmailMessage;

@Service
public class KafkaEmail {

	@KafkaListener(containerFactory="kafkaListenerContainerFactory", topics = "Kafka_Example", groupId = "group_id")
	public void consumeEmail(KafkaEmailMessage email) {
		System.out.println("Consumed message - To " + (email.getTo() != null ? email.getTo()[0] : "") + " message - " + email.getMessage());
	}
}
