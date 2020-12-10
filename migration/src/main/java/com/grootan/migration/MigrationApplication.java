package com.grootan.migration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class MigrationApplication {

	public static void main(String[] args) {
		
		System.out.println("Application Started Successfully");
		System.setProperty("spring.main.allow-bean-definition-overriding", "true");
		SpringApplication.run(MigrationApplication.class, args);
	}

}
