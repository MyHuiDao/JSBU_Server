package com.huidao.service.fish.slot.machine;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.huidao.common.entity.FishMagnificationDeploy;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.fish.slot.machine.IFishMagnificationDeployService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.DBDao;
import com.yehebl.orm.data.common.dto.Page;

@Service
@Component
public class FishMagnificationDeployService implements IFishMagnificationDeployService {

	@Autowired
	private DBDao dbDao;

	@PostConstruct
	public void InitBinder() {
		initFishMagnificationDeploy("1", "大鲸鱼", 50);
		initFishMagnificationDeploy("2", "大乌贼", 25);
		initFishMagnificationDeploy("3", "小蓝鱼", 15);
		initFishMagnificationDeploy("4", "大眼鱼", 10);
		initFishMagnificationDeploy("5", "尖牙鱼", 8);
		initFishMagnificationDeploy("6", "小绿鱼", 5);
		initFishMagnificationDeploy("7", "小红鱼", 3);
		initFishMagnificationDeploy("8", "小丑鱼", 1);
		initFishMagnificationDeploy("9", "白羊驼", 0);
		initFishMagnificationDeploy("10", "粉羊驼", 0);

	}

	@Transactional
	public void initFishMagnificationDeploy(String id, String fishName, Integer mulytiple) {
		FishMagnificationDeploy fishMagnificationDeploy = dbDao.createExpressionMap().eq("fishName", fishName)
				.get(FishMagnificationDeploy.class);
		if (fishMagnificationDeploy == null) {
			FishMagnificationDeploy fishMagnificationDeploy2 = new FishMagnificationDeploy();
			fishMagnificationDeploy2.setId(id);
			fishMagnificationDeploy2.setFishName(fishName);
			fishMagnificationDeploy2.setMultiple(mulytiple);
			fishMagnificationDeploy2.setCreateDate(new Date());
			dbDao.save(fishMagnificationDeploy2);
		}
	}

	@Override
	public MsgDto<Page<FishMagnificationDeploy>> findFishMagnificationDepoyAll(Integer page, Integer size) {
		Map<String, Object> parms = new HashMap<String, Object>();
		return MsgFactory.success(dbDao.findPageByMap(page, size, parms, FishMagnificationDeploy.class));
	}

	@Override
	@Transactional
	public MsgDto<Integer> add(FishMagnificationDeploy fishMagnificationDeploy) {
		if (fishMagnificationDeploy == null) {
			throw ParamException.param_not_exception;
		}
		return MsgFactory.success(dbDao.save(fishMagnificationDeploy));
	}

	@Override
	@Transactional
	public MsgDto<Integer> update(FishMagnificationDeploy fishMagnificationDeploy) {
		if (fishMagnificationDeploy == null) {
			throw ParamException.param_not_exception;
		}
		FishMagnificationDeploy fishMagnificationDeploy2 = dbDao.get(fishMagnificationDeploy.getId(),
				FishMagnificationDeploy.class);
		fishMagnificationDeploy2.setMultiple(fishMagnificationDeploy.getMultiple());
		return MsgFactory.success(dbDao.update(fishMagnificationDeploy2));
	}

	@Override
	@Transactional
	public MsgDto<Integer> delete(String id) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		return MsgFactory.success(dbDao.deleteById(id, FishMagnificationDeploy.class));
	}

	@Override
	public MsgDto<FishMagnificationDeploy> getFishMagnificationDeploy(String id) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		return MsgFactory.success(dbDao.get(id, FishMagnificationDeploy.class));
	}

	@Override
	public MsgDto<List<FishMagnificationDeploy>> findAll() {

		return MsgFactory.success(dbDao.findAll(FishMagnificationDeploy.class));
	}

}
