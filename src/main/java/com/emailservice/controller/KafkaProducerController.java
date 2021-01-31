package com.emailservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emailservice.model.KafkaEmailMessage;

@RestController
@RequestMapping("kafka")
public class KafkaProducerController {
	
	@Autowired
	KafkaTemplate<String, KafkaEmailMessage> kafkaTemplate;
	
	private static final String TOPIC = "Kafka_Example";

	@GetMapping("/publish/{message}")
	public String post(@PathVariable("message") final String message) {
		KafkaEmailMessage email = new KafkaEmailMessage();
		String[] to = {"spankaj@gmail.com"};
		email.setTo(to);
		email.setMessage(message);
		
		kafkaTemplate.send(TOPIC, email); // publishes message to topic
		
		return "Published Successfully";
	}
}
