package com.huidao.service.match.fish;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.huidao.common.entity.MatchFishBets;
import com.huidao.common.entity.MatchFishOpenPrize;
import com.huidao.common.enums.GameType;
import com.huidao.common.enums.GoldSourceType;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.game.IUserService;
import com.huidao.common.interfaces.match.fish.IMatchFishBetsService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.DBDao;

@Service
@Component
public class MatchFishBetsService implements IMatchFishBetsService {

	@Autowired
	private DBDao dbDao;

	@Autowired
	private IUserService userService;

	@Override
	@Transactional
	public MsgDto<Integer> saveMatchFishBets(MatchFishBets matchFishBets) {
		try {
			MatchFishOpenPrize matchFishOpenPrize = dbDao.get(matchFishBets.getOpenPrizeId(), MatchFishOpenPrize.class);
			if (matchFishOpenPrize.getStatus() != 0) {
				return MsgFactory.failMsg("该期已开奖，请重新下注");
			}
			MsgDto<String> msg = userService.reduceGold(matchFishBets.getUserId(),
					Long.valueOf(matchFishBets.getBetsGold()), GoldSourceType.lose, GameType.match_fish, null);
			if (MsgFactory.isFail(msg)) {
				return MsgFactory.failMsg("金币不足");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return MsgFactory.success(dbDao.save(matchFishBets));
	}

	@Override
	public List<MatchFishBets> getUserMatchFishBets(String openPrizeId, String userId) {
		if (StringUtils.isBlank(openPrizeId)) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(userId)) {
			throw ParamException.param_not_exception;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_openPrizeId", openPrizeId);
		map.put("EQ_userId", userId);
		return dbDao.findByMap(map, MatchFishBets.class);
	}

}
