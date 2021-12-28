package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class Sprint2SessionServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(Sprint2SessionServicesApplication.class, args);
	}

}
