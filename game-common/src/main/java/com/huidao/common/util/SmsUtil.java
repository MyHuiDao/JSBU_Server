package com.huidao.common.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class SmsUtil {
	
	  //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";
    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    static final String accessKeyId = "LTAIj1pWwGQ8lVse";
    static final String accessKeySecret = "CFaKShF2DFVr2kh1MWVpMyPR2FhXUr";
    
    public static void sendValidateCode(String mobile,String code) {
    	sendMsg(mobile, "SMS_129763295", "{\"code\":\""+code+"\"}");
    }
	public static void sendMsg(String mobile,String tempCode ,String data) {
		if(!ValidateUtil.isMobile(mobile)) {
			throw new RuntimeException("短信发送失败"+mobile+"不是手机号码");
		}
		  //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        try {
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		} catch (ClientException e1) {
			throw new RuntimeException("短信发送失败");
		}
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(mobile);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("金沙渔港");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(tempCode);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam(data);

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch
        try {
        	SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        	if(sendSmsResponse.getCode()==null||!sendSmsResponse.getCode().equals("OK")) {
        		throw new RuntimeException("短信发送失败");
        	}
		} catch (Exception e) {
			throw new RuntimeException("短信发送失败");
		}
	}

}
