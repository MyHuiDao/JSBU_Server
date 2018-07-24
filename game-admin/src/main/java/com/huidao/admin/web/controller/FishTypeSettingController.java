package com.huidao.admin.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huidao.common.anno.JSON;
import com.huidao.common.anno.Permission;
import com.huidao.common.entity.FishTypeSetting;
import com.huidao.common.interfaces.admin.IFishTypeSettingService;
import com.huidao.common.msg.MsgDto;
import com.yehebl.orm.data.common.dto.Page;
import com.yehebl.orm.data.common.dto.PageUtil;

@Controller
@RequestMapping("/fishTypeSetting")
public class FishTypeSettingController {

	@Reference
	private IFishTypeSettingService fishTypeSettingService;
	
	@RequestMapping("/findFishTypeSettingAll")
	@Permission("game_fish_type_setting")
	@JSON
	public MsgDto<Page<FishTypeSetting>> findFishTypeSettingAll(Integer level) {

		return fishTypeSettingService.findFishTypeSettingAll(level,PageUtil.getPage().getPage(), PageUtil.getPage().getSize());
	}

	@RequestMapping("/addFishTypeSetting")
	@Permission("fish_type_setting_add")
	@JSON
	public MsgDto<Integer> addFishTypeSetting(FishTypeSetting fishTypeSetting) {
		return fishTypeSettingService.add(fishTypeSetting);
	}

	@RequestMapping("/updateFishTypeSetting")
	@Permission("fish_type_setting_update")
	@JSON
	public MsgDto<Integer> updateFishTypeSetting(FishTypeSetting fishTypeSetting) {
		return fishTypeSettingService.update(fishTypeSetting);
	}

	@RequestMapping("/deleteFishTypeSetting")
	@Permission("fish_type_setting_delete")
	@JSON
	public MsgDto<Integer> deleteFishTypeSetting(String id) {
		return fishTypeSettingService.delete(id);
	}

	@RequestMapping("/getFishTypeSetting")
	@Permission("fish_type_setting_get")
	@JSON
	public MsgDto<FishTypeSetting> getFishTypeSetting(String id) {
		return fishTypeSettingService.getFishTypeSetting(id);
	}

}
