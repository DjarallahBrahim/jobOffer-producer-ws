package com.kafka.webservice.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@EntityScan(basePackages = "com.joboffer.ws.core.jpa.entities")  // Specify the package for entities
public class KafkaWsApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(KafkaWsApplication.class, args);
	}

}
