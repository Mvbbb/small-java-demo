package com.yzh.myoauth2authorizationcode.config;

import org.springframework.context.annotation.Bean; 
import org.springframework.context.annotation.Configuration; 
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore; 

/**
 * 使用 Jwt 存储 token 的配置
 */
@Configuration
public class JwtTokenStoreConfig {
	@Bean 
	public TokenStore jwtTokenStore(){
		return new JwtTokenStore(jwtAccessTokenConverter());
	}
	
	@Bean 
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter(); 
		// 配置 jwt 使用的密匙
		accessTokenConverter.setSigningKey("test_key");
		return accessTokenConverter;
	}
	@Bean
	public JwtTokenEnhancer jwtTokenEnhancer(){
		return new JwtTokenEnhancer();
	}
}
