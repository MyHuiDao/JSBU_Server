package com.huidao.service.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.huidao.common.entity.EmailUser;
import com.huidao.common.entity.GameEmail;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.admin.IGameEmailService;
import com.huidao.common.interfaces.game.ISendNoticeService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.DBDao;
import com.yehebl.orm.data.common.dto.Page;
import com.yehebl.orm.data.common.xml.QueryXmlSql;

@Service
@Component
public class GameEmailService implements IGameEmailService {

	@Autowired
	private DBDao dbDao;

	@Reference
	private ISendNoticeService sendNoticeService;

	@Override
	public MsgDto<Page<GameEmail>> findGameEmailAll(Integer page, Integer size, String title, String status) {
		Map<String, Object> maps = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(title)) {
			maps.put("LIKE_title", title);
		}
		if (StringUtils.isNotBlank(status)) {
			maps.put("EQ_status", status);
		}
		maps.put("NEQ_status", 2);
		return MsgFactory.success(dbDao.findPageByMap(page, size, maps, GameEmail.class));
	}

	@Override
	@Transactional
	public MsgDto<Integer> add(GameEmail gameEmail) {
		if (gameEmail == null) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(gameEmail.getTitle())) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(gameEmail.getContent())) {
			throw ParamException.param_not_exception;
		}
		gameEmail.setCreateTime(new Date());
		return MsgFactory.success(dbDao.save(gameEmail));
	}

	@Override
	@Transactional
	public MsgDto<Integer> update(GameEmail gameEmail) {
		if (gameEmail == null) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(gameEmail.getTitle())) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(gameEmail.getContent())) {
			throw ParamException.param_not_exception;
		}
		return MsgFactory.success(dbDao.update(gameEmail));
	}

	@Override
	public MsgDto<Integer> delete(String id) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		GameEmail gameEmail = dbDao.get(id, GameEmail.class);
		gameEmail.setStatus(2);
		return MsgFactory.success(dbDao.update(gameEmail));
	}

	@Override
	public MsgDto<GameEmail> get(String id) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		return MsgFactory.success(dbDao.get(id, GameEmail.class));
	}

	@Override
	@Transactional
	public MsgDto<Integer> sendEmail(String emailId, String[] userIds) {
		if (StringUtils.isBlank(emailId)) {
			throw ParamException.param_not_exception;
		}
		if (userIds == null || userIds.length <= 0) {
			throw ParamException.param_not_exception;
		}
		for (int i = 0; i < userIds.length; i++) {
			EmailUser emailUser = new EmailUser();
			emailUser.setEmailId(emailId);
			emailUser.setUserId(userIds[i]);
			emailUser.setStatus(1);
			dbDao.save(emailUser);
			sendNoticeService.sendUserEmialNotice(userIds[i]);
		}
		return MsgFactory.success();
	}

	@Override
	@Transactional
	public MsgDto<Page<GameEmail>> getUserGameEmail(Integer page, Integer size, String userId) {
		QueryXmlSql queryXmlSql = new QueryXmlSql();
		queryXmlSql.addParams("id", userId);
		queryXmlSql.addParams("date", new Date());
		List<GameEmail> lists = getUserNotGameEmail(userId);
		for (GameEmail gameEmail : lists) {
			EmailUser emailUser = new EmailUser();
			emailUser.setUserId(userId);
			emailUser.setEmailId(gameEmail.getId());
			emailUser.setStatus(1);
			emailRelated(emailUser);
		}
		return MsgFactory
				.success(dbDao.findPageBySqlName("getUserGameEmail", queryXmlSql, page, size, GameEmail.class));
	}

	@Override
	public List<GameEmail> getUserNotGameEmail(String id) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		QueryXmlSql queryXmlSql = new QueryXmlSql();
		queryXmlSql.addParams("id", id);
		queryXmlSql.addParams("date", new Date());
		return dbDao.findBySqlName("getUserNotGameEmail", queryXmlSql, GameEmail.class);
	}

	@Override
	@Transactional
	public Integer emailRelated(EmailUser emailUser) {
		if (emailUser == null) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(emailUser.getUserId())) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(emailUser.getEmailId())) {
			throw ParamException.param_not_exception;
		}

		return dbDao.save(emailUser);
	}

}
