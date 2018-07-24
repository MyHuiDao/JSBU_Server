package com.huidao.common.spring;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.huidao.common.anno.JSON;

public class ResponseJson implements HandlerMethodReturnValueHandler {
	
	@Override
	public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest) throws Exception {
		mavContainer.setRequestHandled(true);
		HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;utf-8");
		JSON jsonAnno = returnType.getMethodAnnotation(JSON.class);
		Class<?>[] clazz = jsonAnno.clazz();
		String[] property = jsonAnno.property();
		String[] filterProperty = jsonAnno.filterProperty();
		if (clazz.length != property.length && clazz.length != filterProperty.length) {
			throw new Exception("JSON注解clazz与property或者filterProperty的长度不相等");
		}
		SimplePropertyPreFilter[] filters = new SimplePropertyPreFilter[clazz.length];

		for (int i = 0; i < filters.length; i++) {
			filters[i] = new SimplePropertyPreFilter(clazz[i]);
			if (i < property.length) {
				String[] proertyStr = property[i].split(",");
				for (int j = 0; j < proertyStr.length; j++) {
					filters[i].getIncludes().add(proertyStr[j]);
				}
			}
			if (i < filterProperty.length) {
				String[] filterPropertyStr = filterProperty[i].split(",");
				for (int j = 0; j < filterPropertyStr.length; j++) {
					filters[i].getExcludes().add(filterPropertyStr[j]);
				}
			}

		}
		String json = JSONObject.toJSONString(returnValue, filters,SerializerFeature.DisableCircularReferenceDetect);
		response.getWriter().write(json);
	}

	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		boolean hasJsonAnno = returnType.getMethodAnnotation(JSON.class) != null;
		return hasJsonAnno;
	}

}
