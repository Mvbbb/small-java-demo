package com.yzh.myoauth2authorizationcode.config;

import com.yzh.myoauth2authorizationcode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * 授权服务器配置
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager).userDetailsService(userService);
	}

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
				.redirectUris("http://www.baidu.com")
				//配置授权范围
				.scopes("all")
				//表示授权类型
				.authorizedGrantTypes("authorization_code","password");

		/**
		 * 在授权服务器中需要配置客户端信息, 目的是防止别的客户端冒充该客户端
		 */

	}
}
