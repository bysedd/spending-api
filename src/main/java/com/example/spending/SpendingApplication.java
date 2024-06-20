package com.example.spending;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The SpendingApplication class is the main entry point for running the Spending Application.
 * It is a Spring Boot application that initializes and starts the application.
 */
@SpringBootApplication
public class SpendingApplication {

	/**
	 * The main method is the entry point for running the Spending Application.
	 * It initializes and starts the application.
	 *
	 * @param args an array of command-line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(SpendingApplication.class, args);
	}

}
