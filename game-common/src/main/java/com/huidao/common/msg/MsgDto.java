package com.huidao.common.msg;

import java.io.Serializable;

/**
 * 封装消息类
 * 
 * @author tzd
 *
 */
public class MsgDto<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	public MsgDto() {
	}

	public MsgDto(String code, T data) {
		this.code = code;
		this.data = data;
	}
	
	public MsgDto(String code) {
		this.code = code;
	}

	/**
	 * 操作类型code
	 */
	private String code;

	/**
	 * 返回数据
	 */
	private T data;
	
	/**
	 * 消息
	 */
	private String msg;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	

}
