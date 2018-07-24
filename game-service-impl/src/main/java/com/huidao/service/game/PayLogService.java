package com.huidao.service.game;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huidao.common.contants.GameSettingContants;
import com.huidao.common.entity.AgenLevel;
import com.huidao.common.entity.AgentBonusLog;
import com.huidao.common.entity.GameGoldExchange;
import com.huidao.common.entity.PayLog;
import com.huidao.common.entity.SysUser;
import com.huidao.common.entity.User;
import com.huidao.common.entity.UserPayGoldBonusLog;
import com.huidao.common.enums.GameType;
import com.huidao.common.enums.GameUserOnceType;
import com.huidao.common.enums.GoldSourceType;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.admin.IAgenLevelService;
import com.huidao.common.interfaces.game.IGameSettingService;
import com.huidao.common.interfaces.game.IGameUserOnceService;
import com.huidao.common.interfaces.game.IPayLogService;
import com.huidao.common.interfaces.game.ISendNoticeService;
import com.huidao.common.interfaces.game.IUserService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.huidao.common.util.HttpUtil;
import com.huidao.config.SystemConfig;
import com.huidao.util.MD5Tool;
import com.huidao.util.UtilDate;
import com.yehebl.orm.DBDao;

@Component
@Service(timeout = 5000)
public class PayLogService implements IPayLogService {

	private static final Logger log = LoggerFactory.getLogger(PayLogService.class);

	@Autowired
	private DBDao dbDao;

	@Autowired
	private IUserService userService;

	@Autowired
	private SystemConfig systemConfig;

	@Autowired
	private IGameSettingService gameSettingService;

	@Reference
	private ISendNoticeService sendNoticeService;

	@Autowired
	private IGameUserOnceService gameUserOnceService;

	@Autowired
	private IAgenLevelService agenbLevelService;

