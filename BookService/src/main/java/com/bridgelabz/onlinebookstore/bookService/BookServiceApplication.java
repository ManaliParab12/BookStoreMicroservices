package com.bridgelabz.onlinebookstore.bookService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@EnableEurekaClient
@SpringBootApplication
public class BookServiceApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(BookServiceApplication.class, args);
	}
}
// eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiI2MDJmZDJiZTRjYTIyYjZmNDRhOGMxMmUifQ.9x7W2cXdjJaArjl7LKauPB3nSanzfFrrIq6-MFxdf4E