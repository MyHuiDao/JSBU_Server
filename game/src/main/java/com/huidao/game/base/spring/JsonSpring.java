package com.huidao.game.base.spring;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.huidao.common.anno.JSON;
import com.huidao.game.base.message.manager.MessageManager;

@Aspect
@Component
public class JsonSpring {
	private static final Logger log = LoggerFactory.getLogger(JsonSpring.class);

	// Service层切点
	@Around("@annotation(com.huidao.common.anno.JSON)")
	public Object serviceAspect(ProceedingJoinPoint pjp) throws Exception {
		Object obj = null;
		try {
			String methodName = pjp.getSignature().getName();
			Class<?> classTarget = pjp.getTarget().getClass();
			Class<?>[] par = ((MethodSignature) pjp.getSignature()).getParameterTypes();
			Method objMethod = classTarget.getMethod(methodName, par);
			JSON json = objMethod.getAnnotation(JSON.class);
			if (json != null) {
				JSONDto jd = new JSONDto();
				jd.setClazz(json.clazz());
				jd.setFilterProperty(json.filterProperty());
				jd.setProperty(json.property());
				MessageManager.currentJson.set(jd);
			}
			obj = pjp.proceed();
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new Exception(e.getMessage()); // 交给异常处理器统一处理
		}
		return obj;
	}
}
