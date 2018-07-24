package com.huidao.admin.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SystemConfig {

	@Value("${upload_url}")
	private String uploadUrl;
	
	@Value("${qr_code_show}")
	private String qrCodeShow;
	
	
	@Value("${proxy_register}")
	private String proxyRegister;

	public String getUploadUrl() {
		return uploadUrl;
	}

	public void setUploadUrl(String uploadUrl) {
		this.uploadUrl = uploadUrl;
	}

	public String getQrCodeShow() {
		return qrCodeShow;
	}

	public void setQrCodeShow(String qrCodeShow) {
		this.qrCodeShow = qrCodeShow;
	}

	public String getProxyRegister() {
		return proxyRegister;
	}

	public void setProxyRegister(String proxyRegister) {
		this.proxyRegister = proxyRegister;
	}
	
	

}
