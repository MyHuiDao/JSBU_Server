package com.huidao.service.game;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.huidao.common.entity.ShopClass;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.IShopClassService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.DBDao;
import com.yehebl.orm.data.common.ICopyProperties;
import com.yehebl.orm.data.common.dto.Page;

@Service
@Component
public class ShopClassService implements IShopClassService {

	@Autowired
	private DBDao dbDao;

	/**
	 * 获取商城分类列表
	 * @return
	 */
	public MsgDto<List<ShopClass>> getShopClass() {
		return MsgFactory.success(dbDao.findByExpression("ORDER_seq", "asc", ShopClass.class));
	}

	/**
	 * 分页查询名称
	 * @return
	 */
	public MsgDto<Page<ShopClass>> findPage(Integer page, Integer size, Map<String, Object> parms) {
		if (page == null) {
			throw ParamException.param_not_exception;
		}
		if (size == null) {
			throw ParamException.param_not_exception;
		}
		return MsgFactory.success(dbDao.findPageByMap(page, size, parms, ShopClass.class));
	}

	/**
	 * 检查商品分类名称是否存在
	 * @param id
	 * @param name
	 * @return
	 */
	@Override
	public MsgDto<String> checkName(String id, String name) {
		if (StringUtils.isBlank(name)) {
			throw ParamException.param_not_exception;
		}
		if (dbDao.checkUnique(id, "name", name, ShopClass.class)) {
			return MsgFactory.failMsg("商品名称已存在");
		}
		return MsgFactory.success();
	}

    /**
     * 通过id查询（修改时查询）
     */
	@Override
	public MsgDto<ShopClass> get(String id) {
		if(StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		return MsgFactory.success(dbDao.get(id, ShopClass.class));
	}

	/**
	 * 添加名称（添加操作）
	 */
	@Override
	@Transactional
	public MsgDto<String> add(ShopClass shopClass) {
		if (shopClass == null) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(shopClass.getName())) {
			throw ParamException.param_not_exception;
		}
		dbDao.save(shopClass);
		return MsgFactory.success();
	}

	/**
	 * 商品修改（修改操作）
	 */
	@Override
	@Transactional
	public MsgDto<String> delete(String id) {
		if(StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		dbDao.deleteById(id, ShopClass.class);
		return MsgFactory.success();
	}

	/**
	 * 修改商品名称
	 */
	@Override
	@Transactional
	public MsgDto<String> update(ShopClass shopClass) {
		if(shopClass == null) {
			throw ParamException.param_not_exception;
		}
		if(StringUtils.isBlank(shopClass.getName())) {
			throw ParamException.param_not_exception;
		}
		dbDao.update(shopClass,new ICopyProperties<ShopClass>() {
			@Override
			public void copy(ShopClass oldEntity, ShopClass newEntity) {
				newEntity.setName(oldEntity.getName());
				newEntity.setSeq(oldEntity.getSeq());
			}
		},ShopClass.class);
		return null;
	}
}