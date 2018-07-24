package com.huidao.common.msg;

/**
 * 消息实体工厂
 * 
 * @author lenovo
 *
 */
public class MsgFactory {
	public static final String success_code="0";
	public static final String fail_code="-1";

	public static <T>MsgDto<T> success() {
		return new MsgDto<>(success_code);
	}

	public static <T>MsgDto<T> fail() {
		return new MsgDto<>(fail_code);
	}

	public static <T>MsgDto<T> success(T data) {
		return new MsgDto<T>(success_code, data);
	}

	public static <T>MsgDto<T> fail(T data) {
		return new MsgDto<T>(fail_code, data);
	}

	public static <T>MsgDto<T> success(String code, T data) {
		return new MsgDto<T>(code, data);
	}

	public static <T>MsgDto<T> fail(String code, T data) {
		return new MsgDto<T>(code, data);
	}
	
	
	public static <T>MsgDto<T> successMsg(String msg) {
		MsgDto<T> msgDto = new MsgDto<T>(success_code);
		msgDto.setMsg(msg);
		return msgDto;
	}
	public static <T>MsgDto<T> successMsg(String code,String msg) {
		MsgDto<T> msgDto = new MsgDto<T>(code);
		msgDto.setMsg(msg);
		return msgDto;
	}

	public static <T>MsgDto<T> failMsg(String msg) {
		MsgDto<T> msgDto = new MsgDto<T>(fail_code);
		msgDto.setMsg(msg);
		return msgDto;
	}
	public static <T>MsgDto<T> failMsg(String code,String msg) {
		MsgDto<T> msgDto = new MsgDto<T>(code);
		msgDto.setMsg(msg);
		return msgDto;
	}
	
	
	public static boolean isSuccess(MsgDto<?> msg) {
		if(success_code.equals(msg.getCode())) {
			return true;
		}
		return false;
	}
	
	public static boolean isFail(MsgDto<?> msg) {
		if(fail_code.equals(msg.getCode())) {
			return true;
		}
		return false;
	}

}
