package com.huidao.game.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huidao.common.anno.JSON;
import com.huidao.common.anno.Permission;
import com.huidao.common.entity.Goods;
import com.huidao.common.entity.Order;
import com.huidao.common.entity.ShopClass;
import com.huidao.common.entity.User;
import com.huidao.common.interfaces.IOrderService;
import com.huidao.common.interfaces.IShopClassService;
import com.huidao.common.interfaces.game.IGoodsService;
import com.huidao.common.interfaces.game.IUserService;
import com.huidao.common.msg.MsgDto;
import com.huidao.game.manager.UserManager;
import com.yehebl.orm.data.common.dto.Page;

@Controller
@RequestMapping("/shop")
public class ShopController {

	@Reference
	private IShopClassService shopClassService;

	@Reference
	private IGoodsService goodsService;
	
	@Reference
	private IOrderService orderService;
	
	
	@Reference
	private IUserService userService;

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
	 * 获取商品
	 */
	@RequestMapping(value = "/getGoods", method = RequestMethod.GET)
	@JSON
	@Permission
	public MsgDto<List<Goods>> getGoods(String classId) {
		return goodsService.getGoods(classId);
	}
	
	/**
	 * 购买商品
	 * @return
	 */
	@RequestMapping(value = "/buyGoods", method = RequestMethod.GET)
	@JSON
	@Permission
	public MsgDto<String> buyGoods(String goodsId,Order order){
		User user = userService.getUserByToken(UserManager.getCurrentToken());
		return orderService.buyGoods(user.getId(), goodsId, order);
	}
	
	/**
	 * 获取订单
	 * @return
	 */
	@RequestMapping(value = "/getOrders", method = RequestMethod.GET)
	@JSON(clazz= {Order.class},property= {"createDate,goodsName,mobile,name,status"})
	@Permission
	public MsgDto<Page<Order>> getOrders(Integer page,Integer size,Integer status){
		User user = userService.getUserByToken(UserManager.getCurrentToken());
		return orderService.findPage(page,size, status,user.getId());
	}

}
