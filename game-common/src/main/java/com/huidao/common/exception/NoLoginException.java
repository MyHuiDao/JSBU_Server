package com.huidao.common.exception;

public class NoLoginException extends RuntimeException {
	
	public static final NoLoginException noLoginException=new NoLoginException();

	private static final long serialVersionUID = 1L;
	public NoLoginException() {
		super("用户未登录");
	}
}
