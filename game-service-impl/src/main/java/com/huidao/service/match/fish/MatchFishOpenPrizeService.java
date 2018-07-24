package com.huidao.service.match.fish;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.huidao.common.entity.MatchFishOpenPrize;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.match.fish.IMatchFishOpenPrizeService;
import com.yehebl.orm.DBDao;

@Component
@Service
public class MatchFishOpenPrizeService implements IMatchFishOpenPrizeService {

	@Autowired
	private DBDao dbDao;

	@Override
	public String insterMatchFishOpenPrize(String roomId) {
		MatchFishOpenPrize matchFishOpenPrize = new MatchFishOpenPrize();
		matchFishOpenPrize.setRoomId(roomId);
		matchFishOpenPrize.setCreateDate(new Date());
		matchFishOpenPrize.setStatus(0);
		dbDao.save(matchFishOpenPrize);
		return matchFishOpenPrize.getId();
	}

	@Override
	public List<MatchFishOpenPrize> findMatchFishOpenPrize(Integer page, Integer size, String roomId, Date createDate) {
		if (page == null) {
			page = 1;
		}
		if (size == null) {
			size = 10;
		}
		if (StringUtils.isBlank(roomId)) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(createDate.toString())) {
			throw ParamException.param_not_exception;
		}
		return dbDao.createExpressionMap().eq("roomId", roomId).geq("openDate", createDate).orderDesc("openDate")
				.limit(page, size).find(MatchFishOpenPrize.class);
	}
}
