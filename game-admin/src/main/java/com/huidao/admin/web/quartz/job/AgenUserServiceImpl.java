package com.huidao.admin.web.quartz.job;

import java.util.List;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huidao.common.contants.GameSettingContants;
import com.huidao.common.entity.SysUser;
import com.huidao.common.entity.User;
import com.huidao.common.interfaces.admin.ISysUserService;
import com.huidao.common.interfaces.game.IGameSettingService;
import com.huidao.common.interfaces.game.IUserService;

@Component
public class AgenUserServiceImpl {
	@Reference
	private IUserService userService;
	@Reference
	private ISysUserService sysUserService;
	@Reference
	private IGameSettingService gameSettingService;

	/**
	 * 审核取消代理资格
	 */
	public void disqualification() {
		String mark = gameSettingService.getValue(GameSettingContants.whether_open_proxy_features);
		if (mark.equals("N")) {
			return;
		}
		// 1.得到所有的代理用户
		List<SysUser> lists = sysUserService.getAgenUser();
		// 2.查询上个月推荐人数是否有10人
		for (SysUser a : lists) {
			List<User> users = userService.getRecommendUserNumber(a.getGameUserId());
			// 3.若没有10人取消代理资格
			if (users.size() < 10) {
				SysUser sysUser = sysUserService.getGameUserId(a.getGameUserId());
				if (sysUser != null) {
					sysUser.setStatus(1);
					sysUserService.update(sysUser, "1");
				}
			}
		}
	}
}
