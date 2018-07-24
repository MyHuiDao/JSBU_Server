package com.huidao.service.admin;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.huidao.common.entity.Goods;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.admin.IGoodsService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.DBDao;
import com.yehebl.orm.data.common.dto.Page;
import com.yehebl.orm.data.common.xml.QueryXmlSql;

@Component
@Service
public class SysGoodsService implements IGoodsService {
	@Autowired
	private DBDao dbDao;

	/**
	 * 分页显示商品信息
	 */
	@Override
	public MsgDto<Page<Goods>> findPageGoodsAllData(Integer page, Integer size, String name, String shopClassId) {
		// 1.创建map集合用于有条件查询时使用
		QueryXmlSql queryXmlSql = new QueryXmlSql();
		if (StringUtils.isNotBlank(name)) {
			queryXmlSql.addParams("name", "%"+name+"%");
		}
		if (StringUtils.isNotBlank(shopClassId)) {
			queryXmlSql.addParams("shopClassId", shopClassId);
		}
		return MsgFactory
				.success(dbDao.findPageBySqlName("findPageGoodsAllData", queryXmlSql, page, size, Goods.class));
	}

	/**
	 * 添加商品
	 */
	@Override
	@Transactional
	public MsgDto<String> add(Goods goods) {
		if (goods == null) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(goods.getName())) {
			throw ParamException.param_not_exception;
		}
		// if (StringUtils.isBlank(goods.getImg())) {
		// throw ParamException.param_not_exception;
		// }
		//
		// if (StringUtils.isBlank(goods.getShopClassId())) {
		// throw ParamException.param_not_exception;
		// }
		if (goods.getPrice() == null) {
			throw ParamException.param_not_exception;
		}
		if (goods.getStock() == null) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(goods.getDesc())) {
			throw ParamException.param_not_exception;
		}
		if (goods.getSeq() == null) {
			throw ParamException.param_not_exception;
		}
		dbDao.save(goods);
		return MsgFactory.success();
	}

	/**
	 * 修改商品信息
	 */
	@Override
	@Transactional
	public MsgDto<String> update(Goods goods) {
		if (goods == null) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(goods.getName())) {
			throw ParamException.param_not_exception;
		}
		// if (StringUtils.isBlank(goods.getImg())) {
		// throw ParamException.param_not_exception;
		// }
		//
		// if (StringUtils.isBlank(goods.getShopClassId())) {
		// throw ParamException.param_not_exception;
		// }
		if (goods.getPrice() == null) {
			throw ParamException.param_not_exception;
		}
		if (goods.getStock() == null) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(goods.getDesc())) {
			throw ParamException.param_not_exception;
		}
		if (goods.getSeq() == null) {
			throw ParamException.param_not_exception;
		}
		dbDao.update(goods);
		return MsgFactory.success();
	}

	/**
	 * 删除商品信息
	 */
	@Override
	@Transactional
	public MsgDto<String> delete(String id) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		dbDao.deleteById(id, Goods.class);
		return MsgFactory.success();
	}

	/**
	 * 获取商品信息
	 */
	@Override
	public MsgDto<Goods> getGoods(String id) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		return MsgFactory.success(dbDao.get(id, Goods.class));
	}

}
