package com.huidao.common.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringBeanUtil implements ApplicationContextAware {
	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext ac) throws BeansException {
		applicationContext = ac;
	}

	public static Object getBean(Class<?> clazz) {
		return applicationContext.getBean(clazz);
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

}
