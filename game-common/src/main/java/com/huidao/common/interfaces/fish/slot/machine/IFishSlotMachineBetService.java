package com.huidao.common.interfaces.fish.slot.machine;

import java.util.Map;

import com.huidao.common.entity.User;
import com.huidao.common.msg.MsgDto;

/**
 * 用户下注记录
 * 
 * @author Administrator
 *
 */
public interface IFishSlotMachineBetService {
	/**
	 * 下注
	 * 
	 * @param fishSlotMachineBet
	 * @return
	 */
	public MsgDto<Map<String, Object>> bet(User user, String bet);
}
