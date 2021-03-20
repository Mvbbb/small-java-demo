package com.yzh.rabbitmqdemo.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: JavaLearning
 * @description: 用于测试json转换的实体类
 * @author: Mvbbb
 * @create: 2021-03-20 12:44
 */
@Data
public class Person implements Serializable {
	private String name;
	private Integer age;

	public Person(String name, Integer age) {
		this.name = name;
		this.age = age;
	}

	public Person() {
	}
}
