package com.huidao.game.web.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huidao.common.anno.JSON;
import com.huidao.common.entity.ShareBonus;
import com.huidao.common.entity.UserShare;
import com.huidao.common.enums.GameType;
import com.huidao.common.enums.GoldSourceType;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.admin.IShareBonusService;
import com.huidao.common.interfaces.admin.IUserShareService;
import com.huidao.common.interfaces.game.IUserService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;

@Controller
@RequestMapping("/userShare")
public class UserShareController {

	@Reference
	private IUserShareService userShareService;

	@Reference
	private IShareBonusService shareBonusService;

	@Reference
	private IUserService userService;

	@RequestMapping("/sharePromotion")
	@JSON
	public MsgDto<Integer> sharePromotion(String userId) {
		if (StringUtils.isBlank(userId)) {
			throw ParamException.param_not_exception;
		}
		// 1.统计用户推广的次数
		Integer count = userShareService.findUserShareAll(userId);
		List<ShareBonus> lists = shareBonusService.findShareBonusAll();
		// 2.将用户分享记录保存
		UserShare userShare = new UserShare();
		userShare.setGameUserId(userId);
		userShare.setCreatedate(new Date());
		userShareService.add(userShare);
		for (ShareBonus shareBouns : lists) {
			// 3.判断用户当天分享次数是否有满足分享奖励
			if (shareBouns.getCount() == count) {
				userService.addGold(userId, shareBouns.getGold(), GoldSourceType.free, "微信分享", GameType.app,null);
				return MsgFactory.success(shareBouns.getGold());
			}
		}
		return MsgFactory.success();
	}

}
