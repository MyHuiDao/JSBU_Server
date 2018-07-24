package com.huidao.game.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huidao.common.cache.redis.RedisCache;
import com.huidao.common.entity.CustomerServiceMsg;
import com.huidao.common.entity.User;
import com.huidao.common.interfaces.game.ICustomerServiceMsgService;
import com.huidao.game.base.session.CustmoterSessionManager;

public class CustmoterServiceThread extends Thread {
	
	private static final Logger log=LoggerFactory.getLogger(CustmoterServiceThread.class);

	private ICustomerServiceMsgService customerServiceMsgService;

	private CustmoterSessionManager custmoterSessionManager;
	public CustmoterServiceThread(ICustomerServiceMsgService customerServiceMsgService,CustmoterSessionManager custmoterSessionManager) {
		this.customerServiceMsgService = customerServiceMsgService;
		this.custmoterSessionManager=custmoterSessionManager;
	}

	@Override
	public void run() {
		RedisCache.lock("customerServiceMsgService");
		try {
			List<User> data = customerServiceMsgService.findUserNoSendMsg().getData();
			for (User user : data) {
				List<CustomerServiceMsg> data2 = customerServiceMsgService.sysUserFindNoSendMsg(user.getId()).getData();
				for (CustomerServiceMsg customerServiceMsg : data2) {
					custmoterSessionManager.sendMsg(customerServiceMsg);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			RedisCache.unlock("customerServiceMsgService");
		}
	}

}
