package com.huidao.admin.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huidao.common.anno.JSON;
import com.huidao.common.anno.Permission;
import com.huidao.common.entity.ShopClass;
import com.huidao.common.interfaces.IShopClassService;
import com.huidao.common.msg.MsgDto;
import com.yehebl.orm.data.common.dto.Page;
import com.yehebl.orm.data.common.dto.PageUtil;

@Controller
@RequestMapping("/shop")
public class ShopClassController {

	@Reference
	private IShopClassService shopClassService;

	/**
	 * 获取商城分类
	 */
	@RequestMapping(value = "/getExChangeGoldList", method = RequestMethod.GET)
	@JSON
	@Permission
	public MsgDto<List<ShopClass>> getShopClass() {
		return shopClassService.getShopClass();
	}

	/**
	 * 分页查询名称
	 * @return
	 */
	@RequestMapping(value = "/getShopClassName", method = RequestMethod.GET)
	@Permission("shop_class_view")
	@JSON
	public MsgDto<Page<ShopClass>> getShopClassName(String name) {
		Map<String, Object> map = new HashMap<>();
		if (StringUtils.isNotBlank(name)) {
			map.put("LIKE_name", name);
		}
		return shopClassService.findPage(PageUtil.getPage().getPage(), PageUtil.getPage().getSize(), map);
	}
	
	/**
	 * 检查商品名称是否存在
	 * @param id
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/getShopNameById", method = RequestMethod.GET)
	@Permission
	@JSON
	public MsgDto<ShopClass> getShopNameById(String id) {
		return shopClassService.get(id);
	}

	/**
	 * 检查商品名称是否存在
	 * @param id
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/checkName", method = RequestMethod.GET)
	@Permission
	@JSON
	public MsgDto<String> checkName(String id, String name) {
		return shopClassService.checkName(id, name);
	}

	/**
	 * 添加商品名称	
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/addShopByName", method = RequestMethod.GET)
	@Permission("role_new")
	@JSON
	public MsgDto<String> addShopByName(ShopClass shopClass) {
		return shopClassService.add(shopClass);
	}
	
	/**
	 * 修改商品名称
	 * @param shopClass
	 * @return
	 */
	@RequestMapping(value = "/updShopByName", method = RequestMethod.POST)
	@Permission("shop_class_update")
	@JSON
	public MsgDto<String> updShopByName(ShopClass shopClass){
		return shopClassService.update(shopClass);
	}
	
	/**
	 * 删除商品名称
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/dalShopByName", method = RequestMethod.GET)
	@Permission("shop_class_delete")
	@JSON
	public MsgDto<String> dalShopByName(String id) {
		return shopClassService.delete(id);
	}
	
}
