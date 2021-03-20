package com.yzh.rabbitmqdemo.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @program: JavaLearning
 * @description: rabbitmq 的配置类, 比如说配置消息转换器, 确认回调
 * @author: Mvbbb
 * @create: 2021-03-20 12:39
 */
@Configuration
public class MyRabbitConfig {
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@Bean
	public MessageConverter messageConverter(){
		return new Jackson2JsonMessageConverter();
	}

	/**
	 * 回调机制配置 
	 */
	@PostConstruct // MyRabbitConfig 构造器执行完之后调用该方法
	public void initRabbitTemplate(){ 
		// 设置消息抵达 exchange 回调
		rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback(){
			/** 
			 * @param correlationData 当前消息的唯一关联数据(这是消息的唯一id)
			 * @param ack 消息是否成功收到
			 * @param cause 失败的原因
			 */
			@Override
			public void confirm(CorrelationData correlationData, boolean ack, String cause) {
				System.out.println("confirm...correlationData["+correlationData+"]==>>ack["+ack+"]"+"==>>cause["+cause+"]");
			}
		});
		// 设置消息抵达 queue 回调
		rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
			/**
			 * 只要消息没有投递给指定的队列,就触发这个失败回调
			 * @param message 投递失败的消息的详细信息
			 * @param replyCode 回复的状态码
			 * @param replyText 回复的文本内容
			 * @param exchange 当时这个消息发给哪个交换机
			 * @param routingKey 当时这个信息的路由键
			 */
			@Override
			public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
				System.out.println("Failed message ["+message+"]");
			}
		});
	}

}