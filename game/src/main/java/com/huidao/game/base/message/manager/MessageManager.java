package com.huidao.game.base.message.manager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.huidao.game.base.message.dto.MsgDto;
import com.huidao.game.base.session.SessionManager;
import com.huidao.game.base.spring.JSONDto;

@Component
public class MessageManager {

	@Autowired
	private SessionManager sessionManager;

	public static ThreadLocal<JSONDto> currentJson = new ThreadLocal<JSONDto>();

	private static final Logger log = LoggerFactory.getLogger(MessageManager.class);

	public MsgDto getMsg(String code, Object data) {
		return new MsgDto(code, data);
	}

	private String getMsgToString(MsgDto md) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", Integer.valueOf(md.getCode()));
		map.put("data", md.getContent());
		JSONDto jd = currentJson.get();
		if (jd != null) {
			Class<?>[] clazz = jd.getClazz();
			String[] property = jd.getProperty();
			String[] filterProperty = jd.getFilterProperty();
			if (clazz.length != property.length && clazz.length != filterProperty.length) {
				log.error("JSON注解clazz与property或者filterProperty的长度不相等");
				return null;
			}
			SimplePropertyPreFilter[] filters = new SimplePropertyPreFilter[clazz.length];
			for (int i = 0; i < filters.length; i++) {
				filters[i] = new SimplePropertyPreFilter(clazz[i]);
				if (i < property.length) {
					String[] proertyStr = property[i].split(",");
					for (int j = 0; j < proertyStr.length; j++) {
						filters[i].getIncludes().add(proertyStr[j]);
					}
				}
				if (i < filterProperty.length) {
					String[] filterPropertyStr = filterProperty[i].split(",");
					for (int j = 0; j < filterPropertyStr.length; j++) {
						filters[i].getExcludes().add(filterPropertyStr[j]);
					}
				}

			}
			return JSONObject.toJSONString(map, filters, SerializerFeature.DisableCircularReferenceDetect);
		} else {
			return JSONObject.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
		}
	}

	/**
	 * 本机session消息发送
	 * 
	 * @param session
	 * @param md
	 */
	public boolean sendMsg(Session session, MsgDto md) {
		String msg = null;
		try {
			if (session == null || !session.isOpen()) {
				return false;
			}
			msg = getMsgToString(md);
			log.info("发送消息：{},userId:{}", msg, sessionManager.getUserId(session));
			session.getBasicRemote().sendText(msg);
			return true;
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			try {
				session.getBasicRemote().sendText(msg);
				return true;
			} catch (IOException e1) {
				try {
					session.close();
				} catch (IOException e2) {
					log.error(e2.getMessage(), e2);
				}
				log.error(e1.getMessage(), e1);
				return false;
			}

		}
	}

	/**
	 * 本机session消息发送
	 * 
	 * @param session
	 * @param md
	 */
	public boolean sendMsg(Session session, String code, Object data) {
		MsgDto msg = getMsg(code, data);
		return sendMsg(session, msg);

	}

	public boolean sendMsg(Session session, String code) {
		MsgDto msg = getMsg(code, null);
		return sendMsg(session, msg);
	}

	/**
	 * 发送消息给指定用户,用于日后集群
	 * 
	 * @param userId
	 * @param md
	 */
	public boolean sendMsg(String userId, MsgDto md) {
		try {
			Session session = sessionManager.getSession(userId);
			if (session == null) {
				log.error("用户找不到对应的session");
				return false;
			}
			return sendMsg(session, md);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}

	public boolean sendMsg(String userId, String code, Object data) {
		MsgDto msg = getMsg(code, data);
		return sendMsg(userId, msg);
	}

	public boolean sendMsg(String userId, String code) {
		MsgDto msg = getMsg(code, null);
		return sendMsg(userId, msg);
	}

}
