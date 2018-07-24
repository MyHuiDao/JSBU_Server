package com.huidao.service.admin;

import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.huidao.common.entity.ShareBonus;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.admin.IShareBonusService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.DBDao;

@Component
@Service
public class ShareBonusService implements IShareBonusService {
	@Autowired
	private DBDao dbDao;

	@Override
	public List<ShareBonus> findShareBonusAll() {

		return dbDao.findAll(ShareBonus.class);
	}

	@Override
	@Transactional
	public MsgDto<String> add(ShareBonus shareBouns) {
		if (shareBouns == null) {
			throw ParamException.param_not_exception;
		}
		if (shareBouns.getCount() == null) {
			throw ParamException.param_not_exception;
		}
		if (shareBouns.getGold() == null) {
			throw ParamException.param_not_exception;
		}
		dbDao.save(shareBouns);
		return MsgFactory.success();
	}

	@Override
	@Transactional
	public MsgDto<String> update(ShareBonus shareBouns) {
		if (shareBouns == null) {
			throw ParamException.param_not_exception;
		}
		if (shareBouns.getCount() == null) {
			throw ParamException.param_not_exception;
		}
		if (shareBouns.getGold() == null) {
			throw ParamException.param_not_exception;
		}
		dbDao.update(shareBouns);
		return MsgFactory.success();
	}

	@Override
	@Transactional
	public MsgDto<String> delete(String id) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		dbDao.deleteById(id, ShareBonus.class);
		return MsgFactory.success();
	}

	@Override
	public ShareBonus get(String id) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		return dbDao.get(id, ShareBonus.class);
	}

}
