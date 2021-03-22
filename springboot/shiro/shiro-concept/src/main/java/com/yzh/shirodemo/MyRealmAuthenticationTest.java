package com.yzh.shirodemo;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.jupiter.api.Test;

/**
 * @program: JavaLearning
 * @description: 自定义realm的认证测试类
 * @author: Mvbbb
 * @create: 2021-03-21 22:39
 */
public class MyRealmAuthenticationTest {
	@Test
	public void testAuthentication() {

		MyRealm myRealm = new MyRealm(); // 实现自己的 Realm 实例

		// 1.构建SecurityManager环境
		DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
		defaultSecurityManager.setRealm(myRealm);

		// 2.主体提交认证请求
		SecurityUtils.setSecurityManager(defaultSecurityManager); // 设置SecurityManager环境
		Subject subject = SecurityUtils.getSubject(); // 获取当前主体

		UsernamePasswordToken token = new UsernamePasswordToken("daming", "123456");
		subject.login(token); // 登录

		// subject.isAuthenticated()方法返回一个boolean值,用于判断用户是否认证成功
		System.out.println("isAuthenticated:" + subject.isAuthenticated()); // 输出true
		// 判断subject是否具有admin和user两个角色权限,如没有则会报错
		subject.checkRoles("admin", "user");
		//subject.checkRole("xxx"); // 报错
		// 判断subject是否具有user:add权限
		subject.checkPermission("user:add");
	}
}