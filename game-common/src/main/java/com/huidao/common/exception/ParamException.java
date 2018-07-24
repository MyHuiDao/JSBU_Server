package com.huidao.common.exception;

/**
 * 参数异常类
 * @author lenovo
 *
 */
public class ParamException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ParamException(String msg) {
		super(msg);
	}
	
	/**
	 * 参数为空
	 */
	public final static ParamException param_not_exception=new ParamException("参数不能为空");
	
	/**
	 * 参数非法
	 */
	public final static ParamException param_exception=new ParamException("参数非法");
}
