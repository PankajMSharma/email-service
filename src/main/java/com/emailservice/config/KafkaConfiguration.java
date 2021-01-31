package com.emailservice.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.emailservice.model.KafkaEmailMessage;

@EnableKafka
// @KafkaAutoConfiguration
@Configuration
public class KafkaConfiguration {

	@Bean
	public ProducerFactory<String, KafkaEmailMessage> producerFactory() {
		
		Map<String, Object> configs = new HashMap<>();
		
		configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class); // Serialization for Key
		configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class); // Serialization for Value (We need JSON Serializer because we want to send json message on topic)
		return new DefaultKafkaProducerFactory<>(configs);
	}
	
	@Bean
	public KafkaTemplate<String, KafkaEmailMessage> kafkaTeplate() {
		return new KafkaTemplate<>(this.producerFactory());
	}
	
/*	@Bean
	// @ConditionalOnMissingBean(ConsumerFactory.class)
	@ConditionalOnMissingBean(value=ConsumerFactory.class)
	public ConsumerFactory<String, KafkaEmailMessage> emailConsumerFactory() {
		Map<String, Object> configs = new HashMap<>();
		
		configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		configs.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id"); // Group Id
		configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); // DeSerialization for Key
		configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class); // DeSerialization for Value (We need JSON Serializer because we want to send json message on topic)
		return new DefaultKafkaConsumerFactory<String, KafkaEmailMessage>(configs,
				new StringDeserializer(), new JsonDeserializer<>(KafkaEmailMessage.class));
	}
	
	@Bean
	// @ConditionalOnMissingBean(name = "concurrentKafkaListenerContainerFactory")
	public ConcurrentKafkaListenerContainerFactory<String, KafkaEmailMessage> emailKafkaListenerContainerFactory() {
		
		ConcurrentKafkaListenerContainerFactory<String, KafkaEmailMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(emailConsumerFactory());
		return factory;
	}*/
	
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, KafkaEmailMessage>>
                        kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, KafkaEmailMessage> factory =
                                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(3);
        factory.getContainerProperties().setPollTimeout(3000);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, KafkaEmailMessage> consumerFactory() {
        return new DefaultKafkaConsumerFactory<String, KafkaEmailMessage>(
        		consumerConfigs(),
        		new StringDeserializer(),
        		new JsonDeserializer<>(KafkaEmailMessage.class)
        		);
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id"); // Group Id
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); // DeSerialization for Key
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class); // DeSerialization for Value (We need JSON Serializer because we want to send json message on topic)
        return props;
    }
}
