package com.huidao.common.exception;

public class UserNoLoginException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserNoLoginException(String msg) {
		super(msg);
	}
}
