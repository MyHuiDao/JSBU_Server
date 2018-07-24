package com.huidao.common.interfaces.admin;

import com.huidao.common.entity.Goods;
import com.huidao.common.msg.MsgDto;
import com.yehebl.orm.data.common.dto.Page;

/**
 * 商城商品接口
 * 
 * @author lenovo
 *
 */
public interface IGoodsService {
	/**
	 * 
	 * @param page
	 *            页码
	 * @param size
	 *            记录数
	 * @param name
	 *            商品名称
	 * @param shopClassId
	 *            分类ID
	 * @return
	 */
	public MsgDto<Page<Goods>> findPageGoodsAllData(Integer page, Integer size, String name, String shopClassId);

	/**
	 * 添加商品
	 * 
	 * @param goods
	 * @return
	 */
	public MsgDto<String> add(Goods goods);

	/**
	 * 修改商品
	 * 
	 * @param goods
	 * @return
	 */
	public MsgDto<String> update(Goods goods);

	/**
	 * 删除商品
	 * 
	 * @param id
	 * @return
	 */
	public MsgDto<String> delete(String id);

	/**
	 * 获取商品信息
	 * 
	 * @param id
	 * @return
	 */
	public MsgDto<Goods> getGoods(String id);

}
