package com.huidao.service.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.huidao.common.entity.UserShare;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.admin.IUserShareService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.DBDao;

@Component
@Service
public class UserShareService implements IUserShareService {

	@Autowired
	private DBDao dbDao;

	@Override
	public Integer findUserShareAll(String userId) {

		return dbDao.getResultObjectBySql(
				"select count(1) from user_share us where us.game_user_id=? and DATE_FORMAT(us.createDate,'%Y-%m-%d')=DATE_FORMAT(NOW(),'%Y-%m-%d')",
				userId).integerValue();
	}

	@Override
	@Transactional
	public MsgDto<String> add(UserShare userShare) {
		if (userShare == null) {
			throw ParamException.param_not_exception;
		}
		dbDao.save(userShare);
		return MsgFactory.success();
	}

}
