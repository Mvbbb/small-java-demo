package com.yzh.myoauth2authorizationcode.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * 授权服务器配置
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
				// 配置客户端 ID
				.withClient("admin") 
				// 配置客户端秘密码
				.secret(passwordEncoder.encode("112233")) 
				// 配置accessToken 的有效期
				.accessTokenValiditySeconds(3600)
				//配置刷新token的有效期
				.refreshTokenValiditySeconds(864000)
				//配置重定向的地址 需要和请求授权的redirect_uri一致
				.redirectUris("http://www.yzh.im")
				//配置授权范围
				.scopes("all")
				//表示授权类型
				.authorizedGrantTypes("authorization_code");
		
	}
}
