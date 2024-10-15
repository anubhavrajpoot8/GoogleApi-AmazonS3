package com.assignment.FreightFox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.assignment.FreightFox.model")
@EnableJpaRepositories("com.assignment.FreightFox.repo")
public class FreightFoxApplication {

	public static void main(String[] args) {
		SpringApplication.run(FreightFoxApplication.class, args);
	}

}
