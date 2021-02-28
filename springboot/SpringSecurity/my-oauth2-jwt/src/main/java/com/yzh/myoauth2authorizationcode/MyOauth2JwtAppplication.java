package com.yzh.myoauth2authorizationcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
public class MyOauth2JwtAppplication {

	public static void main(String[] args) {
		SpringApplication.run(MyOauth2JwtAppplication.class, args);
	}

}
