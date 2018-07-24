package com.huidao.match.game.handler;

import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huidao.common.anno.JSON;
import com.huidao.common.entity.MatchFishBets;
import com.huidao.common.entity.MatchFishOpenPrize;
import com.huidao.common.entity.User;
import com.huidao.match.base.anno.LockRoom;
import com.huidao.match.base.anno.UnLockRoom;
import com.huidao.match.base.session.SessionManager;
import com.huidao.match.game.contants.TypeCode;
import com.huidao.match.game.dto.RoomDto;
import com.huidao.match.game.manager.RoomManager;
import com.yehebl.handler.annotaction.Handler;

@Handler
@Component
public class GameHandler {

	@Autowired
	private SessionManager sessionManager;

	@Autowired
	private RoomManager roomManager;

	@Handler(TypeCode.add_room)
	@UnLockRoom
	@JSON(clazz = { RoomDto.class }, property = { "countdown,roomId,status,onlineUsers" })
	public void addRoom(Session session) {
		String userId = sessionManager.getUserId(session);
		roomManager.addRoom(userId);
	}

	@Handler(TypeCode.bets)
	@LockRoom
	@UnLockRoom
	@JSON(clazz = { MatchFishBets.class }, property = { "betsGold,betsNumber" })
	public void bets(Session session, String betsNumber, String betsGold) {
		String userId = sessionManager.getUserId(session);
		roomManager.bets(userId, betsNumber, betsGold);
	}

	@Handler(TypeCode.exit_room)
	public void exitRoom(Session session) {
		String userId = sessionManager.getUserId(session);
		roomManager.exitRoom(userId);
	}

	@Handler(TypeCode.get_user)
	@JSON(clazz = { User.class }, property = { "code,nickName,headImg,gold" })
	public void getUser(Session session) {
		String userId = sessionManager.getUserId(session);
		roomManager.getUser(userId);
	}

	@Handler(TypeCode.get_room_match_fish_open_prize)
	@JSON(clazz = { MatchFishOpenPrize.class }, property = { "count,result,openDate,status" })
	public void findMatchFishOpenPrize(Session session, Integer page, Integer size) {
		String userId = sessionManager.getUserId(session);
		roomManager.findMatchFishOpenPrize(page, size, userId);
	}

	@Handler(TypeCode.get_lottery_end_date)
	public void getLotteryEndDate(Session session) {
		String userId = sessionManager.getUserId(session);
		roomManager.getLotteryEndDate(userId);
	}

	@Handler(TypeCode.get_user_gold)
	public void getUserGold(Session session) {
		String userId = sessionManager.getUserId(session);
		roomManager.getUserGold(userId);
	}

}
