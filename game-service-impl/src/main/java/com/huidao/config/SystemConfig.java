package com.huidao.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SystemConfig {
	/**
	 * 用户token过期时间
	 */
	@Value("${token_time}")
	private Integer tokenTime;

	@Value("${id_sum}")
	private Integer idSum;

	@Value("${id_min}")
	private Integer idMin;

	@Value("${weixin_app_id}")
	private String weixinAppId;

	@Value("${weixin_app_secret}")
	private String weixinAppSecret;

	@Value("${weixin_public_id}")
	private String weixinPublicAppId;

	@Value("${weixin_public_secret}")
	private String weixinPublicSecret;

	@Value("${junfutong_pay_key}")
	private String junfutongPayKey;

	@Value("${junfutong_yinyongNum}")
	private String junfutongYinyongNum;

	public Integer getTokenTime() {
		return tokenTime;
	}

	public void setTokenTime(Integer tokenTime) {
		this.tokenTime = tokenTime;
	}

	public Integer getIdSum() {
		return idSum;
	}

	public void setIdSum(Integer idSum) {
		this.idSum = idSum;
	}

	public Integer getIdMin() {
		return idMin;
	}

	public void setIdMin(Integer idMin) {
		this.idMin = idMin;
	}

	public String getWeixinAppId() {
		return weixinAppId;
	}

	public void setWeixinAppId(String weixinAppId) {
		this.weixinAppId = weixinAppId;
	}

	public String getWeixinAppSecret() {
		return weixinAppSecret;
	}

	public void setWeixinAppSecret(String weixinAppSecret) {
		this.weixinAppSecret = weixinAppSecret;
	}

	public String getWeixinPublicAppId() {
		return weixinPublicAppId;
	}

	public void setWeixinPublicAppId(String weixinPublicAppId) {
		this.weixinPublicAppId = weixinPublicAppId;
	}

	public String getWeixinPublicSecret() {
		return weixinPublicSecret;
	}

	public void setWeixinPublicSecret(String weixinPublicSecret) {
		this.weixinPublicSecret = weixinPublicSecret;
	}

	public String getJunfutongPayKey() {
		return junfutongPayKey;
	}

	public void setJunfutongPayKey(String junfutongPayKey) {
		this.junfutongPayKey = junfutongPayKey;
	}

	public String getJunfutongYinyongNum() {
		return junfutongYinyongNum;
	}

	public void setJunfutongYinyongNum(String junfutongYinyongNum) {
		this.junfutongYinyongNum = junfutongYinyongNum;
	}

}