	@Override
	@Transactional
	public MsgDto<Map<String, String>> order(String goldId, String ip, String userId, String code, String productcode,
			String terminal, Integer type) {
		if (StringUtils.isBlank(goldId)) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(userId)) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(productcode)) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(terminal)) {
			throw ParamException.param_not_exception;
		}
		if (type == null) {
			type = 0;
		}
		GameGoldExchange gg = dbDao.get(goldId, GameGoldExchange.class);
		if (gg == null) {
			throw ParamException.param_exception;
		}
		if (gameUserOnceService.isGameUserOnce(userId, GameUserOnceType.once_pay).getData()) {
			Double oncePay = Double.valueOf(gameSettingService.getValue(GameSettingContants.game_onec_pay));
			if (oncePay > gg.getRmb().doubleValue()) {
				return MsgFactory.failMsg("首冲不能低于" + oncePay + "元");
			}
		} else {
			Double minPay = Double.valueOf(gameSettingService.getValue(GameSettingContants.game_min_pay));
			if (minPay > gg.getRmb().doubleValue()) {
				return MsgFactory.failMsg("充值不能低于" + minPay + "元");
			}
		}
		PayLog pl = new PayLog();
		pl.setCreateDate(new Date());
		pl.setFreeGold(gg.getFreeGold());
		pl.setGold(gg.getGold());
		pl.setGoldId(goldId);
		pl.setUserId(userId);
		pl.setStatus(0);
		pl.setType(type);
		pl.setPrice(gg.getRmb());
		dbDao.save(pl);
		DecimalFormat df = new DecimalFormat("#.00");
		String p3_money = df.format(gg.getRmb());
		String p14_customname = code;
		String p25_terminal = terminal;
		String p7_productcode = productcode;
		String p10_bank_card_code = "";
		String p16_customip = ip;
		// 商户应用号
		String p1_yingyongnum = systemConfig.getJunfutongYinyongNum();
		// 商户订单号
		String p2_ordernumber = pl.getId();

		// 商户订单时间
		String p6_ordertime = UtilDate.getdtlongNum();
		// 订单签名
		System.out.println("签名之前>>" + p1_yingyongnum + "&" + p2_ordernumber + "&" + p3_money + "&" + p6_ordertime + "&"
				+ p7_productcode + "&" + systemConfig.getJunfutongPayKey() + "<<");
		String p8_sign = MD5Tool.encoding(p1_yingyongnum + "&" + p2_ordernumber + "&" + p3_money + "&" + p6_ordertime
				+ "&" + p7_productcode + "&" + systemConfig.getJunfutongPayKey());
		System.out.println("签名结果>>" + p8_sign + "<<");
		// 签名方式
		String p9_signtype = "1";
		// 编码格式
		String p23_charset = "UTF-8";

		Map<String, String> result = new HashMap<>();
		result.put("p1_yingyongnum", p1_yingyongnum);
		result.put("p2_ordernumber", p2_ordernumber);
		result.put("p6_ordertime", p6_ordertime);
		result.put("p3_money", p3_money);
		result.put("p7_productcode", p7_productcode);
		result.put("p8_sign", p8_sign);
		result.put("p9_signtype", p9_signtype);
		result.put("p10_bank_card_code", p10_bank_card_code);
		result.put("p14_customname", p14_customname);
		result.put("p16_customip", p16_customip);
		result.put("p23_charset", p23_charset);
		result.put("p25_terminal", p25_terminal);

		System.out.println("发送的参数：" + JSONObject.toJSONString(result));
		return MsgFactory.success(result);
	}

	@Override
	@Transactional
	public MsgDto<String> callback(String p1_yingyongnum, String p2_ordernumber, String p3_money, String p4_zfstate,
			String p5_orderid, String p6_productcode, String p7_bank_card_code, String p8_charset, String p9_signtype,
			String p10_sign, String p11_pdesc) {

		if (StringUtils.isBlank(p2_ordernumber)) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(p11_pdesc)) {
			p11_pdesc = "";
		}
		String str = MD5Tool.encoding(p1_yingyongnum + "&" + p2_ordernumber + "&" + p3_money + "&" + p4_zfstate + "&"
				+ p5_orderid + "&" + p6_productcode + "&" + p7_bank_card_code + "&" + p8_charset + "&" + p9_signtype
				+ "&" + p11_pdesc + "&" + systemConfig.getJunfutongPayKey());
		if (!str.equals(p10_sign)) {
			log.error("验签失败{}", str);
			throw new RuntimeException("验签失败 ");
		}

		PayLog payLog = dbDao.get(p2_ordernumber, PayLog.class);
		if (payLog == null) {
			log.error("没有该订单{}", p2_ordernumber);
			throw new RuntimeException("没有该订单 ");
		}
		DecimalFormat df = new DecimalFormat("#.00");
		String price = df.format(payLog.getPrice());
		String money = df.format(BigDecimal.valueOf(Double.valueOf(p3_money)));
		if (!price.equals(money)) {
			log.error("支付金额不等于实际金额 【支付金额：{}-实际金额{}】", price, money);
			throw new RuntimeException("支付金额不等于实际金额 ");
		}

		if (!"1".equals(p4_zfstate)) {
			log.error("支付未成功：{}", str);
			throw new RuntimeException("支付未成功");
		}
		if (payLog.getStatus() != 0) {
			log.error("订单状态不正确{}", payLog.getStatus());
			throw new RuntimeException("订单状态不正确");
		}
		int result = dbDao.update("update pay_log set status=1 where status=0 and id=?", PayLog.class, payLog.getId());
		if (result == 0) {
			log.error("支付失败");
			throw new RuntimeException("支付失败");
		}
		// 分红处理
		User u = dbDao.get(payLog.getUserId(), User.class);

		bouns(1, u.getRecommendUserId(), BigDecimal.valueOf(Double.valueOf(money)), payLog.getId(), null);

		MsgDto<String> msg = null;
		if (payLog.getType() == 1) {
			msg = userService.addGold(payLog.getUserId(), payLog.getGold(), GoldSourceType.free, GameType.app, null);
		} else {
			msg = userService.addGold(payLog.getUserId(), payLog.getGold(), GoldSourceType.pay, GameType.app, null);
		}
		if (MsgFactory.isFail(msg)) {
			log.error("添加金币出错{}", payLog.getStatus());
			throw new RuntimeException("添加金币出错");
		}
		if (payLog.getFreeGold() != null && payLog.getFreeGold() > 0) {
			MsgDto<String> msg1 = userService.addGold(payLog.getUserId(), payLog.getFreeGold(), GoldSourceType.free,
					GameType.app, null);
			if (MsgFactory.isFail(msg1)) {
				log.error("添加赠送金币出错{}", payLog.getStatus());
				throw new RuntimeException("添加金币出错");
			}
		}
		payLog.setStatus(1);
		payLog.setPayDate(new Date());
		dbDao.update(payLog);
		if (payLog.getType() == 1) {
			SysUser sysUser = dbDao.getByExpression("EQ_gameUserId", u.getId(), SysUser.class);
			if (sysUser != null) {
				AgenLevel agenLevel = agenbLevelService.get(sysUser.getAgenLevelId());
				if (agenLevel != null) {
					AgenLevel nextAgentLevel = getAgenLevelNextByMoeny(agenLevel, payLog.getPrice().doubleValue());
					if (nextAgentLevel != null) {
						sysUser.setAgenLevelId(nextAgentLevel.getId());
						dbDao.update(sysUser);
					}
				}
			}
		}
		if (gameUserOnceService.isGameUserOnce(payLog.getUserId(), GameUserOnceType.once_pay).getData()) {
			gameUserOnceService.addGameUserOnce(payLog.getUserId(), GameUserOnceType.once_pay);
		}
		User u1 = dbDao.get(payLog.getUserId(), User.class);
		u1.setIsVip("1");
		u1.setVipLevel(1);
		dbDao.update(u1);
		if (payLog.getType() != 1) {
			sendNoticeService.noticeRechargeInfo(payLog.getUserId(), payLog.getGold());
		}
		return MsgFactory.success("success");

	}

	@Transactional
	@Override
	public void bouns(int level, String gameUserId, BigDecimal rmb, String payId, String parentGameUserId) {
		if (StringUtils.isBlank(gameUserId)) {
			return;
		}
		SysUser sysUser = dbDao.getByExpression("EQ_gameUserId", gameUserId, SysUser.class);
		if (sysUser == null) {
			return;
		}
		if (StringUtils.isBlank(sysUser.getAgenLevelId())) {
			return;
		}
		AgenLevel al = dbDao.get(sysUser.getAgenLevelId(), AgenLevel.class);
		if (al == null) {
			return;
		}
		Integer proportion = null;
		if (level == 1) {
			proportion = al.getProportion1();
		} else if (level == 2) {
			proportion = al.getProportion2();
		} else if (level == 3) {
			proportion = al.getProportion3();
		} else {
			return;
		}
		DecimalFormat formater = new DecimalFormat("#.00");
		formater.setRoundingMode(RoundingMode.FLOOR);
		Double format = Double
				.valueOf(formater.format(rmb.multiply(BigDecimal.valueOf(proportion)).divide(BigDecimal.valueOf(100))));
		if (level == 1) {
			UserPayGoldBonusLog ypgb = new UserPayGoldBonusLog();
			ypgb.setSysUserId(sysUser.getId());
			ypgb.setUserId(gameUserId);
			ypgb.setCreateDate(new Date());
			Integer rate = Integer.valueOf(gameSettingService.getValue(GameSettingContants.gold_exchange_rate));
			ypgb.setGold(BigDecimal.valueOf(format * rate).intValue());
			dbDao.save(ypgb);
		}

		AgentBonusLog abl = new AgentBonusLog();
		abl.setBonusMoney(BigDecimal.valueOf(format));
		abl.setBonusProportion(proportion);
		abl.setPayId(payId);
		abl.setUserId(gameUserId);
		abl.setParentBonusId(parentGameUserId);
		abl.setCreateDate(new Date());
		dbDao.save(abl);
		String sql = "update sys_user set money=money+? where id=?";
		int update = dbDao.update(sql, SysUser.class, format, sysUser.getId());
		if (update == 0) {
			throw new RuntimeException("分红失败," + JSONObject.toJSONString(abl));
		}
		User parentUser = dbDao.get(gameUserId, User.class);
		if (parentUser == null) {
			return;
		}
		bouns(level + 1, parentUser.getRecommendUserId(), rmb, payId, gameUserId);
	}

	@Override
	@Transactional
	public MsgDto<Integer> applePaycallback(String userId, String orderId, String voucher, Integer type) {
		if (StringUtils.isBlank(userId)) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(orderId)) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(voucher)) {
			throw ParamException.param_not_exception;
		}
		String transactionId = null;
		String productId = null;
		try {
			String body = HttpUtil.postByte("https://buy.itunes.apple.com/verifyReceipt", voucher).getBody();
			log.info("苹果内购返回数据：" + body);
			JSONObject json = (JSONObject) JSONObject.parse(body);
			JSONObject receiptJson = json.getJSONObject("receipt");
			JSONArray jsonArray = receiptJson.getJSONArray("in_app");
			transactionId = jsonArray.getJSONObject(0).getString("transaction_id");
			productId = jsonArray.getJSONObject(0).getString("product_id");
			if (StringUtils.isBlank(transactionId)) {
				throw ParamException.param_not_exception;
			}
		} catch (Exception e) {
			String body = HttpUtil.postByte("https://sandbox.itunes.apple.com/verifyReceipt", voucher).getBody();
			log.info("苹果内购返回数据：" + body);
			JSONObject json = (JSONObject) JSONObject.parse(body);
			JSONObject receiptJson = json.getJSONObject("receipt");
			JSONArray jsonArray = receiptJson.getJSONArray("in_app");
			transactionId = jsonArray.getJSONObject(0).getString("transaction_id");
			productId = jsonArray.getJSONObject(0).getString("product_id");
			if (StringUtils.isBlank(transactionId)) {
				throw ParamException.param_not_exception;
			}
		}
		PayLog payLog1 = dbDao.getByExpression("EQ_appleOrderId", transactionId, PayLog.class);
		if (payLog1 != null) {
			throw ParamException.param_exception;
		}
		PayLog payLog = dbDao.get(orderId, PayLog.class);
		if (payLog == null) {
			log.error("没有该订单{}", orderId);
			throw new RuntimeException("没有该订单 ");
		}
		if (!userId.equals(payLog.getUserId())) {
			log.error("订单用户和当前登录用户不一致", payLog.getUserId() + ":当前用户" + userId);
			throw new RuntimeException("订单状态不正确");
		}
		if (payLog.getStatus() != 0) {
			log.error("订单状态不正确{}", payLog.getStatus());
			throw new RuntimeException("订单状态不正确");
		}
		GameGoldExchange gameGoldExchange = dbDao.get(payLog.getGoldId(), GameGoldExchange.class);
		if (gameGoldExchange == null) {
			log.error("没找到对应的产品：{}", payLog.getGold());
			throw new RuntimeException("没找到对应的产品");
		}
		if (!("jinshayugang_" + gameGoldExchange.getRmb().toBigInteger().toString()).equals(productId)) {
			log.error("苹果返回的产品id:{}与本地存储的金额不相同:{}", productId, gameGoldExchange.getRmb().toBigInteger());
			throw new RuntimeException("苹果返回的产品id与本地存储的金额不相同");
		}

		int result = dbDao.update("update pay_log set status=1 where status=0 and id=?", PayLog.class, payLog.getId());
		if (result == 0) {
			log.error("支付失败");
			throw new RuntimeException("支付失败");
		}
		// 分红处理
		User u = dbDao.get(payLog.getUserId(), User.class);

		bouns(1, u.getRecommendUserId(), payLog.getPrice(), payLog.getId(), null);

		MsgDto<String> msg = userService.addGold(payLog.getUserId(), payLog.getGold(), GoldSourceType.pay, GameType.app,
				null);
		if (MsgFactory.isFail(msg)) {
			log.error("添加金币出错{}", payLog.getStatus());
			throw new RuntimeException("添加金币出错");
		}
		if (payLog.getFreeGold() != null && payLog.getFreeGold() > 0) {
			MsgDto<String> msg1 = null;
			if (payLog.getType() == 1) {
				msg1 = userService.addGold(payLog.getUserId(), payLog.getGold(), GoldSourceType.free, GameType.app,
						null);
			} else {
				msg1 = userService.addGold(payLog.getUserId(), payLog.getGold(), GoldSourceType.pay, GameType.app,
						null);
			}
			if (MsgFactory.isFail(msg1)) {
				log.error("添加赠送金币出错{}", payLog.getStatus());
				throw new RuntimeException("添加金币出错");
			}
		}
		payLog.setStatus(1);
		payLog.setType(0);
		payLog.setPayDate(new Date());
		payLog.setAppleOrderId(transactionId);
		dbDao.update(payLog);
		int resultCount = dbDao.getCountByExpression("EQ_appleOrderId", transactionId, PayLog.class);
		if (resultCount > 1) {
			log.error("当前凭证被使用条数：" + result);
			throw ParamException.param_exception;
		}
		if (payLog.getType() == 1) {
			SysUser sysUser = dbDao.getByExpression("EQ_gameUserId", u.getId(), SysUser.class);
			if (sysUser != null) {
				AgenLevel agenLevel = agenbLevelService.get(sysUser.getAgenLevelId());
				if (agenLevel != null) {
					AgenLevel nextAgentLevel = getAgenLevelNextByMoeny(agenLevel, payLog.getPrice().doubleValue());
					if (nextAgentLevel != null) {
						sysUser.setAgenLevelId(nextAgentLevel.getId());
						dbDao.update(sysUser);
					}
				}
			}
		}
		if (gameUserOnceService.isGameUserOnce(payLog.getUserId(), GameUserOnceType.once_pay).getData()) {
			gameUserOnceService.addGameUserOnce(payLog.getUserId(), GameUserOnceType.once_pay);
		}
		User u1 = dbDao.get(payLog.getUserId(), User.class);
		u1.setIsVip("1");
		u1.setVipLevel(1);
		dbDao.update(u1);
		return MsgFactory.success((payLog.getGold() + payLog.getFreeGold()));
	}

	private AgenLevel getAgenLevelNextByMoeny(AgenLevel currentAgenLevel, Double money) {
		AgenLevel nextAgentLevel = agenbLevelService.getNextLevel(currentAgenLevel.getLevel());
		if (nextAgentLevel != null) {
			if (nextAgentLevel.getPrice().doubleValue() <= money) {
				return getAgenLevelNextByMoeny(nextAgentLevel, money);
			} else {
				return currentAgenLevel;
			}
		}
		return currentAgenLevel;
	}

	@Transactional
	@Override
	public MsgDto<String> applePayOrder(String goldId, String userId) {
		if (StringUtils.isBlank(goldId)) {
			throw ParamException.param_not_exception;
		}
		GameGoldExchange gg = dbDao.get(goldId, GameGoldExchange.class);
		if (gg == null) {
			throw ParamException.param_exception;
		}
		if (gameUserOnceService.isGameUserOnce(userId, GameUserOnceType.once_pay).getData()) {
			Double oncePay = Double.valueOf(gameSettingService.getValue(GameSettingContants.game_onec_pay));
			if (oncePay > gg.getRmb().doubleValue()) {
				return MsgFactory.failMsg("首冲不能低于" + oncePay + "元");
			}
		} else {
			Double minPay = Double.valueOf(gameSettingService.getValue(GameSettingContants.game_min_pay));
			if (minPay > gg.getRmb().doubleValue()) {
				return MsgFactory.failMsg("充值不能低于" + minPay + "元");
			}
		}
		PayLog pl = new PayLog();
		pl.setCreateDate(new Date());
		pl.setFreeGold(gg.getFreeGold());
		pl.setGold(gg.getGold());
		pl.setUserId(userId);
		pl.setGoldId(goldId);
		pl.setStatus(0);
		pl.setPrice(gg.getRmb());
		pl.setType(0);
		dbDao.save(pl);
		return MsgFactory.success(pl.getId());
	}

	@Override
	public List<PayLog> getPayGold(String id) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		return dbDao.createExpressionMap().eq("userId", id).gt("gold", 0).order("createDate").eq("status", "1")
				.find(PayLog.class);
	}
}
