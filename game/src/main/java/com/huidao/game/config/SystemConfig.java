package com.huidao.game.config;

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

	@Value("${validate_code_time}")
	private Integer validateCodeTime;

	@Value("${weixin_app_id}")
	private String weixinAppId;

	@Value("${weixin_secret}")
	private String weixinSecret;

	@Value("${weixin_redirect_uri}")
	private String weixinRedirectUri;
	
	@Value("${game_down_load}")
	private String gameDownLoad;
	
	@Value("${proxy_register}")
	private String proxyRegister;
	
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

	public Integer getValidateCodeTime() {
		return validateCodeTime;
	}

	public void setValidateCodeTime(Integer validateCodeTime) {
		this.validateCodeTime = validateCodeTime;
	}

	public String getWeixinAppId() {
		return weixinAppId;
	}

	public void setWeixinAppId(String weixinAppId) {
		this.weixinAppId = weixinAppId;
	}

	public String getWeixinSecret() {
		return weixinSecret;
	}

	public void setWeixinSecret(String weixinSecret) {
		this.weixinSecret = weixinSecret;
	}

	public String getWeixinRedirectUri() {
		return weixinRedirectUri;
	}

	public void setWeixinRedirectUri(String weixinRedirectUri) {
		this.weixinRedirectUri = weixinRedirectUri;
	}

	public String getGameDownLoad() {
		return gameDownLoad;
	}

	public void setGameDownLoad(String gameDownLoad) {
		this.gameDownLoad = gameDownLoad;
	}
	public String getProxyRegister() {
		return proxyRegister;
	}

	public void setProxyRegister(String proxyRegister) {
		this.proxyRegister = proxyRegister;
	}
	
}
