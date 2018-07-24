package com.huidao.game.base.message.dto;

/**
 * 封装消息类
 * 
 * @author tzd
 *
 */
public class MsgDto {

	public MsgDto() {
	}

	public MsgDto(String code, Object data) {
		this.code = code;
		this.data = data;
	}

	/**
	 * 操作类型code
	 */
	private String code;

	/**
	 * 返回数据
	 */
	private Object data;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Object getContent() {
		return data;
	}

	public void setContent(Object data) {
		this.data = data;
	}

}
