package com.example.ppgclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.example.ppgclient", "Controll", "Service","Mapper"})
@EntityScan(basePackages = "Entity")
@EnableJpaRepositories(basePackages = "Repository")

public class PpgclientApplication {

	public static void main(String[] args) {
		SpringApplication.run(PpgclientApplication.class, args);
	}

}
