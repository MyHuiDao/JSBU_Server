package com.huidao.common.interfaces.game;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.huidao.common.entity.PayLog;
import com.huidao.common.msg.MsgDto;

/**
 * 支付订单记录表
 * 
 * @author Administrator
 *
 */
public interface IPayLogService {

	/**
	 * * 购买商品下订单
	 * 
	 * @param goldId
	 *            金币商品id
	 * @param userId
	 *            用户id
	 * @param ip
	 *            支付ip
	 * @param code
	 *            用户code
	 * @param productcode
	 *            支付代码
	 * @param terminal
	 *            终端代码
	 * @return
	 */
	public MsgDto<Map<String, String>> order(String goldId, String ip, String userId, String code, String productcode,
			String terminal, Integer type);

	/**
	 * 支付回调
	 * 
	 * @param p1_yingyongnum
	 * @param p2_ordernumber
	 * @param p3_money
	 * @param p4_zfstate
	 * @param p5_orderid
	 * @param p6_productcode
	 * @param p7_bank_card_code
	 * @param p8_charset
	 * @param p9_signtype
	 * @param p10_sign
	 * @param p11_pdesc
	 * @return
	 */
	public MsgDto<String> callback(String p1_yingyongnum, String p2_ordernumber, String p3_money, String p4_zfstate,
			String p5_orderid, String p6_productcode, String p7_bank_card_code, String p8_charset, String p9_signtype,
			String p10_sign, String p11_pdesc);

	/**
	 * 支付金币分红
	 * 
	 * @param level
	 * @param gameUserId
	 * @param rmb
	 * @param payId
	 * @param parentGameUserId
	 */
	public void bouns(int level, String gameUserId, BigDecimal rmb, String payId, String parentGameUserId);

	/**
	 * 苹果回调支付
	 * 
	 * @param voucher
	 * @param type
	 *            0：沙盒，正式
	 * @return
	 */
	public MsgDto<Integer> applePaycallback(String userId, String orderId, String voucher, Integer type);

	/**
	 * 苹果下单
	 * 
	 * @param goldId
	 * @param ip
	 * @param string
	 * @return
	 */
	public MsgDto<String> applePayOrder(String goldId, String userId);

	
	

	/**
	 * 获取用户充值的所有金币
	 * @param id
	 * @return
	 */
	public List<PayLog> getPayGold(String id);
}
