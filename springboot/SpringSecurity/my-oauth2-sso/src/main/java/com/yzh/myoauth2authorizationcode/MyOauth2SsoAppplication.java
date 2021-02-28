package com.yzh.myoauth2authorizationcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

@SpringBootApplication
@EnableOAuth2Sso
public class MyOauth2SsoAppplication {

	public static void main(String[] args) {
		SpringApplication.run(MyOauth2SsoAppplication.class, args);
	}

}
