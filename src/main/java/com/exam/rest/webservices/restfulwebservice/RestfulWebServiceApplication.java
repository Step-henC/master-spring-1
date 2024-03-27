package com.exam.rest.webservices.restfulwebservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestfulWebServiceApplication {
//localhost:8080/swagger-ui.html
//localhost:8080/v3/api-docs

//monitor with spring-boot-starter-actuator
		//localhost:8080/actuator
		//beans endpoint: list of beans in app
		//health endpoint 
		// metrics endpoint 
		//mappings 
	public static void main(String[] args) {
		SpringApplication.run(RestfulWebServiceApplication.class, args);
	}

}
