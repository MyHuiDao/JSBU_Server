package com.huidao.service.match.fish;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.huidao.common.contants.GameSettingContants;
import com.huidao.common.entity.MatchFishBets;
import com.huidao.common.entity.MatchFishOpenPrize;
import com.huidao.common.enums.GameType;
import com.huidao.common.enums.GoldSourceType;
import com.huidao.common.interfaces.game.IGameSettingService;
import com.huidao.common.interfaces.game.IUserService;
import com.huidao.common.interfaces.match.fish.IOpenPrizeService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.data.common.IDao;

@Component
@Service
public class OpenPrizeService implements IOpenPrizeService {

	@Autowired
	private IDao dao;

	@Autowired
	private IUserService userService;
	
	@Autowired
	private IGameSettingService gameSettingService;

	@Override
	public MatchFishOpenPrize getNoOpen() {
		return dao.createExpressionMap().eq("status", 0).order("createDate").getOne(MatchFishOpenPrize.class);
	}

	@Override
	public MsgDto<Boolean> update(MatchFishOpenPrize matchFishOpenPrize) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public MsgDto<Map<String, Object>> openPrize(MatchFishOpenPrize matchFishOpenPrize, boolean flag) {
		matchFishOpenPrize.setStatus(1);
		int update = dao.update("update match_fish_open_prize set status=1 where status=? and id =?",MatchFishOpenPrize.class,0,matchFishOpenPrize.getId());
		if (update == 0) {
			return MsgFactory.fail();
		}
		Double odds1=Double.valueOf(gameSettingService.getValue(GameSettingContants.match_fish_odds_number1));
		Double odds2=Double.valueOf(gameSettingService.getValue(GameSettingContants.match_fish_odds_number2));
		List<MatchFishBets> list = dao.createExpressionMap().eq("openPrizeId", matchFishOpenPrize.getId())
				.find(MatchFishBets.class);

		Long sumBetsGold = 0L;
		for (MatchFishBets mb : list) {
			sumBetsGold += mb.getBetsGold();
		}
		List<String> accptOpenPrize = new ArrayList<>();
		for (int i = 1; i <= 4; i++) {
			for (int j = 1; j <= 4; j++) {
				if (i == j) {
					continue;
				}
				String hypothesis2 = i + "," + j;
				String hypothesis = i + "";
				Long hypothesisOutGold = 0L;
				for (MatchFishBets mb : list) {
					if (mb.getBetsNumber().equals(hypothesis2)) {
						hypothesisOutGold += BigDecimal.valueOf(odds2).multiply(BigDecimal.valueOf(mb.getBetsGold()))
								.longValue();
					} else if (mb.getBetsNumber().equals(hypothesis)) {
						hypothesisOutGold += BigDecimal.valueOf(odds1).multiply(BigDecimal.valueOf(mb.getBetsGold()))
								.longValue();
					}
				}
				if (sumBetsGold >= hypothesisOutGold) {
					accptOpenPrize.add(hypothesis2);
				}
			}
		}
		
		String twoNumber=accptOpenPrize.get(ThreadLocalRandom.current().nextInt(accptOpenPrize.size()));
		List<String> allNumber=new ArrayList<>();
		allNumber.add("1");
		allNumber.add("2");
		allNumber.add("3");
		allNumber.add("4");
		String [] twoNumbers=twoNumber.split(",");
		for (int i = 0; i < twoNumbers.length; i++) {
			for (int j = 0; j < allNumber.size(); j++) {
				if(allNumber.get(j).equals(twoNumbers[i])) {
					allNumber.remove(j);
					break;
				}
			}
		}
		int number3= ThreadLocalRandom.current().nextInt(1);
		int number4=number3==0?1:0;
		String resultOpenPrize=twoNumber+","+allNumber.get(number3)+","+allNumber.get(number4);
		Map<String, Object> result = new HashMap<>();
		result.put("result", resultOpenPrize);
		matchFishOpenPrize.setResult(resultOpenPrize);
		matchFishOpenPrize.setStatus(2);
		matchFishOpenPrize.setOpenDate(new Date());
		dao.update(matchFishOpenPrize);
		for (MatchFishBets mb : list) {
			mb.setStatus(1);
			mb.setGetGold(0L);
			mb.setOdds(BigDecimal.ZERO);
			if (resultOpenPrize.startsWith(mb.getBetsNumber())) {
				if(mb.getBetsNumber().split(",").length==1) {
					mb.setOdds(BigDecimal.valueOf(odds1));
				}else if(mb.getBetsNumber().split(",").length==2){
					mb.setOdds(BigDecimal.valueOf(odds2));
				}
				mb.setGetGold(mb.getOdds().multiply(BigDecimal.valueOf(mb.getBetsGold())).longValue());
				mb.setStatus(2);
				userService.addGold(mb.getUserId(), mb.getGetGold(), GoldSourceType.win, GameType.match_fish, null);
			}
			List<MatchFishBets> matchFishBetsList=null;
			if(!result.containsKey("bets"+mb.getUserId())) {
				matchFishBetsList=new ArrayList<>();
				result.put("bets"+mb.getUserId(), matchFishBetsList);
			}else {
				matchFishBetsList=(List<MatchFishBets>) result.get("bets"+mb.getUserId());
			}
			matchFishBetsList.add(mb);
			result.put("bets", matchFishBetsList);
			if(result.containsKey(("gold" + mb.getUserId()))){
				result.put("gold" + mb.getUserId(), Long.valueOf(result.get("gold" + mb.getUserId())+"")+ mb.getGetGold());
			}else {
				result.put("gold" + mb.getUserId(),  mb.getGetGold() + "");
			}
			dao.update(mb);
		}

		if (flag) {
			matchFishOpenPrize.setId(null);
			matchFishOpenPrize.setOpenDate(null);
			matchFishOpenPrize.setResult(null);
			matchFishOpenPrize.setStatus(0);
			matchFishOpenPrize.setCount(null);
			matchFishOpenPrize.setCreateDate(new Date(System.currentTimeMillis()+30*1000));
			dao.save(matchFishOpenPrize);
			result.put("nextId", matchFishOpenPrize.getId());
		}
		return MsgFactory.success(result);
	}

}
