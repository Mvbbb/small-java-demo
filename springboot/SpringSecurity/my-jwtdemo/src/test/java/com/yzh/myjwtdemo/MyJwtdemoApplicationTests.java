package com.yzh.myjwtdemo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.Base64Codec;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
class MyJwtdemoApplicationTests {

	@Test
	void contextLoads() {
	}

	/**
	 * 测试 Jwt 生成
	 */
	@Test
	public void testCreateToken(){
		//创建一个JwtBuilder对象 
		JwtBuilder jwtBuilder = Jwts.builder()
				//声明的标识{"jti":"888"} 
				.setId("888") 
				// 主体，用户{"sub":"Rose"} 
				.setSubject("Rose") 
				// 创建日期{"ita":"xxxxxx"} 
				.setIssuedAt(new Date()) 
				//签名手段，参数1：算法，参数2：盐 
				.signWith(SignatureAlgorithm.HS256,"xxxx");
		String token = jwtBuilder.compact();
		System.out.println(token);
		// 三部分的 Base64 解密
		System.out.println("--------------------");
		String[] split = token.split("\\."); 
			// header 包含了 加密算法, 类型
		System.out.println(Base64Codec.BASE64.decodeToString(split[0]));
			// payload 包含了 签发者,用户,接收方,过期时间,签发时间,身份标识...
		System.out.println(Base64Codec.BASE64.decodeToString(split[1]));
			//无法解密 第三部分是采用加密算法生成的,无法使用 Base64 解密
		System.out.println(Base64Codec.BASE64.decodeToString(split[2]));
	}

	/**
	 * 测试 Token 解析
	 */
	@Test
	public void testParseToken(){
		String token="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiJSb3NlIiwiaWF0IjoxNjE0NTAzNjIyfQ.YcIJ5hOZxPELf9yAtWt0jCOK6_EgHiOjSWZdeUdV7gs";
		//解析token获取负载中的声明对象
		Claims claims = Jwts.parser()
				.setSigningKey("xxxx")
				.parseClaimsJws(token)
				.getBody();
		//解析token获取负载中的声明对象
		System.out.println(claims.getId());
		System.out.println(claims.getSubject());
		System.out.println(claims.getIssuedAt());
	}
	/**
	 * Token 过期校验
	 */
	@Test
	public void testCreateTokenHasExp(){
		//当前系统时间的长整型
		long now=System.currentTimeMillis();
		//过期时间，这里是1分钟后的时间长整型
		long exp=now+60*1000;
		//当前系统时间的长整型
		JwtBuilder builder = Jwts.builder()
				//声明的标识{"jti":"888"}
				.setId("888")
				//主体，用户{"sub":"Rose"}
				.setSubject("Rose")
				//创建日期{"ita":"xxxxxx"}
				.setIssuedAt(new Date())
				//签名手段，参数1：算法，参数2：盐
				.signWith(SignatureAlgorithm.HS256, "xxxx")
				//设置过期时间
				.setExpiration(new Date(exp));
		String token= builder.compact();
		System.out.println(token);
	}

	/**
	 * 测试带有过期时间的 jwt token 解析
	 */
	@Test
	public void testParseTokenHasExp(){
		String token="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiJSb3NlIiwiaWF0IjoxNjE0NTA0Mjk4LCJleHAiOjE2MTQ1MDQzNTh9.vmp1nnQIC4i5JKQ6yDLBaqqTvBJa4ONcsNdWraOF9UM";
		//解析token获取负载中的声明对象
		Claims claims = Jwts.parser() 
				.setSigningKey("xxxx") 
				.parseClaimsJws(token) 
				.getBody();

		System.out.println("id:" + claims.getId()); 
		System.out.println("subject:" + claims.getSubject()); 
		System.out.println("issuedAt:" + claims.getIssuedAt()); 
		DateFormat sf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		System.out.println("签发时间:"+sf.format(claims.getIssuedAt())); 
		System.out.println("过期时间:"+sf.format(claims.getExpiration())); 
		System.out.println("当前时间:"+sf.format(new Date()));
	}

	/**
	 * 测试带自定义 claims 的 token 生成
	 */
	@Test
	public void testCreateTokenByClaims(){
		//当前系统时间的长整型
		long now = System.currentTimeMillis();
		//过期时间，这里是1分钟后的时间长整型
		long exp = now + 60 * 1000;
		//创建一个JwtBuilder对象
		JwtBuilder jwtBuilder = Jwts.builder()
				//声明的标识{"jti":"888"}
				.setId("888")
				//主体，用户{"sub":"Rose"}
				.setSubject("Rose")
				//创建日期{"ita":"xxxxxx"}
				.setIssuedAt(new Date())
				//签名手段，参数1：算法，参数2：盐
				.signWith(SignatureAlgorithm.HS256, "xxxx")
				//设置过期时间
				.setExpiration(new Date(exp)).claim("roles","admin")
				//直接传入map
				// .addClaims(map)
				.claim("roles","admin")
				.claim("logo","shsxt.jpg");
		//获取jwt的token
		String token = jwtBuilder.compact();
		System.out.println(token);
	}
	/**
	 * 测试带 自定义 claims 的 token 解析
	 */
	@Test public void testParseTokenByClaims() {
		String token ="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiJSb3NlIiwiaWF0IjoxNjE0NTA0ODIxLCJleHAiOjE2MTQ1MDQ4ODEsInJvbGVzIjoiYWRtaW4iLCJsb2dvIjoic2hzeHQuanBnIn0.8RN8y-XR1aFQc0ZzlhoU_uXVzb1WVqNxJqHZZToIdTo";
		//解析token获取负载中的声明对象 
		Claims claims = Jwts.parser() 
				.setSigningKey("xxxx") 
				.parseClaimsJws(token) 
				.getBody();
		//打印声明的属性
		System.out.println("id:" + claims.getId());
		System.out.println("subject:" + claims.getSubject());
		System.out.println("issuedAt:" + claims.getIssuedAt());
		DateFormat sf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		System.out.println("签发时间:"+sf.format(claims.getIssuedAt())); 
		System.out.println("过期时间:"+sf.format(claims.getExpiration()));
		System.out.println("当前时间:"+sf.format(new Date()));
		System.out.println("roles:"+claims.get("roles"));
		System.out.println("logo:"+claims.get("logo"));
	}
}
