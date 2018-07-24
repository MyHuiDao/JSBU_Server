package com.huidao.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.huidao.common.entity.Goods;
import com.huidao.common.entity.Order;
import com.huidao.common.enums.GameType;
import com.huidao.common.enums.GoldSourceType;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.IOrderService;
import com.huidao.common.interfaces.game.IUserService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.DBDao;
import com.yehebl.orm.data.common.dto.Page;
import com.yehebl.orm.data.common.xml.QueryXmlSql;

@Service
@Component
public class OrderService implements IOrderService {

	@Autowired
	private DBDao dbDao;

	@Autowired
	private IUserService userService;

	@Override
	@Transactional
	public MsgDto<String> buyGoods(String userId, String goodsId, Order order) {
		if (StringUtils.isBlank(userId)) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(goodsId)) {
			throw ParamException.param_not_exception;
		}
		if (order == null) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(order.getMobile())) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(order.getName())) {
			throw ParamException.param_not_exception;
		}
		Goods goods = dbDao.get(goodsId, Goods.class);
		if (goods == null) {
			throw ParamException.param_exception;
		}
		String goodsSql = "update goods set stock=stock-? where stock-?>0 and id=?";
		int goodsUpdate = dbDao.update(goodsSql, Goods.class, 1, 1, goods.getId());
		if (goodsUpdate == 0) {
			return MsgFactory.failMsg("库存不足");
		}
		order.setStatus(0);
		order.setGoodsId(goodsId);
		order.setGameUserId(userId);
		order.setCreateDate(new Date());
		MsgDto<String> msg = userService.reduceGold(userId, goods.getPrice(), GoldSourceType.buy,
				"[名称：" + goods.getName() + "][id:" + goods + "]",GameType.app,null);
		if (MsgFactory.isFail(msg)) {
			return MsgFactory.failMsg("金币不足");
		}
		dbDao.save(order);
		return MsgFactory.success();
	}

	@Override
	@Transactional
	public MsgDto<String> deliveryGoods(String orderId, String number, String companyName) {
		if (StringUtils.isBlank(orderId)) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(number)) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(companyName)) {
			throw ParamException.param_not_exception;
		}

		Order order = dbDao.get(orderId, Order.class);
		if (order == null) {
			throw ParamException.param_exception;
		}
		order.setCompanyName(companyName);
		order.setNumber(number);
		order.setStatus(1);
		order.setExecDate(new Date());
		dbDao.update(order);
		return MsgFactory.success();
	}

	@Override
	@Transactional
	public MsgDto<String> changeStatus(String orderId, Integer status) {
		if (StringUtils.isBlank(orderId)) {
			throw ParamException.param_not_exception;
		}
		if (status == null) {
			throw ParamException.param_not_exception;
		}
		Order order = dbDao.get(orderId, Order.class);
		if (order == null) {
			throw ParamException.param_exception;
		}
		order.setExecDate(new Date());
		dbDao.update(order);
		return MsgFactory.success();
	}

	@Override
	public MsgDto<Page<Order>> findPage(Integer page, Integer size, Integer status, Integer code) {
		if (page == null) {
			throw ParamException.param_not_exception;
		}
		if (size == null) {
			throw ParamException.param_not_exception;
		}
		return MsgFactory.success(dbDao.findPageBySqlName("findPageOrder",
				QueryXmlSql.createQuery().addParams("code", code).addParams("status", status), page, size,
				Order.class));
	}

	@Override
	public MsgDto<Page<Order>> findPage(Integer page, Integer size, Integer status, String userId) {
		if (page == null) {
			throw ParamException.param_not_exception;
		}
		if (size == null) {
			throw ParamException.param_not_exception;
		}
		if(StringUtils.isBlank(userId)) {
			throw ParamException.param_not_exception;
		}
		return MsgFactory.success(dbDao.findPageBySqlName("findPageOrder",
				QueryXmlSql.createQuery().addParams("userId", userId).addParams("status", status), page, size,
				Order.class));
	}

}
