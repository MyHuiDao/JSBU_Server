package com.huidao.common.interfaces.sms;

import com.huidao.common.msg.MsgDto;

/**
 * 手机号码短信发送端口
 * @author Administrator
 *
 */
public interface ISmsService {
	
	/**
	 * 
	 * 发送手机验证码
	 * @param content 发送内容
	 * @param mobile 手机号码
	 * @param timeOut 验证码失效时间
	 */
	public void sendValidateCode(String mobile,Integer timeOut);
	
	/**
	 * 验证 验证码
	 * @param mobile 手机号
	 * @param code 验证码
	 * @return
	 */
	public MsgDto<String> validateCode(String mobile,String code);

}
