package com.huidao;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * dubbo 服务提供启动类
 * 
 * @author Administrator
 *
 */
public class Application {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		new ClassPathXmlApplicationContext("classpath:spring.xml").start();
		synchronized (Application.class) {
			while (true) {
				try {
					Application.class.wait();
				} catch (Throwable e) {
				}
			}
		}
	}
}
