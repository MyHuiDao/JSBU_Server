package com.huidao.common.interfaces.game;

import java.util.List;

import com.huidao.common.entity.Goods;
import com.huidao.common.msg.MsgDto;

public interface IGoodsService {
	/**
	 * 分类id 获取商品
	 * @param classId
	 * @return
	 */
	public MsgDto<List<Goods>> getGoods(String classId);
}
