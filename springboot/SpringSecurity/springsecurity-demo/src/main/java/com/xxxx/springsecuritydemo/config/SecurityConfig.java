package com.xxxx.springsecuritydemo.config;

import com.xxxx.springsecuritydemo.handler.MyAccessDeniedHandler;
import com.xxxx.springsecuritydemo.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * Security配置类
 *
 * @author zhoubin
 * @since 1.0.0
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private MyAccessDeniedHandler accessDeniedHandler;
	@Autowired
	private DataSource dataSource;
	@Autowired
	private PersistentTokenRepository persistentTokenRepository;
	@Autowired
	private UserDetailServiceImpl userDetailService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//表单提交
		http.formLogin()
				//自定义入参
				.usernameParameter("username123")
				.passwordParameter("password123")
				//自定义登录页面
				.loginPage("/showLogin")
				//必须和表单提交的接口一样，会去执行自定义登录逻辑
				.loginProcessingUrl("/login")
				//登录成功后跳转的页面，POST请求
				.successForwardUrl("/toMain")
				//自定义登录成功处理器
				// .successHandler(new MyAuthenticationSuccessHandler("/main.html"))
				//登录失败后跳转的页面，POST请求
				.failureForwardUrl("/toError");
				//自定义登录失败处理器
				// .failureHandler(new MyAuthenticationFailureHandler("/error.html"));


		//授权
		http.authorizeRequests()
				//放行/error.html，不需要认证
				.antMatchers("/error.html").permitAll()
				//放行/login.html，不需要认证
				// .antMatchers("/login.html").permitAll()
				.antMatchers("/showLogin").access("permitAll")
				//放行静态资源
				// .antMatchers("/css/**","/js/**","/images/**").permitAll()
				//放行后缀.png
				// .antMatchers("/**/*.png").permitAll()
				//放行后缀.png,正则表达式
				// .regexMatchers(".+[.]png").permitAll()
				//指定请求方法
				// .regexMatchers(HttpMethod.POST,"/demo").permitAll()
				//mcv匹配
				// .mvcMatchers("/demo").servletPath("/xxxx").permitAll()
				// .antMatchers("/xxxx/demo").permitAll()
				//权限控制,严格区分大小写
				//基于权限
				// .antMatchers("/main1.html").hasAuthority("admiN")
				// .antMatchers("/main1.html").hasAnyAuthority("admin","admiN")
				//基于角色
				// .antMatchers("/main1.html").hasRole("abC")
				// .antMatchers("/main1.html").access("hasRole('abc')")
				// .antMatchers("/main1.html").hasAnyRole("abC","abc")
				//基于IP地址
				// .antMatchers("/main1.html").hasIpAddress("127.0.0.1")

				//所有请求都必须认证才能访问，必须登录
				.anyRequest().authenticated();
				//自定义access方法
				// .anyRequest().access("@myServiceImpl.hasPermission(request,authentication)");

		//异常处理
		http.exceptionHandling()
				.accessDeniedHandler(accessDeniedHandler);


		//记住我
		http.rememberMe()
				//设置数据源
				.tokenRepository(persistentTokenRepository)
				// .rememberMeParameter()
				//超时时间
				.tokenValiditySeconds(60)
				//自定义登录逻辑
				.userDetailsService(userDetailService);

		//退出
		http.logout()
				.logoutUrl("/logout")
				//退出成功后跳转的页面
				.logoutSuccessUrl("/login.html");


		//关闭csrf防护
		// http.csrf().disable();
	}

	@Bean
	public PasswordEncoder getPw() {
		return new BCryptPasswordEncoder();
	}


	@Bean
	public PersistentTokenRepository persistentTokenRepository(){
		JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
		//设置数据源
		jdbcTokenRepository.setDataSource(dataSource);
		//自动建表，第一次启动时开启，第二次启动时注释掉
		// jdbcTokenRepository.setCreateTableOnStartup(true);
		return jdbcTokenRepository;
	}

}