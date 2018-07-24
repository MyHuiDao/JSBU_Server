package com.huidao.common.util.validate;

import org.springframework.stereotype.Service;

import com.huidao.common.spring.SpringServlet;

@Service("validateCodeDB")
public class ValidateCodeDB implements IValidateCodeDB {

	@Override
	public void save(String key, String value) {
		SpringServlet.getSession().setAttribute(key, value);
	}

	@Override
	public String get(String key) {
		return SpringServlet.getSession().getAttribute(key).toString();
	}

	@Override
	public void del(String key) {
		SpringServlet.getSession().removeAttribute(key);
	}

}
