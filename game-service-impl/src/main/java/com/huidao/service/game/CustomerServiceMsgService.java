package com.huidao.service.game;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.huidao.common.entity.CustomerServiceMsg;
import com.huidao.common.entity.User;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.game.ICustomerServiceMsgService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.DBDao;

@Service
@Component
public class CustomerServiceMsgService implements ICustomerServiceMsgService {

	@Autowired
	private DBDao dbDao;

	@Override
	@Transactional
	public MsgDto<Boolean> save(CustomerServiceMsg customerServiceMsg) {
		if (customerServiceMsg == null) {
			throw ParamException.param_not_exception;
		}
		if (customerServiceMsg.getMsg() == null) {
			throw ParamException.param_not_exception;
		}
		dbDao.save(customerServiceMsg);
		return MsgFactory.success(true);
	}

	@Override
	public MsgDto<Boolean> update(String id, int status) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		CustomerServiceMsg customerServiceMsg = dbDao.get(id, CustomerServiceMsg.class);
		customerServiceMsg.setStatus(status);
		dbDao.update(customerServiceMsg);
		return MsgFactory.success(true);
	}

	@Override
	public MsgDto<List<CustomerServiceMsg>> userFindNoSendMsg(String userId) {
		return MsgFactory.success(dbDao.createExpressionMap().eq("status", 2).eq("userId", userId).eq("type", 0)
				.order("createTime").find(CustomerServiceMsg.class));
	}

	@Override
	public MsgDto<List<CustomerServiceMsg>> sysUserFindNoSendMsg(String userId) {
		return MsgFactory.success(dbDao.createExpressionMap().eq("status", 2).eq("userId", userId).eq("type", 1)
				.order("createTime").find(CustomerServiceMsg.class));
	}

	@Override
	public MsgDto<List<User>> findUserNoSendMsg() {
		return MsgFactory.success(dbDao.createQueryXmlSql("findUserNoSendMsg").find(User.class));
	}

}
