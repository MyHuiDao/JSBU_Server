package com.huidao.service.admin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.huidao.common.entity.User;
import com.huidao.common.interfaces.admin.IStatisticsService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.DBDao;
import com.yehebl.orm.data.common.dto.Page;
import com.yehebl.orm.data.common.xml.QueryXmlSql;

/**
 * 数据统计业务层
 * 
 * @author lenovo
 *
 */

@Component
@Service
public class StatisticsService implements IStatisticsService {

	private static final Logger log = LoggerFactory.getLogger(StatisticsService.class);

	@Autowired
	private DBDao dbDao;

	@Override
	public MsgDto<Map<String, List<User>>> countUserRegistData(String startTime, String endTime) {
		Map<String, List<User>> result = new HashMap<>();

		// 1.获取当前时间，并得到七天的日期
		// 创建日期对象1
		Calendar calendar = Calendar.getInstance();
		// 设置日期格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 创建QueryXmlSql对象
		QueryXmlSql queryXmlSql = new QueryXmlSql();
		// 创建List集合，用于保存7天的用户注册数据
		List<User> lists = new ArrayList<User>();

		// 创建List集合，用于保存7天的微信用户注册数据
		List<User> lists1 = new ArrayList<User>();
		// 当指定时间段不为空时
		long day = 15;
		if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
			try {
				calendar.setTime(sdf.parse(endTime));
				day = (sdf.parse(endTime).getTime() - sdf.parse(startTime).getTime()) / 1000 / 60 / 60 / 24 + 1;
			} catch (ParseException e) {
				log.error(e.getMessage(), e);
			}
		}
		// 计算出的时间
		// 计算七天的值，并且保存返回来的值
		for (int i = 0; i < day; i++) {
			String date = sdf.format(calendar.getTime());
			queryXmlSql.addParams("date", date);
			User user = dbDao.getBySqlName("countUserRegistData", queryXmlSql, User.class);
			user.setDate(date);
			lists.add(user);
			User user1 = dbDao.getBySqlName("countUserRegistData1", queryXmlSql, User.class);
			user1.setDate(date);
			lists1.add(user1);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
		}

		// 按时间排序
		Collections.sort(lists, new Comparator<User>() {
			@Override
			public int compare(User arg0, User arg1) {
				int flag = arg0.getDate().compareTo(arg1.getDate());
				return flag;
			}
		});
		Collections.sort(lists1, new Comparator<User>() {
			@Override
			public int compare(User arg0, User arg1) {
				int flag = arg0.getDate().compareTo(arg1.getDate());
				return flag;
			}
		});
		result.put("sum", lists);
		result.put("weixin", lists1);
		return MsgFactory.success(result);
	}

	@Override
	public MsgDto<Page<User>> findUserOnlineDuration(Integer page, Integer size, String code) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return MsgFactory.success(dbDao.createQueryXmlSql("findUserOnlineDuration")
				.addParams("currentDate", sdf.format(new Date().getTime())).addParams("code", code).limit(page, size)
				.findPage(User.class));
	}

	@Override
	public MsgDto<Page<User>> findUserRegistData(Integer page, Integer size, String code) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, -7);
		Date date = calendar.getTime();
		String createTime = sdf.format(date);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("GEQ_createTime", createTime);
		map.put("ORDER_createTime", "desc");
		if (StringUtils.isNotBlank(code)) {
			map.put("EQ_code", code);
		}
		return MsgFactory.success(dbDao.findPageByMap(page, size, map, User.class));
	}

}
