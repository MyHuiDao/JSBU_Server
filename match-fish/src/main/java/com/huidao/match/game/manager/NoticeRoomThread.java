package com.huidao.match.game.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huidao.common.cache.redis.RedisCache;
import com.huidao.common.contants.GameSettingContants;
import com.huidao.common.entity.MatchFishBets;
import com.huidao.common.entity.MatchFishOpenPrize;
import com.huidao.common.interfaces.game.IGameSettingService;
import com.huidao.common.interfaces.match.fish.IOpenPrizeService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.huidao.match.base.message.manager.MessageManager;
import com.huidao.match.base.spring.JSONDto;
import com.huidao.match.game.contants.TypeCode;
import com.huidao.match.game.dto.RoomDto;

public class NoticeRoomThread extends Thread {

	private static final Logger log = LoggerFactory.getLogger(NoticeRoomThread.class);

	private RoomManager roomManager;

	private IOpenPrizeService openPrizeService;

	private MessageManager messageManager;

	private IGameSettingService gameSettingService;

	public NoticeRoomThread(RoomManager roomManager, MessageManager messageManager, IOpenPrizeService openPrizeService,
			IGameSettingService gameSettingService) {
		this.roomManager = roomManager;
		this.openPrizeService = openPrizeService;
		this.messageManager = messageManager;
		this.gameSettingService = gameSettingService;
	}

	@Override
	public void run() {
		String roomId = null;
		JSONDto jd = new JSONDto();
		jd.setClazz(new Class[] { MatchFishBets.class });
		jd.setProperty(new String[] { "betsGold,getGold,odds,betsNumber" });
		MessageManager.currentJson.set(jd);
		while (true) {
			try {
				MatchFishOpenPrize matchFishOpenPrize = openPrizeService.getNoOpen();
				if (matchFishOpenPrize == null) {
					// 当前没有未开奖的记录则休眠10秒
					Thread.sleep(10 * 1000);
					continue;
				}
				roomId = matchFishOpenPrize.getRoomId();
				RoomDto room1 = roomManager.getRoomById(roomId);
				long seconds = 0L;
				if (room1 != null) {
					seconds = room1.getSeconds();
				} else {
					seconds = Long.valueOf(gameSettingService.getValue(GameSettingContants.match_fish_seconds));
				}
				long sleepTime = System.currentTimeMillis() - matchFishOpenPrize.getCreateDate().getTime();
				if (sleepTime < seconds * 1000) {
					Thread.sleep(seconds * 1000 - sleepTime);
				}
				boolean flag = true;
				if (room1 == null) {
					flag = false;
				}
				RedisCache.lock(RoomManager.raceFish_room + roomId);
				// 计算开奖结果
				MsgDto<Map<String, Object>> msg = openPrizeService.openPrize(matchFishOpenPrize, flag);
				if (MsgFactory.isSuccess(msg)) {
					RoomDto room = roomManager.getRoomById(roomId);
					if (room == null) {
						RedisCache.unlock(RoomManager.raceFish_room + roomId);
						continue;
					}
					Map<String, Object> data = msg.getData();
					String nextId = data.get("nextId") + "";
					room.setOpenPrezeId(nextId);
					room.setLotteryTime(System.currentTimeMillis() + 30 * 1000);
					RedisCache.set(RoomManager.raceFish_room + roomId, room);
					List<String> userList = room.getUserLists();
					double aVar = (ThreadLocalRandom.current().nextDouble(6) + 12) / 10;
					double cycleVar = (ThreadLocalRandom.current().nextDouble(10) + 30) / 6;
					double angle = (ThreadLocalRandom.current().nextDouble(90));
					double bSpeedVar = 0.6f + (ThreadLocalRandom.current().nextDouble(6) - 3) / 10;
					for (String userId : userList) {
						Map<String, Object> result = new HashMap<>();
						result.put("result", data.get("result") + "");
						result.put("aVar", aVar);
						result.put("cycleVar", cycleVar);
						result.put("angle", angle);
						result.put("bSpeedVar", bSpeedVar);
						if (StringUtils.isBlank(userId)) {
							continue;
						}
						result.put("gold", data.containsKey("gold" + userId) ? data.get("gold" + userId) + "" : "0");
						result.put("bets", data.containsKey("bets" + userId) ? data.get("bets" + userId) : null);
						messageManager.sendMsg(userId, TypeCode.open, result);
					}
					RedisCache.unlock(RoomManager.raceFish_room + roomId);
				}

			} catch (Exception e) {
				log.error(e.getMessage(), e);
			} finally {
				if (StringUtils.isNotBlank(roomId)) {
					RedisCache.unlock(RoomManager.raceFish_room + roomId);
				}
			}
		}
	}
	
}
