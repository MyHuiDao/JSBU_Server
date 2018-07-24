package com.huidao.service.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.huidao.common.entity.Goods;
import com.huidao.common.interfaces.game.IGoodsService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.DBDao;


@Service
@Component
public class GoodsService implements IGoodsService {
	@Autowired
	private DBDao dbDao;
	@Override
	public MsgDto<List<Goods>> getGoods(String classId) {
		Map<String,Object> map=new HashMap<>();
		map.put("ORDER_seq", "asc");
		if(StringUtils.isNotBlank(classId)) {
			map.put("EQ_shopClassId", classId);
		}
		return MsgFactory.success(dbDao.findByMap(map, Goods.class));
	}
}
