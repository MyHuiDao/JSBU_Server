package com.huidao.common.interfaces;

import java.util.List;
import java.util.Map;

import com.huidao.common.entity.ShopClass;
import com.huidao.common.msg.MsgDto;
import com.yehebl.orm.data.common.dto.Page;

public interface IShopClassService {
	/**
	 * 获取商城分类
	 * @return
	 */
	public MsgDto<List<ShopClass>> getShopClass();

	/**
	 * 分页查询
	 * @param page
	 * @param size
	 * @param name
	 * @return
	 */
	public MsgDto<Page<ShopClass>> findPage(Integer page, Integer size, Map<String, Object> parms);
	
	/**
	 * 添加名称（添加操作）
	 * @param shopClass
	 */
	public MsgDto<String> add(ShopClass shopClass);
	
	/**
	 * 检查商品名称是否存在（添加时校验）
	 * @param id
	 * @param name
	 * @return
	 */
	public MsgDto<String> checkName(String id, String name);
	
	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	public MsgDto<ShopClass> get(String id);
	
	/**
	 * 商品删除（删除操作）
	 * @param id
	 * @return
	 */
	public MsgDto<String> delete(String id);
	
	/**
	 * 商品修改（修改操作）
	 * @param shopClass
	 * @return
	 */
	public MsgDto<String> update(ShopClass shopClass);
}
