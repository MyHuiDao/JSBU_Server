package com.huidao.service.game.notice;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.huidao.common.entity.GameNotice;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.game.notice.IGameNoticeService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.DBDao;
import com.yehebl.orm.data.common.ICopyProperties;

@Service
@Component
public class GameNoticeService implements IGameNoticeService {

	@Autowired
	private DBDao dbDao;

	@Override
	public MsgDto<List<GameNotice>> findByType(String type) {
		Map<String, Object> map = new HashMap<>();
		map.put("EQ_type", type);
		map.put("EQ_status", "1");
		map.put("ORDER_seq", "asc");
		return MsgFactory.success(dbDao.findByMap(map, GameNotice.class));
	}

	@Override
	public MsgDto<List<GameNotice>> findGameNoticeAll(String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(type)) {
			map.put("EQ_type", type);
		}
		return MsgFactory.success(dbDao.findByMap(map, GameNotice.class));
	}

	@Override
	@Transactional
	public MsgDto<Integer> add(GameNotice gameNotice) {
		if (gameNotice == null) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(gameNotice.getCenten())) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(gameNotice.getType())) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(gameNotice.getStatus())) {
			throw ParamException.param_not_exception;
		}
		if (gameNotice.getSeq() == null) {
			throw ParamException.param_not_exception;
		}
		if (gameNotice.getStatus().equals("1")) {
			gameNotice.setReleaseTime(new Date());
		}
		gameNotice.setCreateTime(new Date());
		return MsgFactory.success(dbDao.save(gameNotice));
	}

	@Override
	@Transactional
	public MsgDto<Integer> update(GameNotice gameNotice) {
		if (gameNotice == null) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(gameNotice.getCenten())) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(gameNotice.getType())) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(gameNotice.getStatus())) {
			throw ParamException.param_not_exception;
		}
		if (gameNotice.getSeq() == null) {
			throw ParamException.param_not_exception;
		}
		if (gameNotice.getStatus().equals("1")) {
			gameNotice.setReleaseTime(new Date());
		}
		dbDao.update(gameNotice, new ICopyProperties<GameNotice>() {
			@Override
			public void copy(GameNotice oldEntity, GameNotice newEntity) {
				newEntity.setCenten(oldEntity.getCenten());
				newEntity.setSeq(oldEntity.getSeq());
				newEntity.setStatus(oldEntity.getStatus());
				newEntity.setType(oldEntity.getType());
			}
		}, GameNotice.class);

		return MsgFactory.success();
	}

	@Override
	@Transactional
	public MsgDto<Integer> delete(String id) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		return MsgFactory.success(dbDao.deleteById(id, GameNotice.class));
	}

	@Override
	public MsgDto<GameNotice> get(String id) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		return MsgFactory.success(dbDao.get(id, GameNotice.class));
	}

}
