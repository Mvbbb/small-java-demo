package com.example.demo;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 使用前缀匹配注入数据
 */
@Data
@ToString
@Component
@ConfigurationProperties(prefix = "prefix-properties")
class PropertUsePrefix{
	private String name;
	private String site;
}

@Data
@ToString
@Component
class Info{
	@Value("${application-info.name}")
	private String name;
	@Value("${application-info.version}")
	private String version;
}

@SpringBootApplication
public class Application {
	private String name;
	private String version;
	
	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(Application.class);
		Info info = run.getBean(Info.class);
		System.out.println(info);
		PropertUsePrefix propertUsePrefix = run.getBean(PropertUsePrefix.class);
		System.out.println(propertUsePrefix);
	}
}
