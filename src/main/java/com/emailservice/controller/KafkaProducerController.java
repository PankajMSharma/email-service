package com.emailservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emailservice.model.KafkaEmailMessage;
import com.emailservice.service.EmailHandelerService;

@RestController
@RequestMapping("kafka")
public class KafkaProducerController {
	
	@Autowired
	KafkaTemplate<String, KafkaEmailMessage> kafkaTemplate;
	
	@Autowired
	EmailHandelerService emailService;
	
	private static final String TOPIC = "Kafka_Example";

	@GetMapping("/publish/{message}")
	public String post(@PathVariable("message") final String message) {
		KafkaEmailMessage email = new KafkaEmailMessage();
		String[] to = {"spankaj357@gmail.com"};
		email.setTo(to);
		email.setMessage(message);
		
		emailService.sendBasicEmail(email.getTo(), "Test mail", email.getMessage());
		
		kafkaTemplate.send(TOPIC, email); // publishes message to topic
		
		return "Published Successfully";
	}
}
