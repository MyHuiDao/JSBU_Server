package com.huidao.admin.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huidao.common.anno.JSON;
import com.huidao.common.anno.Permission;
import com.huidao.common.entity.ShareBonus;
import com.huidao.common.interfaces.admin.IShareBonusService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;

/**
 * 分享奖励配置控制层
 * 
 * @author lenovo
 *
 */
@Controller
@RequestMapping("/shareBonus")
public class ShareBonusController {

	@Reference
	private IShareBonusService shareBounsService;

	@RequestMapping(value="/findShareBonusAll",method=RequestMethod.GET)
	@Permission("share_bonus_get")
	@JSON
	public MsgDto<List<ShareBonus>> findShareBonusAll() {

		return MsgFactory.success(shareBounsService.findShareBonusAll());
	}

	@RequestMapping("/addShareBonus")
	@Permission("share_bonus_add")
	@JSON
	public MsgDto<String> addShareBonus(ShareBonus shareBouns) {

		return shareBounsService.add(shareBouns);
	}

	@RequestMapping("/updateShareBonus")
	@Permission("share_bonus_update")
	@JSON
	public MsgDto<String> updateShareBonus(ShareBonus shareBouns) {

		return shareBounsService.update(shareBouns);
	}

	@RequestMapping("/deleteShareBonus")
	@Permission("share_bonus_delete")
	@JSON
	public MsgDto<String> deleteShareBonus(String id) {

		return shareBounsService.delete(id);
	}

	@RequestMapping("/getShareBonus")
	@Permission("share_bonus_get")
	@JSON
	public MsgDto<ShareBonus> getShareBonus(String id) {

		return MsgFactory.success(shareBounsService.get(id));
	}

}
