package com.huidao.service.game;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.huidao.common.contants.GameSettingContants;
import com.huidao.common.dto.GetOutGoldDto;
import com.huidao.common.entity.User;
import com.huidao.common.entity.UserGoldLog;
import com.huidao.common.enums.GoldSourceType;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.game.IGameSettingService;
import com.huidao.common.interfaces.game.IUserGoldLogService;
import com.yehebl.orm.DBDao;
import com.yehebl.orm.data.common.dto.Page;

@Service
@Component
public class UserGoldLogService implements IUserGoldLogService {

	@Autowired
	private DBDao dbDao;

	@Autowired
	private IGameSettingService gameSettingService;

	@Override
	public BigDecimal getSumGoldByType(String userId, GoldSourceType gst) {
		if (StringUtils.isBlank(userId)) {
			throw ParamException.param_exception;
		}
		if (gst == null) {
			throw ParamException.param_exception;
		}
		BigDecimal payGold = dbDao
				.getResultObjectBySql("select sum(gold_num) gold from user_gold_log where user_id=? and source_type=?",
						userId, gst.getType())
				.bigDecimalValue();

		return payGold == null ? BigDecimal.ZERO : payGold;
	}

	@Override
	public GetOutGoldDto getGetOutGold(String userId) {
		if (StringUtils.isBlank(userId)) {
			throw ParamException.param_not_exception;
		}
		BigDecimal bonus = dbDao
				.getResultObjectBySql(
						"select sum(gold) num from user_pay_gold_bonus_log where user_id=? group by user_id;", userId)
				.bigDecimalValue();
		bonus = bonus == null ? BigDecimal.ZERO : bonus;
		GetOutGoldDto gg = new GetOutGoldDto();
		BigDecimal payGold = getSumGoldByType(userId, GoldSourceType.pay);
		BigDecimal buyGold = getSumGoldByType(userId, GoldSourceType.buy);
		BigDecimal getCash = getSumGoldByType(userId, GoldSourceType.getCash);
		BigDecimal currentGold = dbDao.getResultObjectBySql("select gold from user where id=?", userId)
				.bigDecimalValue();
		BigDecimal safeGold = dbDao.getResultObjectBySql("select gold from safe_gold where user_id=?", userId)
				.bigDecimalValue();

		currentGold = currentGold == null ? BigDecimal.ZERO : currentGold;
		safeGold = safeGold == null ? BigDecimal.ZERO : safeGold;
		gg.setCostGold(payGold.subtract(buyGold).subtract(getCash).subtract(bonus));
		gg.setCurrentGold(currentGold.add(safeGold));
		return gg;
	}

	/**
	 * 获取用户金币情况
	 */
	@Override
	public Page<UserGoldLog> findUserGoldLogAll(Integer page, Integer size, String userId, String sourceType,
			String gameType) {
		if (StringUtils.isBlank(userId)) {
			throw ParamException.param_not_exception;
		}
		Map<String, Object> maps = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(sourceType)) {
			maps.put("EQ_sourceType", sourceType);
		}
		if (StringUtils.isNotBlank(gameType)) {
			maps.put("EQ_gameType", gameType);
		}
		maps.put("EQ_userId", userId);
		maps.put("ORDER_createDate", "desc");
		return dbDao.findPageByMap(page, size, maps, UserGoldLog.class);
	}

	@Override
	public List<UserGoldLog> findUserGoldLogData(Integer size, String userId, String startDate, String endDate,
			String gameType) {
		Map<String, Object> parms = new HashMap<String, Object>();
		if (StringUtils.isBlank(userId)) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isNotBlank(startDate)) {
			parms.put("GEQ_createDate", startDate);
		}
		if (StringUtils.isNotBlank(endDate)) {
			parms.put("LEQ_createDate", endDate);
		}
		if (StringUtils.isNotBlank(gameType)) {
			parms.put("EQ_gameType", gameType);
		}
		parms.put("EQ_userId", userId);
		parms.put("ORDER_createDate", "ASC");
		return dbDao.findByMap(parms, size, UserGoldLog.class);
	}

	@Override
	public GetOutGoldDto getGetOutExperience(String userId) {
		if (StringUtils.isBlank(userId)) {
			throw ParamException.param_not_exception;
		}
		Long value = Long.valueOf(gameSettingService.getValue(GameSettingContants.init_experience_num));
		User user = dbDao.get(userId, User.class);
		if (user == null) {
			throw ParamException.param_exception;
		}
		GetOutGoldDto gg = new GetOutGoldDto();
		gg.setCostGold(BigDecimal.valueOf(value));
		gg.setCurrentGold(BigDecimal.valueOf(user.getExperience()));
		return gg;
	}

	@Override
	public BigDecimal getSumGoldByType(String userId, GoldSourceType gst, Date startDate, Date endDate) {
		if (StringUtils.isBlank(userId)) {
			throw ParamException.param_exception;
		}
		if (gst == null) {
			throw ParamException.param_exception;
		}
		if (startDate == null) {
			throw ParamException.param_exception;
		}
		if (endDate == null) {
			throw ParamException.param_exception;
		}
		BigDecimal payGold = dbDao.getResultObjectBySql(
				"select sum(gold_num) gold from user_gold_log where user_id=? and source_type=? and create_date>? and create_date<?",
				userId, gst.getType(), startDate, endDate).bigDecimalValue();

		return payGold == null ? BigDecimal.ZERO : payGold;
	}

}
