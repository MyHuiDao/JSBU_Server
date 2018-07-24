package com.huidao.admin.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huidao.common.anno.JSON;
import com.huidao.common.anno.Permission;
import com.huidao.common.entity.FishSlotMachine;
import com.huidao.common.interfaces.fish.slot.machine.IFishSlotMachineService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.data.common.dto.Page;
import com.yehebl.orm.data.common.dto.PageUtil;

@Controller
@RequestMapping("/sysFsm")
public class FishSlotMachineController {

	@Reference
	private IFishSlotMachineService fishSlotMachineService;

	@RequestMapping("/findFishSlotMachineAll")
	@Permission
	@JSON
	public MsgDto<Page<FishSlotMachine>> findFishSlotMachineAll() {

		return fishSlotMachineService.findFishSlotMachineAll(PageUtil.getPage().getPage(),
				PageUtil.getPage().getSize());
	}

	@RequestMapping("/addFishSlotMachine")
	@Permission
	@JSON
	public MsgDto<Integer> addFishSlotMachine(FishSlotMachine fishSlotMachine) {

		return fishSlotMachineService.add(fishSlotMachine);
	}

	@RequestMapping("/findFishSlotMachineDeploy")
	@Permission
	@JSON
	public MsgDto<List<FishSlotMachine>> findFishSlotMachineDeploy(String deployId) {

		return MsgFactory.success(fishSlotMachineService.findFishSlotMachineList(deployId));
	}

}
