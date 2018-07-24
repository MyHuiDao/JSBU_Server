package com.huidao.admin.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huidao.common.anno.JSON;
import com.huidao.common.anno.Permission;
import com.huidao.common.entity.FishMagnificationDeploy;
import com.huidao.common.interfaces.fish.slot.machine.IFishMagnificationDeployService;
import com.huidao.common.msg.MsgDto;
import com.yehebl.orm.data.common.dto.Page;
import com.yehebl.orm.data.common.dto.PageUtil;

@Controller
@RequestMapping("/fmd")
public class FishMagnificationDeployController {
	@Reference
	private IFishMagnificationDeployService fishMagnificationDeployService;

	@RequestMapping("/find")
	@Permission("fish_magni_fication_deploy")
	@JSON
	public MsgDto<Page<FishMagnificationDeploy>> findFishMagnificationDeployAll() {
		return fishMagnificationDeployService.findFishMagnificationDepoyAll(PageUtil.getPage().getPage(),
				PageUtil.getPage().getSize());
	}

	@RequestMapping("/findAll")
	@Permission
	@JSON
	public MsgDto<List<FishMagnificationDeploy>> findAll() {

		return fishMagnificationDeployService.findAll();
	}

	@RequestMapping("/add")
	@Permission
	@JSON
	public MsgDto<Integer> addFishMagnificationDeploy(FishMagnificationDeploy fishMagnificationDeploy) {

		return fishMagnificationDeployService.add(fishMagnificationDeploy);
	}

	@RequestMapping("/update")
	@Permission("fish_magni_fication_deploy_update")
	@JSON
	public MsgDto<Integer> updateFishMagnificationDeploy(FishMagnificationDeploy fishMagnificationDeploy) {

		return fishMagnificationDeployService.update(fishMagnificationDeploy);
	}

	@RequestMapping("/delete")
	@Permission
	@JSON
	public MsgDto<Integer> deleteFishMagnificationDeploy(String id) {
		return fishMagnificationDeployService.delete(id);
	}

	@RequestMapping("/getFishMagnificationDeploy")
	@Permission
	@JSON
	public MsgDto<FishMagnificationDeploy> getFishMagnificationDeploy(String id) {

		return fishMagnificationDeployService.getFishMagnificationDeploy(id);
	}

}
