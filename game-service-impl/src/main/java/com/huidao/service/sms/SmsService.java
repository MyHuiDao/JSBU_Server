package com.huidao.service.sms;

import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.huidao.common.cache.manager.CacheContants;
import com.huidao.common.cache.redis.RedisCache;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.sms.ISmsService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.huidao.common.util.SmsUtil;
import com.huidao.common.util.ValidateUtil;

/**
 * 短信接口
 * 
 * @author Administrator
 *
 */
@Service(timeout=10000)
@Component
public class SmsService implements ISmsService {

	@Override
	public void sendValidateCode(String mobile, Integer timeOut) {
		if (StringUtils.isBlank(mobile)) {
			throw ParamException.param_not_exception;
		}
		if (timeOut == null) {
			throw ParamException.param_not_exception;
		}
		if (!ValidateUtil.isMobile(mobile)) {
			throw ParamException.param_exception;
		}
		String code = (ThreadLocalRandom.current().nextInt(8999) + 1000) + "";
		String msg = code + ",短信验证码" + (timeOut / 60) + "分钟内有效【辉道科技】";
		System.out.println("发送短信:" + msg);
		RedisCache.set(CacheContants.sms_message + mobile, code, timeOut);
		SmsUtil.sendValidateCode(mobile, code);
	}

	@Override
	public MsgDto<String> validateCode(String mobile, String code) {
		if (StringUtils.isBlank(mobile)) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(code)) {
			throw ParamException.param_not_exception;
		}
		if (!ValidateUtil.isMobile(mobile)) {
			throw ParamException.param_exception;
		}
		String cacheCode = RedisCache.get(CacheContants.sms_message + mobile);
		if (StringUtils.isBlank(cacheCode)) {
			return MsgFactory.failMsg("验证码未发送或者已过期");
		}
		if (!code.equals(cacheCode)) {
			return MsgFactory.failMsg("验证码输入错误");
		}
		RedisCache.remove(CacheContants.sms_message + mobile);
		return MsgFactory.success();
	}

}
