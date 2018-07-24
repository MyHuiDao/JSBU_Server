package com.huidao.admin.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huidao.common.anno.JSON;
import com.huidao.common.anno.Permission;
import com.huidao.common.entity.Goods;
import com.huidao.common.interfaces.admin.IGoodsService;
import com.huidao.common.msg.MsgDto;
import com.yehebl.orm.data.common.dto.Page;
import com.yehebl.orm.data.common.dto.PageUtil;

/**
 * 商品控制层
 * 
 * @author lenovo
 *
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {
	@Reference
	private IGoodsService goodsService;

	/**
	 * 商品分页
	 * 
	 * @param name
	 * @param shopClassId
	 * @return
	 */
	@RequestMapping(value = "/findPageGoodsAllData", method = RequestMethod.GET)
	@Permission("goods_view")
	@JSON
	public MsgDto<Page<Goods>> findPageGoodsAllData(String name, String shopClassId) {

		return goodsService.findPageGoodsAllData(PageUtil.getPage().getPage(), PageUtil.getPage().getSize(), name,
				shopClassId);
	}

	/**
	 * 添加商品信息
	 * 
	 * @param name
	 * @param shopClassId
	 * @return
	 */
	@RequestMapping(value = "/addGoods", method = RequestMethod.POST)
	@Permission("goods_add")
	@JSON
	public MsgDto<String> addGoods(Goods goods) {

		return goodsService.add(goods);
	}

	/**
	 * 修改商品信息
	 * 
	 * @param name
	 * @param shopClassId
	 * @return
	 */
	@RequestMapping(value = "/updateGoods", method = RequestMethod.POST)
	@Permission("goods_update")
	@JSON
	public MsgDto<String> updateGoods(Goods goods) {

		return goodsService.update(goods);
	}

	/**
	 * 删除商品信息
	 * 
	 * @param name
	 * @param shopClassId
	 * @return
	 */
	@RequestMapping(value = "/deleteGoods", method = RequestMethod.GET)
	@Permission("goods_delete")
	@JSON
	public MsgDto<String> deleteGoods(String id) {

		return goodsService.delete(id);
	}

	/**
	 * 获取商品信息
	 * 
	 * @param name
	 * @param shopClassId
	 * @return
	 */
	@RequestMapping(value = "/getGoods", method = RequestMethod.GET)
	@Permission("goods_get")
	@JSON
	public MsgDto<Goods> getGoods(String id) {

		return goodsService.getGoods(id);
	}
}
