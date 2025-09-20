package com.exam.rbac_jwt_mfa_library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableCaching
@EnableAsync
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
