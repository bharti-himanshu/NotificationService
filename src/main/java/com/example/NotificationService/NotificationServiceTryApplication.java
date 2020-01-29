package com.example.NotificationService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.NotificationService")
public class NotificationServiceTryApplication {
	public static void main(String[] args) {

		SpringApplication.run(NotificationServiceTryApplication.class, args);

	}

}
