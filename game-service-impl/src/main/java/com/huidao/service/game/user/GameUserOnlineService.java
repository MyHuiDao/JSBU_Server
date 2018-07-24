package com.huidao.service.game.user;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.huidao.common.entity.UserOnline;
import com.huidao.common.enums.GameType;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.game.IGameUserOnlineService;
import com.yehebl.orm.DBDao;

@Service
@Component
public class GameUserOnlineService implements IGameUserOnlineService {
	
	@Autowired
	private DBDao dbDao;

	@Override
	@Transactional
	public void startGame(GameType type, String userId) {
		if(type==null) {
			throw ParamException.param_not_exception;
		}
		if(StringUtils.isBlank(userId)) {
			throw ParamException.param_not_exception;
		}
		Map<String, Object> parms=new HashMap<>();
		parms.put("EQ_name", type.toString());
		parms.put("EQ_userId", userId);
		parms.put("ORDER_onlineTimeStart", "desc");
		UserOnline uo = dbDao.getOneByMap(parms,UserOnline.class);
		if(uo!=null&&uo.getOnlineTimeEnd()==null) {
			return;
		}
		//60秒只能算持续在线
		if(uo!=null&&(new Date().getTime()-uo.getOnlineTimeEnd().getTime())/1000<60) {
			uo.setOnlineTimeEnd(null);
			dbDao.update(uo);
			return;
		}
		UserOnline nuo=new UserOnline();
		nuo.setName(type.toString());
		nuo.setUserId(userId);
		nuo.setOnlineTimeStart(new Date());
		dbDao.save(nuo);
	}

	@Override
	@Transactional
	public void endGame(GameType type, String userId) {
		if(type==null) {
			throw ParamException.param_not_exception;
		}
		if(StringUtils.isBlank(userId)) {
			throw ParamException.param_not_exception;
		}
		Map<String, Object> parms=new HashMap<>();
		parms.put("EQ_name", type.toString());
		parms.put("EQ_userId", userId);
		parms.put("ORDER_onlineTimeStart", "desc");
		UserOnline uo = dbDao.getOneByMap(parms,UserOnline.class);
		if(uo!=null&&uo.getOnlineTimeEnd()==null) {
			uo.setOnlineTimeEnd(new Date());
			dbDao.update(uo);
			return;
		}
		
	}

}
