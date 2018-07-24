package com.huidao.admin.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huidao.common.anno.JSON;
import com.huidao.common.anno.Permission;
import com.huidao.common.entity.FishSlotmachineDeploy;
import com.huidao.common.interfaces.fish.slot.machine.IFishSlotmachineDeployService;
import com.huidao.common.msg.MsgDto;
import com.yehebl.orm.data.common.dto.Page;
import com.yehebl.orm.data.common.dto.PageUtil;

@Controller
@RequestMapping("/fsd")
public class FishSlotmachineDeployController {

	@Reference
	private IFishSlotmachineDeployService fishSlotmachineDeployService;

	/**
	 * 分页查询
	 * 
	 * @return
	 */
	@RequestMapping("/findAll")
	@Permission("fish_slotmachine_deploy")
	@JSON
	public MsgDto<Page<FishSlotmachineDeploy>> findAll() {

		return fishSlotmachineDeployService.findFishSlotmachineDeployAll(PageUtil.getPage().getPage(),
				PageUtil.getPage().getSize());
	}

	/**
	 * 添加配置
	 * 
	 * @param fishSlotmachineDeploy
	 * @return
	 */
	@RequestMapping("/addDeploy")
	@Permission("fish_slotmachine_deploy_add")
	@JSON
	public MsgDto<String> addDeploy(FishSlotmachineDeploy fishSlotmachineDeploy) {

		return fishSlotmachineDeployService.add(fishSlotmachineDeploy);
	}

	/**
	 * 修改配置
	 * 
	 * @param fishSlotmachineDeploy
	 * @return
	 */
	@RequestMapping("/updateDeploy")
	@Permission("fish_slotmachine_deploy_update")
	@JSON
	public MsgDto<Integer> updateDeploy(FishSlotmachineDeploy fishSlotmachineDeploy) {

		return fishSlotmachineDeployService.update(fishSlotmachineDeploy);
	}

	/**
	 * 删除配置
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteDeploy")
	@Permission
	@JSON
	public MsgDto<Integer> deleteDeploy(String id) {

		return fishSlotmachineDeployService.delete(id);
	}

	/**
	 * 获取相关信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/getDeploy")
	@Permission
	@JSON
	public MsgDto<FishSlotmachineDeploy> getDeploy(String id) {

		return fishSlotmachineDeployService.get(id);
	}

}
