package com.yzh.shirodemo;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.jupiter.api.Test;

/**
 * @program: JavaLearning
 * @description: 授权测试类
 * @author: Mvbbb
 * @create: 2021-03-21 21:42
 */
public class AuthorizationTest {
	SimpleAccountRealm simpleAccountRealm=new SimpleAccountRealm();
	
	
	@Test
	public void testAuthentication(){
		// 添加一个用户
		simpleAccountRealm.addAccount("daming","123456","admin","user");
		
		//1. 构建 SecurityManager 环境
		DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
		defaultSecurityManager.setRealm(simpleAccountRealm);
		
		//2. 主体提交认证请求 
		SecurityUtils.setSecurityManager(defaultSecurityManager);
		Subject subject = SecurityUtils.getSubject();

		UsernamePasswordToken token = new UsernamePasswordToken("daming", "123456");
		subject.login(token); //登录

		//3. subject.isAuthenticated() 方法返回一个 boolean 值,判断用户是否认证
		System.out.println("是否认证: "+subject.isAuthenticated());
		//4. 判断subject是否具有admin和user 两个角色,没有会报错
		subject.checkRoles("admin","user");
	}
}