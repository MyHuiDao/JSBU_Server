package com.huidao.service.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.huidao.common.entity.GameArea;
import com.huidao.common.enums.GameType;
import com.huidao.common.interfaces.game.IGameAreaService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.DBDao;

@Service
@Component
public class GameAreaService implements IGameAreaService {

	
	@Autowired
	private DBDao dbDao;
	
	@Override
	public GameArea get(String id) {
		return dbDao.get(id, GameArea.class);
	}

	@Override
	public MsgDto<List<GameArea>> findGameAreaType(String type) {
		GameType.valueOf(type);
		Map<String, Object> map = new HashMap<>();
		map.put("NEQ_status", "2");
		map.put("EQ_gameType", type);
		map.put("ORDER_seq", "asc");
		return MsgFactory.success(dbDao.findByMap(map, GameArea.class));
	}
	
	

}
