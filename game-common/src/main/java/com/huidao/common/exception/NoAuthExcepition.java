package com.huidao.common.exception;

public class NoAuthExcepition extends RuntimeException {
	public static final NoAuthExcepition noAuthExcepition=new NoAuthExcepition();
	private static final long serialVersionUID = 1L;
	public NoAuthExcepition() {
		super("没有权限");
	}
}
