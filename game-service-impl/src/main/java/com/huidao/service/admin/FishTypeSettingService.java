package com.huidao.service.admin;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.huidao.common.cache.manager.CacheContants;
import com.huidao.common.cache.redis.RedisCache;
import com.huidao.common.entity.FishTypeSetting;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.admin.IFishTypeSettingService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.DBDao;
import com.yehebl.orm.data.common.dto.Page;

@Component
@Service
public class FishTypeSettingService implements IFishTypeSettingService {

	@Autowired
	private DBDao dbDao;

	@PostConstruct
	public void init() {
		initFishTypeSetting(1, "绿河豚", 0, 0);
		initFishTypeSetting(2, "小丑鱼", 7, 9);
		initFishTypeSetting(3, "小刺豚", 7, 9);
		initFishTypeSetting(4, "红海龟", 0, 0);
		initFishTypeSetting(5, "灯笼鱼", 6, 7);
		initFishTypeSetting(6, "石斑鱼", 6, 7);
		initFishTypeSetting(7, "小蓝鱼", 5, 6);
		initFishTypeSetting(8, "蝴蝶鱼", 5, 6);
		initFishTypeSetting(9, "神仙鱼", 5, 6);
		initFishTypeSetting(10, "孔雀鱼", 4, 5);
		initFishTypeSetting(11, "绿海龟", 4, 4);
		initFishTypeSetting(12, "大灯笼鱼", 4, 4);
		initFishTypeSetting(13, "大泥鳅", 3, 3);
		initFishTypeSetting(14, "蓝旗鱼", 3, 3);
		initFishTypeSetting(15, "红箭鱼", 3, 3);
		initFishTypeSetting(16, "小海豹", 2, 2);
		initFishTypeSetting(17, "魔鬼鱼", 2, 2);
		initFishTypeSetting(18, "爱心魔鬼鱼", 2, 2);
		initFishTypeSetting(19, "鲨鱼", 1, 1);
		initFishTypeSetting(20, "金鲨鱼", 1, 1);
	}

	@Transactional
	public void initFishTypeSetting(Integer level, String name, Integer initCount, Integer maxCount) {
		FishTypeSetting fts = dbDao.getByExpression("EQ_level", level, FishTypeSetting.class);
		if (fts == null) {
			FishTypeSetting fishTypeSetting = new FishTypeSetting();
			fishTypeSetting.setLevel(level);
			fishTypeSetting.setInitCount(initCount);
			fishTypeSetting.setMaxCount(maxCount);
			fishTypeSetting.setName(name);
			dbDao.save(fishTypeSetting);
		}
	}

	@Override
	public MsgDto<Page<FishTypeSetting>> findFishTypeSettingAll(Integer level, Integer page, Integer size) {
		return MsgFactory.success(dbDao.createExpressionMap().eq("level", level).limit(page, size).order("level")
				.findPage(FishTypeSetting.class));
	}

	@Override
	@Transactional
	public MsgDto<Integer> add(FishTypeSetting fishTypeSetting) {
		if (fishTypeSetting == null) {
			throw ParamException.param_not_exception;
		}
		return MsgFactory.success(dbDao.save(fishTypeSetting));
	}

	@Override
	@Transactional
	public MsgDto<Integer> delete(String id) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		return MsgFactory.success(dbDao.deleteById(id, FishTypeSetting.class));
	}

	@Override
	@Transactional
	public MsgDto<Integer> update(FishTypeSetting fishTypeSetting) {
		if (fishTypeSetting == null) {
			throw ParamException.param_not_exception;
		}
		RedisCache.remove(CacheContants.fish_type_setting_lists);
		return MsgFactory.success(dbDao.update(fishTypeSetting));
	}

	@Override
	public MsgDto<FishTypeSetting> getFishTypeSetting(String id) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		return MsgFactory.success(dbDao.get(id, FishTypeSetting.class));
	}

	@Override
	public List<FishTypeSetting> getFishTypeSetting() {
		String str = RedisCache.get(CacheContants.fish_type_setting_lists);
		if (StringUtils.isNotBlank(str)) {
			return JSONObject.parseArray(str, FishTypeSetting.class);
		}
		List<FishTypeSetting> lists = dbDao.createExpressionMap().order("level").find(FishTypeSetting.class);
		RedisCache.set(CacheContants.fish_type_setting_lists, lists);
		return lists;
	}

}
