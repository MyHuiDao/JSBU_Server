package com.huidao.common.util;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;

public class AliPayUtil {

	public static final Logger log = LoggerFactory.getLogger(AliPayUtil.class);

	public static boolean getCash(String account, String name, double amount) throws AlipayApiException {
		String appid = PropertiesUtil.getProperties("/aliplay.properties", "aliplay.appId");
		String privateKey = PropertiesUtil.getProperties("/aliplay.properties", "aliplay.privateKey");
		String publicKey = PropertiesUtil.getProperties("/aliplay.properties", "aliplay.publicKey");
		// 公司名称
		String comPanyName = PropertiesUtil.getProperties("/aliplay.properties", "aliplay.comPanyName");
		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", appid, privateKey,
				"json", "GBK", publicKey, "RSA2");
		AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
		request.setBizContent("{" + "\"out_biz_no\":\"" + UUID.randomUUID().toString() + "\","
				+ "\"payee_type\":\"ALIPAY_LOGONID\"," + "\"payee_account\":\"" + account + "\"," + "\"amount\":\""
				+ amount + "\"," + "\"payer_show_name\":\"" + comPanyName + "\"," + "\"payee_real_name\":\"" + name
				+ "\"," + "\"remark\":\"转账备注\"" + "}");
		AlipayFundTransToaccountTransferResponse response = alipayClient.execute(request);
		log.info("支付宝付款返回：" + JSONObject.toJSONString(response));
		if (response.isSuccess()) {
			return true;
		} else {
			return false;
		}
	}
}
