package com.huidao.common.util.validate;

public interface IValidateCodeDB {

	/**
	 * 存取code
	 */
	public void save(String key, String value);

	/**
	 * 取
	 */
	public String get(String key);

	/**
	 * 删
	 */
	public void del(String key);
}
