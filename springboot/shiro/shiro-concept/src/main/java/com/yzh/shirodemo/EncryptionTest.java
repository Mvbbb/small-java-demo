package com.yzh.shirodemo;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.jupiter.api.Test;

/**
 * @program: JavaLearning
 * @description: Shiro 中提供的加密工具测试
 * @author: Mvbbb
 * @create: 2021-03-22 17:32
 */
public class EncryptionTest {
	@Test
	public void test(){
		String password="123456";
		String salt = new SecureRandomNumberGenerator().nextBytes().toString();
		int times = 2; // 加密次数
		String algorithmName = "md5"; // 加密算法

		// SimpleHash(String algorithmName, Object source, Object salt, int hashIterations)
		String encodePassword = new SimpleHash(algorithmName, password, salt, times).toString();
		System.out.println("加密之后得到"+encodePassword);
	}
}