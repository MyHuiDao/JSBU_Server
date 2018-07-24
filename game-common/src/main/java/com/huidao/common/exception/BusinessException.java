package com.huidao.common.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String code="-1";

	public BusinessException(int code, String msg) {
		super(msg);
		this.code = msg;
	}

	public BusinessException(String msg) {
		super(msg);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	

}
