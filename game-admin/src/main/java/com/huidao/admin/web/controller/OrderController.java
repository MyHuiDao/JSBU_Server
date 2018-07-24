package com.huidao.admin.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huidao.common.anno.JSON;
import com.huidao.common.anno.Permission;
import com.huidao.common.entity.Order;
import com.huidao.common.interfaces.IOrderService;
import com.huidao.common.msg.MsgDto;
import com.yehebl.orm.data.common.dto.Page;
import com.yehebl.orm.data.common.dto.PageUtil;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Reference
	private IOrderService orderService;

	/**
	 * 分页显示订单信息
	 * 
	 * @param status
	 * @param code
	 * @return
	 */
	@RequestMapping("/findOrderPage")
	@Permission("order_get")
	@JSON
	public MsgDto<Page<Order>> findOrderPage(Integer status, Integer code) {

		return orderService.findPage(PageUtil.getPage().getPage(), PageUtil.getPage().getSize(), status, code);
	}

	/**
	 * 发送快递
	 * 
	 * @param orderId
	 *            订单id
	 * @param number
	 *            快递单号
	 * @return
	 */
	@RequestMapping("/deliveryGoods")
	@Permission("order_update")
	@JSON
	public MsgDto<String> deliveryGoods(String orderId, String number, String companyName) {
		return orderService.deliveryGoods(orderId, number, companyName);
	}

	/**
	 * 修改订单状态
	 * 
	 * @param orderId
	 * @param status
	 * @return
	 */
	@RequestMapping("/changeStatus")
	@Permission
	@JSON
	public MsgDto<String> changeStatus(String orderId, Integer status) {
		return orderService.changeStatus(orderId, status);
	}
}
