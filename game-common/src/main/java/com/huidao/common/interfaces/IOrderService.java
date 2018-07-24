package com.huidao.common.interfaces;

import com.huidao.common.entity.Order;
import com.huidao.common.msg.MsgDto;
import com.yehebl.orm.data.common.dto.Page;

/**
 * 订单服务
 * @author Administrator
 *
 */
public interface IOrderService {
	
	/**
	 * 购买商品 生成订单
	 * @param userId
	 * @param goodsId
	 * @param Order 订单表
	 * @return
	 */
	public MsgDto<String> buyGoods(String userId,String goodsId,Order order);
	
	
	/**
	 * 发送快递
	 * @param orderId 订单id
	 * @param number 快递单号
	 * @return
	 */
	public MsgDto<String> deliveryGoods(String orderId,String number,String companyName);
	
	
	
	/**
	 * 修改状态
	 * @param orderId
	 * @param status
	 * @return
	 */
	public MsgDto<String> changeStatus(String orderId,Integer status);
	
	
	/**
	 * 分页查询订单
	 * @param page
	 * @param size
	 * @param status 状态
	 * @param code 用户code
	 * @return
	 */
	public MsgDto<Page<Order>>  findPage(Integer page,Integer size,Integer status,Integer code);
	
	/**
	 * 分页查询订单
	 * @param page
	 * @param size
	 * @param status 状态(0:未处理,1::已处理)
	 * @param userId 用户id
	 * @return
	 */
	public MsgDto<Page<Order>>  findPage(Integer page,Integer size,Integer status,String userId);

}
