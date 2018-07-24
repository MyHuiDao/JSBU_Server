package com.huidao.match.base.handler;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huidao.match.base.spring.SpringBeanService;
import com.yehebl.handler.HandlerUtil;
import com.yehebl.handler.IGetInstance;

@Component
public class InitListener {

	@Autowired
	private SpringBeanService springBeanService;
	@PostConstruct
	public void init() {
		HandlerUtil.setIGetInstance(new IGetInstance() {
			@Override
			public <T>T get(Class<T> clazz) {
				return springBeanService.getBean(clazz);
			}
		});
		HandlerUtil.Init("com.huidao.match.game");
	}

}
