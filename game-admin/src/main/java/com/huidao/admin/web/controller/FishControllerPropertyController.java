package com.huidao.admin.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huidao.common.anno.JSON;
import com.huidao.common.anno.Permission;
import com.huidao.common.entity.FishControllerProperty;
import com.huidao.common.interfaces.admin.IFishControllerPropertyService;
import com.huidao.common.msg.MsgDto;
import com.yehebl.orm.data.common.dto.Page;
import com.yehebl.orm.data.common.dto.PageUtil;

@Controller
@RequestMapping("/fcp")
public class FishControllerPropertyController {

	@Reference
	private IFishControllerPropertyService fishControllerPropertyService;

	@RequestMapping("/findFishContollerProperty")
	@Permission("fish_controller_property")
	@JSON
	public MsgDto<Page<FishControllerProperty>> findFishContollerProperty(Integer type) {

		return fishControllerPropertyService.findFishControllerProperty(PageUtil.getPage().getPage(),
				PageUtil.getPage().getSize(), type);
	}

	@RequestMapping("/addFishControllerProperty")
	@Permission("fish_controller_property_add")
	@JSON
	public MsgDto<Integer> addFishControllerProperty(FishControllerProperty fishControllerProperty, String userId,
			String gameId) {

		return fishControllerPropertyService.add(fishControllerProperty, userId, gameId);
	}

	@RequestMapping("/updateFishControllerProperty")
	@Permission("fish_controller_property_update")
	@JSON
	public MsgDto<Integer> updateFishControllerProperty(String id, Double wholeDangWei, Double playerDangWei,
			Double fishDangWei, Double fuDuDangWei) {

		return fishControllerPropertyService.update(id, wholeDangWei, playerDangWei, fishDangWei, fuDuDangWei);
	}

	@RequestMapping("/deleteFishControllerProperty")
	@Permission("fish_controller_property_delete")
	@JSON
	public MsgDto<Integer> deleteFishControllerProperty(String id) {
		return fishControllerPropertyService.delete(id);
	}

	@RequestMapping("/getFishControllerProperty")
	@Permission("fish_controller_property_get")
	@JSON
	public MsgDto<FishControllerProperty> getFishControllerProperty(String id) {

		return fishControllerPropertyService.getFishControllerProperty(id);
	}

	@RequestMapping("/getUserFishControllerProperty")
	@Permission("fish_controller_property_user,fish_controller_property_game")
	@JSON
	public MsgDto<FishControllerProperty> getUserFishControllerProperty(String userId, String gameId) {
		return fishControllerPropertyService.getUserFishControllerProperty(userId, gameId);
	}

	@RequestMapping("/deleteUserGameFishControllerProperty")
	@Permission("fish_controller_property_delete")
	@JSON
	public MsgDto<Integer> deleteUserGameFishControllerProperty(String userId, String gameId, Integer type) {

		return fishControllerPropertyService.deleteUserGameFishControllerProperty(userId, gameId, type);
	}

}
