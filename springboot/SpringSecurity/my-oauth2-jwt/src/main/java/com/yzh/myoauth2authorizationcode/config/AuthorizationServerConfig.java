package com.yzh.myoauth2authorizationcode.config;

import com.yzh.myoauth2authorizationcode.service.UserService;
import org.apache.el.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.List;

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
	
	@Autowired
	@Qualifier("jwtTokenStore")
	private TokenStore tokenStore;
	
	@Autowired
	private JwtAccessTokenConverter jwtAccessTokenConverter;
	
	@Autowired
	private JwtTokenEnhancer jwtTokenEnhancer;

	/**
	 * 使用密码模式需要配置
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints){
		TokenEnhancerChain enhancerChain=new TokenEnhancerChain();
		List<TokenEnhancer> delegates=new ArrayList<>();
		// 配置 jwt 的内容增强器
		delegates.add(jwtTokenEnhancer);
		delegates.add(jwtAccessTokenConverter);
		enhancerChain.setTokenEnhancers(delegates);
		endpoints.
				authenticationManager(authenticationManager).
				userDetailsService(userService)
				// 配置存储令牌策略
				.tokenStore(tokenStore)
				.accessTokenConverter(jwtAccessTokenConverter)
				.tokenEnhancer(enhancerChain);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
				// 配置客户端 ID
				.withClient("admin") 
				// 配置客户端秘密码
				.secret(passwordEncoder.encode("112233"))
				//配置重定向的地址 需要和请求授权的redirect_uri一致
				.redirectUris("http://www.baidu.com")
				.accessTokenValiditySeconds(3600)
				.refreshTokenValiditySeconds(864000)
				//配置授权范围
				.scopes("all")
				/**
				 * 授权类型
				 * authorization_code：授权码模式
				 * password:密码模式
				 */
				.authorizedGrantTypes("authorization_code","password","refresh_token");

		/**
		 * 在授权服务器中需要配置客户端信息, 目的是防止别的客户端冒充该客户端
		 */

	}
}
