package com.example.kafkaOwnProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.kafkaOwnProject.kafkaClasses.KafkaProducer;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class KafkaOwnProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaOwnProjectApplication.class, args);

		ApplicationContext context = SpringApplication.run(KafkaOwnProjectApplication.class, args);

		KafkaProducer kafkaProducer = context.getBean(KafkaProducer.class);

		kafkaProducer.sendMessage("Hello, Kafka!");
	}
}
