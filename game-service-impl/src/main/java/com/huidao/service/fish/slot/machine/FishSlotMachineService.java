package com.huidao.service.fish.slot.machine;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.huidao.common.entity.FishSlotMachine;
import com.huidao.common.entity.FishSlotmachineDeploy;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.fish.slot.machine.IFishSlotMachineService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.DBDao;
import com.yehebl.orm.data.common.dto.Page;

@Service
@Component
public class FishSlotMachineService implements IFishSlotMachineService {

	@Autowired
	private DBDao dbDao;

	@Override
	public MsgDto<Page<FishSlotMachine>> findFishSlotMachineAll(Integer page, Integer size) {
		Map<String, Object> parms = new HashMap<String, Object>();
		return MsgFactory.success(dbDao.findPageByMap(page, size, parms, FishSlotMachine.class));
	}

	@Override
	@Transactional
	public MsgDto<Integer> add(FishSlotMachine fishSlotMachine) {
		if (fishSlotMachine == null) {
			throw ParamException.param_not_exception;
		}
		FishSlotMachine fishSlotMachine2 = dbDao.createExpressionMap()
				.eq("randomValue", fishSlotMachine.getRandomValue()).eq("deployId", fishSlotMachine.getDeployId())
				.get(FishSlotMachine.class);
		if (fishSlotMachine2 != null) {
			fishSlotMachine2.setFishName(fishSlotMachine.getFishName());
			fishSlotMachine2.setRandomValue(fishSlotMachine.getRandomValue());
			fishSlotMachine2.setMultipleId(fishSlotMachine.getMultipleId());
			return MsgFactory.success(dbDao.update(fishSlotMachine2));
		}
		fishSlotMachine.setCreateDate(new Date());
		return MsgFactory.success(dbDao.save(fishSlotMachine));
	}

	@Override
	@Transactional
	public MsgDto<Integer> update(FishSlotMachine fishSlotMachine) {
		if (fishSlotMachine == null) {
			throw ParamException.param_not_exception;
		}
		return MsgFactory.success(dbDao.update(fishSlotMachine));
	}

	@Override
	public MsgDto<Integer> delete(String id) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		return MsgFactory.success(dbDao.deleteById(id, FishSlotMachine.class));
	}

	@Override
	public MsgDto<FishSlotMachine> getFishSlotMachine(String id) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		return MsgFactory.success(dbDao.get(id, FishSlotMachine.class));
	}

	@Override
	public FishSlotMachine getFishSlotMachineRandomValue(Integer randomValue) {
		return dbDao.getByExpression("EQ_randomValue", randomValue, FishSlotMachine.class);
	}

	@Override
	public List<FishSlotMachine> getFishSlotMachineType(Integer type) {
		if (type == null) {
			throw ParamException.param_not_exception;
		}
		FishSlotmachineDeploy fishSlotmachineDeploy = dbDao.createExpressionMap().eq("type", type)
				.get(FishSlotmachineDeploy.class);
		return dbDao.createExpressionMap().eq("deployId", fishSlotmachineDeploy.getId()).find(FishSlotMachine.class);
	}

	@Override
	public List<FishSlotMachine> findFishSlotMachineList(String deployId) {
		if (StringUtils.isBlank(deployId)) {
			throw ParamException.param_not_exception;
		}
		return dbDao.createExpressionMap().eq("deployId", deployId).order("randomValue").find(FishSlotMachine.class);
	}

}
