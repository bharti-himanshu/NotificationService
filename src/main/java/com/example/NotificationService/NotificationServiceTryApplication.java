package com.example.NotificationServiceTry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.NotificationServiceTry")
public class NotificationServiceTryApplication {
	public static void main(String[] args) {

		SpringApplication.run(NotificationServiceTryApplication.class, args);

	}

}
