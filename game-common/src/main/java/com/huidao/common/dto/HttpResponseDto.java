package com.huidao.common.dto;
import java.util.Map;

public class HttpResponseDto {
	/**
	 * 返回主体信息
	 */
	private String body;
	/**
	 * http头文件信息
	 */
	private Map<String,String> headers;
	
	
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public Map<String, String> getHeaders() {
		return headers;
	}
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
}