package com.huidao.service.fish.slot.machine;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.huidao.common.entity.FishMagnificationDeploy;
import com.huidao.common.entity.FishSlotMachine;
import com.huidao.common.entity.FishSlotMachineBet;
import com.huidao.common.entity.FishSlotmachineDeploy;
import com.huidao.common.entity.User;
import com.huidao.common.enums.GameType;
import com.huidao.common.enums.GoldSourceType;
import com.huidao.common.exception.BusinessException;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.fish.slot.machine.IFishSlotMachineBetService;
import com.huidao.common.interfaces.game.IUserService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.DBDao;

@Service
@Component
public class FishSlotMachineBetService implements IFishSlotMachineBetService {

	@Autowired
	private DBDao dbDao;

	@Autowired
	private IUserService userServicer;

	@Override
	@Transactional
	public MsgDto<Map<String, Object>> bet(User user, String bet) {
		if (user == null) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(bet)) {
			throw ParamException.param_not_exception;
		}

		// 1.判断用户是否是首次进入游戏
		List<FishSlotMachineBet> lists = dbDao.createExpressionMap().eq("userId", user.getId())
				.find(FishSlotMachineBet.class);
		// 2.判断用户是否为第三阶段配置表
		// 用于保存查询时间
		Date queryDate = null;
		try {
			List<FishSlotMachineBet> threeStage = dbDao.createExpressionMap().eq("stage", 3).orderDesc("betDate")
					.find(FishSlotMachineBet.class);
			if (threeStage.size() <= 0) {
				queryDate = new Date(1514736000000L);
			} else {
				queryDate = threeStage.get(0).getBetDate();
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		// 3.计算用户收支比

		// 统计用户收入
		BigDecimal income = dbDao.getResultObjectBySql(
				"select sum(ugl.gold_num) from user_gold_log ugl where ugl.user_id=? and ugl.create_date>=? and ugl.game_type='slot_machine' and ugl.source_type=4",
				user.getId(), queryDate).bigDecimalValueNotNull();
		// 统计用户支出
		BigDecimal expenditure = dbDao.getResultObjectBySql(
				"select sum(ugl.gold_num) from user_gold_log ugl where ugl.user_id=? and ugl.create_date>=? and ugl.game_type='slot_machine' and ugl.source_type=5",
				user.getId(), queryDate).bigDecimalValueNotNull();
		BigDecimal incomeRatio = null;
		if (expenditure.intValue() == 0) {
			incomeRatio = BigDecimal.ZERO;
		} else {
			incomeRatio = income.divide(expenditure, 10, RoundingMode.DOWN);
		}
		System.out.println("用户收入======：" + income);
		System.out.println("用户支出======：" + expenditure);
		System.out.println("用户收支比======：" + incomeRatio);
		// 配置表类型
		Integer type = 0;
		if (lists.size() < 0) {
			// 首次配置
			type = 0;
		} else {
			// 走普通配置
			type = 1;
		}
		// 获得阶段配置
		List<FishSlotmachineDeploy> fsds = dbDao.createExpressionMap().neqIsBlank("incomeRatio", null)
				.find(FishSlotmachineDeploy.class);
		for (int i = 0; i < fsds.size(); i++) {
			FishSlotmachineDeploy fsd = fsds.get(i);
			if (fsd.getIncomeRatio() >= incomeRatio.doubleValue()) {
				type = fsd.getType();
			}
		}

		FishSlotmachineDeploy fishSlotmachineDeploy = dbDao.createExpressionMap().eq("type", type)
				.get(FishSlotmachineDeploy.class);
		// 生成开奖结果
		FishSlotMachine fishSlotMachine = dbDao.createExpressionMap().eq("randomValue", getRandomValue())
				.eq("deployId", fishSlotmachineDeploy.getId()).get(FishSlotMachine.class);
		// 用于保存结果
		Map<String, Object> maps = new HashMap<String, Object>();
		// 得到开奖结果
		Integer randomValue = fishSlotMachine.getRandomValue();
		FishSlotMachineBet fishSlotMachineBet = new FishSlotMachineBet();
		if (type == 4) {
			// 设置配置表为第三配置表
			fishSlotMachineBet.setStage(3);
		}
		// 下注用户
		fishSlotMachineBet.setUserId(user.getId());
		// 用户下注
		fishSlotMachineBet.setUserBet(bet);
		// 开奖结果
		fishSlotMachineBet.setLotteryResult(randomValue.toString());
		// 处理中奖结果
		String[] bets = bet.split(",");
		// 统计用户下注金额
		Integer betGold = 0;
		try {
			for (String strs : bets) {
				String[] fishs = strs.split(":");
				betGold += Integer.valueOf(fishs[1]);
			}
		} catch (Exception e) {
			throw new BusinessException("数据转换出错");
		}
		try {
			if (betGold <= 0) {
				throw ParamException.param_not_exception;
			}
			// 扣除用户金币
			userServicer.reduceGold(user.getId(), betGold, GoldSourceType.lose, "", GameType.slot_machine, null);
			maps.put("deductionGold", betGold);
		} catch (Exception exception) {
			throw new BusinessException("金币不足");
		}

		// 判断出的结果是否为特殊鱼
		if (fishSlotMachine.getFishName().equals("白羊驼")) {
			Integer whiteAlpacaRandom = 0;
			for (;;) {
				whiteAlpacaRandom = getRandomValue();
				FishSlotMachine fishSlotMachine2 = dbDao.createExpressionMap().eq("randomValue", whiteAlpacaRandom)
						.eq("deployId", fishSlotmachineDeploy.getId()).get(FishSlotMachine.class);
				if (fishSlotMachine2.getFishName().equals("白羊驼") || fishSlotMachine2.getFishName().equals("粉羊驼")) {
					continue;
				}
				break;
			}
			FishSlotMachine whiteAlpaca1 = dbDao.createExpressionMap().eq("randomValue", whiteAlpacaRandom)
					.eq("deployId", fishSlotmachineDeploy.getId()).get(FishSlotMachine.class);
			for (;;) {
				whiteAlpacaRandom = getRandomValue();
				FishSlotMachine fishSlotMachine2 = dbDao.createExpressionMap().eq("randomValue", whiteAlpacaRandom)
						.eq("deployId", fishSlotmachineDeploy.getId()).get(FishSlotMachine.class);
				if (fishSlotMachine2.getFishName().equals("白羊驼") || fishSlotMachine2.getFishName().equals("粉羊驼")) {
					continue;
				}
				break;
			}
			FishSlotMachine whiteAlpaca2 = dbDao.createExpressionMap().eq("randomValue", whiteAlpacaRandom)
					.eq("deployId", fishSlotmachineDeploy.getId()).get(FishSlotMachine.class);
			// 将初始结果和特殊结果保存
			maps.put("specialFish", fishSlotMachine.getFishName());
			maps.put("result", whiteAlpaca1.getFishName() + "," + whiteAlpaca2.getFishName());
			String[] randoms = { whiteAlpaca1.getFishName(), whiteAlpaca2.getFishName() };
			Integer sumGold = 0;
			String multiple = "";
			// 判断用户是否中奖并处理
			try {
				// 前两个form循环用于解析用户下注
				for (String strs : bets) {
					String[] fishs = strs.split(":");
					for (int i = 0; i < fishs.length; i++) {
						// 白羊驼随机出的两个值
						for (int j = 0; j < randoms.length; j++) {
							// 判断用户下注是否有购买
							if (fishs[i].equals(randoms[j])) {
								// 用户中奖，获取用户下注金额
								BigDecimal betAmount = BigDecimal.valueOf(Double.valueOf(fishs[i + 1]));
								FishMagnificationDeploy fishMagnificationDeploy = dbDao.createExpressionMap()
										.eq("fishName", randoms[j]).get(FishMagnificationDeploy.class);
								// 获得该鱼的倍数
								BigDecimal odds = BigDecimal
										.valueOf(Double.valueOf(fishMagnificationDeploy.getMultiple()));
								// 保存倍数
								multiple += fishMagnificationDeploy.getFishName() + ":" + odds + ",";
								fishSlotMachineBet.setOdds(odds.toString());
								BigDecimal gold = betAmount.multiply(odds);
								fishSlotMachineBet.setGetGoldCoins(gold.intValue());
								// 将所获得的金币增加到用户账号中
								userServicer.addGold(user.getId(), gold.longValue(), GoldSourceType.win,
										GameType.slot_machine, null);
								fishSlotMachineBet.setBetDate(new Date());
								dbDao.save(fishSlotMachineBet);
								sumGold += gold.intValue();
							}
						}
					}
				}
			} catch (Exception e) {
				throw new BusinessException("数据格式转换失败");
			}
			if (StringUtils.isNotBlank(multiple)) {
				maps.put("multiple", multiple.substring(0, multiple.length() - 1));
			}
			maps.put("gold", sumGold);
			return MsgFactory.success("1", maps);
		} else if (fishSlotMachine.getFishName().equals("粉羊驼")) {
			// 判断随机值出的是否还是粉羊驼
			Integer alpacaRandom = getRandomValue();
			for (;;) {
				alpacaRandom = getRandomValue();
				FishSlotMachine fishSlotMachine2 = dbDao.createExpressionMap().eq("randomValue", alpacaRandom)
						.eq("deployId", fishSlotmachineDeploy.getId()).get(FishSlotMachine.class);
				if (fishSlotMachine2.getFishName().equals("粉羊驼") || fishSlotMachine2.getFishName().equals("白羊驼")) {
					continue;
				}
				break;
			}
			FishSlotMachine alpaca = dbDao.createExpressionMap().eq("randomValue", alpacaRandom)
					.eq("deployId", fishSlotmachineDeploy.getId()).get(FishSlotMachine.class);
			// 保存特殊鱼
			maps.put("specialFish", fishSlotMachine.getFishName());
			// 特殊处理后的结果
			maps.put("result", alpaca.getFishName());

			try {
				for (String strs : bets) {
					String[] fishs = strs.split(":");
					for (int i = 0; i < fishs.length; i++) {
						// 判断用户是否中奖
						if (fishs[i].equals(alpaca.getFishName())) {
							// 用户中奖，获取用户下注金额
							BigDecimal betAmount = BigDecimal.valueOf(Double.valueOf(fishs[i + 1]));
							FishMagnificationDeploy fishMagnificationDeploy = dbDao.get(alpaca.getMultipleId(),
									FishMagnificationDeploy.class);
							// 获得该鱼的倍数
							BigDecimal odds = BigDecimal.valueOf(Double.valueOf(fishMagnificationDeploy.getMultiple()));
							// 保存倍数
							maps.put("multiple", odds);
							fishSlotMachineBet.setOdds(odds.toString());
							BigDecimal gold = betAmount.multiply(odds).multiply(BigDecimal.valueOf(2));
							fishSlotMachineBet.setGetGoldCoins(gold.intValue());
							// 将所获得的金币增加到用户账号中
							userServicer.addGold(user.getId(), gold.longValue(), GoldSourceType.win,
									GameType.slot_machine, null);
							fishSlotMachineBet.setBetDate(new Date());
							dbDao.save(fishSlotMachineBet);
							maps.put("gold", gold.intValue());
							return MsgFactory.success("1", maps);
						}
					}
				}
			} catch (Exception e) {
				throw new BusinessException("数据格式转换失败");
			}

		} else {
			maps.put("result", fishSlotMachine.getFishName());
			try {
				for (String strs : bets) {
					String[] fishs = strs.split(":");
					for (int i = 0; i < fishs.length; i++) {
						// 判断用户是否中奖
						if (fishs[i].equals(fishSlotMachine.getFishName())) {
							// 用户中奖，获取用户下注金额
							BigDecimal betAmount = BigDecimal.valueOf(Double.valueOf(fishs[i + 1]));
							FishMagnificationDeploy fishMagnificationDeploy = dbDao.get(fishSlotMachine.getMultipleId(),
									FishMagnificationDeploy.class);
							// 获得该鱼的倍数
							BigDecimal odds = BigDecimal.valueOf(Double.valueOf(fishMagnificationDeploy.getMultiple()));
							// 保存倍数
							maps.put("multiple", odds);
							fishSlotMachineBet.setOdds(odds.toString());
							BigDecimal gold = betAmount.multiply(odds);
							fishSlotMachineBet.setGetGoldCoins(gold.intValue());
							// 将所获得的金币增加到用户账号中
							userServicer.addGold(user.getId(), gold.longValue(), GoldSourceType.win,
									GameType.slot_machine, null);
							fishSlotMachineBet.setBetDate(new Date());
							dbDao.save(fishSlotMachineBet);
							maps.put("gold", gold.intValue());
							return MsgFactory.success("1", maps);
						}
					}
				}
			} catch (Exception e) {
				throw new BusinessException("数据格式转换失败");
			}
		}
		// 下注时间
		fishSlotMachineBet.setBetDate(new Date());
		dbDao.save(fishSlotMachineBet);
		maps.put("gold", null);
		return MsgFactory.success("0", maps);
	}

	/**
	 * 获取随机值
	 * 
	 * @return
	 */
	public Integer getRandomValue() {
		Random random = new Random();
		Integer randomResult = 0;
		for (int i = 0; i < 3; i++) {
			randomResult += random.nextInt(9);
		}
		return randomResult;
	}

}
