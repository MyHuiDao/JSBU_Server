package com.huidao.service.fish.slot.machine;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.huidao.common.entity.FishSlotmachineDeploy;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.fish.slot.machine.IFishSlotmachineDeployService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.DBDao;
import com.yehebl.orm.data.common.dto.Page;

@Service
@Component
public class FishSlotmachineDeployService implements IFishSlotmachineDeployService {

	@Autowired
	private DBDao dbDao;

	@Override
	public MsgDto<Page<FishSlotmachineDeploy>> findFishSlotmachineDeployAll(Integer page, Integer size) {

		return MsgFactory.success(dbDao.createExpressionMap().limit(page, size).findPage(FishSlotmachineDeploy.class));
	}

	@Override
	public MsgDto<String> add(FishSlotmachineDeploy fishSlotmachineDeploy) {
		if (fishSlotmachineDeploy == null) {
			throw ParamException.param_not_exception;
		}
		FishSlotmachineDeploy fishSlotmachineDeploy2 = dbDao.createExpressionMap()
				.eq("name", fishSlotmachineDeploy.getName()).get(FishSlotmachineDeploy.class);
		if (fishSlotmachineDeploy2 == null) {
			fishSlotmachineDeploy.setCreateDate(new Date());
			dbDao.save(fishSlotmachineDeploy);
			return MsgFactory.success(fishSlotmachineDeploy.getId());
		}
		return MsgFactory.success(fishSlotmachineDeploy2.getId());
	}

	@Override
	public MsgDto<Integer> update(FishSlotmachineDeploy fishSlotmachineDeploy) {
		if (fishSlotmachineDeploy == null) {
			throw ParamException.param_not_exception;
		}
		return MsgFactory.success(dbDao.update(fishSlotmachineDeploy));
	}

	@Override
	public MsgDto<Integer> delete(String id) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		return MsgFactory.success(dbDao.deleteById(id, FishSlotmachineDeploy.class));
	}

	@Override
	public MsgDto<FishSlotmachineDeploy> get(String id) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		return MsgFactory.success(dbDao.get(id, FishSlotmachineDeploy.class));
	}

}
