package com.gokakkar.banking_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class BankingSystemApplication {
	public static void main(String[] args) {
		SpringApplication.run(BankingSystemApplication.class, args);
	}
}
