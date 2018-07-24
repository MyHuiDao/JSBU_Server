package com.huidao.fishing.base.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringBeanService implements ApplicationContextAware {
	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext ac) throws BeansException {
		applicationContext = ac;
	}

	public <T> T getBean(Class<T> clazz) {
		return applicationContext.getBean(clazz);
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

}
