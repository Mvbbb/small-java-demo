package com.yzh.rabbitmqdemo;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.yzh.rabbitmqdemo.bean.Person;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @program: JavaLearning
 * @description: <p>
 * 用于监听queue中的消息
 * 测试监听消息, 必须开启 @EnableRabbit 注解
 * queues 指定需要监听的所有队列</p>
 * @author: Mvbbb
 * @create: 2021-03-20 12:56
 */
@Service
public class Consumer {
	/**
	 * 
	 * @param message 可以有很多类型, 
	 *                Message: 原生消息类型, 头+体
	 *                <T> 发送消息的类型,[比如说json对于的Java类型]
	 *                Channel: 当前传输数据的通道
	 *  Queue: 可以很多人来监听,但是只能由一个人接收到消息, 接受消息之后队列删除消息.
	 *    监听者在接收消息时, 只有将一个消息处理完才可以接收下一个消息.
	 *                
	 *  监听消息的两个注解
	 * @RabbitListener: 类+方法上 (监听哪些队列)
	 * @RabbitHandler: 方法上, 用于重载区分不同类型的消息
	 */
//	@RabbitListener(queues = {"hello.java.queue"})
//	public void recieveMessage(Object message) {
//		System.out.println("接收到消息"+message+"==>>类型"+message.getClass());
//	}

	/**
	 * 消费端确认 (保证每一个消息被正确的消费,此时才可以 broker 删除这个消息, 默认是自动确认的, 只要消息接收到, 客户端就会自动确认, 服务端就会移除这个消息. 
	 * 问题, 我们收到很多消息自动回复给服务器ack, 只有一个消息处理成功,宕机了, 会发生消息丢失.
	 * 手动确认模式,只要没有回复 MQ ack, 消息就一直是 unacked状态, 即使 consumer 宕机, 消息也不会发生丢失, 会重新变为 ready 状态,可以由另外的 consumer 来接收.
	 */
	@RabbitListener(queues = {"hello.java.queue"})
	public void receiveMessageWithAck(Message message, Person person, Channel channel) throws IOException {
		System.out.println("接收到消息"+message+"==>>类型"+message.getClass());
		System.out.println("消息处理完成");
		// channel 内顺序自增, 每条消息占用的 channel 序号可能不一样
		long deliveryTag = message.getMessageProperties().getDeliveryTag();
		System.out.println(deliveryTag);
		// 签收message, 非批量模式
		channel.basicAck(deliveryTag, false);
		// 拒绝message
		//void basicNack(long deliveryTag, boolean multiple, boolean requeue) 
		//requeue为 false 那么直接丢弃该消息. 为 true,发回mq,重新入队
		//void basicReject(long deliveryTag, boolean requeue)
		//channel.basicNack(deliveryTag,false,false);
		//channel.basicReject();
	}
}