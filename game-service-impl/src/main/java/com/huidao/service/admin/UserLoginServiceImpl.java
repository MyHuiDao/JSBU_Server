package com.huidao.service.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.huidao.common.entity.UserLogin;
import com.huidao.common.interfaces.admin.IUserLoginService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.DBDao;
import com.yehebl.orm.data.common.dto.Page;
import com.yehebl.orm.data.common.xml.QueryXmlSql;

@Component
@Service
public class UserLoginServiceImpl implements IUserLoginService {

	@Autowired
	private DBDao dbDao;

	/**
	 * 获取用户十五天的登录数据
	 */
	@Override
	public MsgDto<Map<String, List<UserLogin>>> getUserLoginAllData(Date startDate, Date endDate) {
		QueryXmlSql queryXmlSql = new QueryXmlSql();
		if (endDate == null) {
			endDate = new Date();
		}
		if (startDate == null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(endDate);
			calendar.add(Calendar.DATE, -14);
			startDate = calendar.getTime();
		}
		long day = (endDate.getTime() - startDate.getTime()) / 1000 / 60 / 60 / 24 + 1;

		queryXmlSql.addParams("startDate", startDate);
		queryXmlSql.addParams("endDate", endDate);
		List<UserLogin> list = dbDao.findBySqlName("getUserLoginAllData", queryXmlSql, UserLogin.class);
		List<UserLogin> list1 = dbDao.findBySqlName("getUserLoginAllData1", queryXmlSql, UserLogin.class);
		Map<String, List<UserLogin>> result = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<UserLogin> newList = new ArrayList<>();
		List<UserLogin> newList1 = new ArrayList<>();
		for (int i = 0; i < day; i++) {
			boolean flag = false;
			for (UserLogin ul : list) {
				if (ul.getTime().equals(sdf.format(startDate))) {
					newList.add(ul);
					flag = true;
					break;
				}
			}
			if (!flag) {
				UserLogin ul = new UserLogin();
				ul.setTime(sdf.format(startDate));
				ul.setCount(0);
				newList.add(ul);
			}

			boolean flag1 = false;
			for (UserLogin ul : list1) {
				if (ul.getTime().equals(sdf.format(startDate))) {
					newList1.add(ul);
					flag1 = true;
					break;
				}
			}
			if (!flag1) {
				UserLogin ul = new UserLogin();
				ul.setTime(sdf.format(startDate));
				ul.setCount(0);
				newList1.add(ul);
			}
			startDate.setTime(startDate.getTime() + (24 * 60 * 60 * 1000));
		}

		result.put("sum", newList);
		result.put("noRepeat", newList1);
		return MsgFactory.success(result);
	}

	/**
	 * 获取指定日期用户登录数据
	 */
	@Override
	public List<UserLogin> getSpecifiedData(String date) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_loginTime", date);
		return dbDao.findByMap(map, UserLogin.class);
	}

	/**
	 * 用户登录数据
	 */
	@Override
	public MsgDto<Page<UserLogin>> findUserLoginAll(Integer page, Integer size, Date startDate, Date endDate) {
		if (endDate == null) {
			endDate = new Date();
		}
		if (startDate == null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(endDate);
			calendar.add(Calendar.DATE, -14);
			startDate = calendar.getTime();
		}
		QueryXmlSql queryXmlSql = new QueryXmlSql();
		// 大于等于
		queryXmlSql.addParams("startDate", startDate);
		// 小于等于
		queryXmlSql.addParams("endDate", endDate);
		return MsgFactory
				.success(dbDao.findPageBySqlName("findUserLoginAll", queryXmlSql, page, size, UserLogin.class));
	}

}
