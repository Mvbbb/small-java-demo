package com.yzh.rabbitmqdemo;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 使用 rabbitmq 
 * 1. 引入依赖, RabbitAutoConfiguration 就会自动生效
 * 2. 给容器中自动配置了 RabbitTemplate, AmqpAdmin, CachingConnectionFactroy,RabbitMessagingTemplate
 *        所有属性都是在 spring.rabbitmq 中配置 
 * 3. @EnableRabbit: 开启 
 */
@SpringBootApplication
@EnableRabbit
public class RabbitmqDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitmqDemoApplication.class, args);
	}

}
