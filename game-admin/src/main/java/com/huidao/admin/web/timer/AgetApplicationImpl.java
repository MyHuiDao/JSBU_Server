package com.huidao.admin.web.timer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huidao.common.cache.redis.RedisCache;
import com.huidao.common.entity.AgenApply;
import com.huidao.common.entity.SysUser;
import com.huidao.common.entity.User;
import com.huidao.common.enums.SysUserType;
import com.huidao.common.interfaces.admin.IAgenApplyService;
import com.huidao.common.interfaces.admin.ISysUserService;
import com.huidao.common.interfaces.game.IUserService;

public class AgetApplicationImpl implements Runnable {

	private static final Logger log = LoggerFactory.getLogger(AgetApplicationImpl.class);

	private IAgenApplyService agenApplyService;
	private IUserService userService;
	private ISysUserService sysUserService;

	public AgetApplicationImpl(IAgenApplyService agenApplyService, IUserService userService,
			ISysUserService sysUserService) {
		super();
		this.agenApplyService = agenApplyService;
		this.userService = userService;
		this.sysUserService = sysUserService;
	}

	@Override
	public void run() {
		while (true) {
			try {
				// 5.休眠五分钟（300000），再唤醒
				Thread.sleep(300000);
				RedisCache.lock("agetApplicationImplRun");
				// 1.查询出所有未审核的代理商
				List<AgenApply> agenApplies = agenApplyService.findAgenApplyAll(0);
				if (agenApplies != null && agenApplies.size() > 0) {
					for (AgenApply agenApply : agenApplies) {
						// 3.为审核通过的代理商添加后台管理账户
						User user = userService.getUserById(agenApply.getGameUserId());
						SysUser gameSysUser = sysUserService.getAccountUser(agenApply.getAccount());
						if (gameSysUser == null) {
							// 4.创建后台用户对象，将相关信息添加到后台用户表中
							SysUser sysUser = new SysUser();
							// 用户的昵称
							sysUser.setNickName(user.getNickName());
							// 账户
							sysUser.setAccount(agenApply.getAccount());
							// 密码
							sysUser.setPassword(agenApply.getPassword());
							// 盐
							sysUser.setSalt(agenApply.getSalt());
							// 类型
							sysUser.setType(SysUserType.agen.toString());
							sysUser.setMoney(BigDecimal.ZERO);
							// 状态
							sysUser.setStatus(0);
							sysUser.setUpdateDate(new Date());
							// 游戏用户id
							sysUser.setGameUserId(user.getId());
							// 2.对未审核的代理商申请进行处理
							agenApply.setAuditDate(new Date());
							agenApply.setAuditStatus(1);
							agenApplyService.update(agenApply);
							sysUserService.addAgent(sysUser, "1");
						} else {
							// 2.对未审核的代理商申请进行处理
							agenApply.setAuditDate(new Date());
							agenApply.setAuditStatus(3);
							agenApplyService.update(agenApply);
						}

					}

				}
				RedisCache.unlock("agetApplicationImplRun");
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
	}
}
