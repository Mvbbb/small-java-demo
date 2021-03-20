package com.yzh.rabbitmqdemo;
  
import com.yzh.rabbitmqdemo.bean.Person;
import lombok.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RabbitmqDemoApplicationTests {

	@Autowired
	private AmqpAdmin amqpAdmin;
	/**
	 * 如何创建 Exchange[hello.java.exchange], Queue, Binding
	 *  1. 使用 AmqpAdmin 进行创建
	 */
	@Test
	public void createExchange(){
		// DirectExchange(String name, boolean durable, boolean autoDelete, Map<String, Object> arguments)
		amqpAdmin.declareExchange(new DirectExchange("hello.java.exchange",true,false));
		System.out.println("Exchange[hello.java.exchange]创建成功");
	}
	
	@Test
	public void createQueue(){
		// 	public Queue(String name, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)
		Queue queue = new Queue("hello.java.queue",true,false,false);
		amqpAdmin.declareQueue(queue);
		System.out.println("Queue[hello.java.queue]创建成功");
	}
	
	@Test
	public void createBinding(){
		//	public Binding(
		//	String destination,  [目的地]
		//	DestinationType destinationType, [目的地类型] 
		//	String exchange, [交换机]
		//	String routingKey, [路由键]
		//	Map<String, Object> arguments) 
		// 将exchange指定的交换机与destination进行绑定,使用 routingkey 作为路由键
		Binding binding = new Binding("hello.java.queue",Binding.DestinationType.QUEUE,"hello.java.exchange","hello.java",null);
		amqpAdmin.declareBinding(binding);
		System.out.println("Binding 创建成功");
	}

	/**
	 * 如何收发消息
	 */
	
	@Autowired
	RabbitTemplate redisTemplate;
	
	@Test
	public void testSendMessage(){
		// 发送消息 并为每一条消息指定唯一 id
		String message="测试消息";
		redisTemplate.convertAndSend("hello.java.exchange","hello.java",message,new CorrelationData(UUID.randomUUID().toString()));
		System.out.println("消息发送成功");
	}

	@Test
	public void testSendClassMessage(){
		// 发送消息: 如果消息是一个对象, 会使用序列化机制将对象写出去
		// 使得发送对象类型的消息是一个 json 格式的
		// 需要在容器中注入MessageConverter
		Person person=new Person("大明",12);
		redisTemplate.convertAndSend("hello.java.exchange","hello.java",person);
		System.out.println("消息发送成功");
	}
 

} 