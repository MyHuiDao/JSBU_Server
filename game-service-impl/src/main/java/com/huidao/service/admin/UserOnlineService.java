package com.huidao.service.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.huidao.common.entity.UserOnline;
import com.huidao.common.interfaces.admin.IUserOnlineService;
import com.yehebl.orm.DBDao;
import com.yehebl.orm.data.common.xml.QueryXmlSql;

/**
 * 统计用户在线数据
 * 
 * @author lenovo
 *
 */
@Component
@Service
public class UserOnlineService implements IUserOnlineService {
	
	private static final Logger log=LoggerFactory.getLogger(UserOnlineService.class);
	@Autowired
	private DBDao dbDao;

	/**
	 * 统计用户在线数据
	 */
	@Override
	public List<UserOnline> statisticsUserOnline(String startDate, String endDate) {
		// 1.获取当前时间
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 2.判断时间参数是否为空
		if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			try {
				// 计算开始时间和结束时间的天数差
				List<UserOnline> list = new ArrayList<UserOnline>();
				Date date1 = sdf.parse(startDate);
				Date date2 = sdf.parse(endDate);
				int day = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
				calendar.setTime(date1);
				QueryXmlSql qXmlSql = new QueryXmlSql();
				for (int j = 0; j < day; j++) {
					calendar.add(Calendar.DATE, 1);
					qXmlSql.addParams("startDate", sdf.format(calendar.getTime()));
					qXmlSql.addParams("endDate", sdf.format(calendar.getTime()));
					UserOnline userOnline = dbDao.getBySqlName("getPeriodData", qXmlSql, UserOnline.class);
					userOnline.setTime(sdf.format(calendar.getTime()));
					list.add(userOnline);
				}
				return list;
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		String time = sdf.format(calendar.getTime());
		// 3.封装成sql语句
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("select '" + time + " 00:00:00" + "' time ");
		for (int i = 1; i < 24; i++) {
			stringBuffer.append("union select '" + time + " " + i + ":00:00" + "' time ");
		}
		QueryXmlSql queryXmlSql = new QueryXmlSql();
		queryXmlSql.addAppend("unionSql", stringBuffer.toString());
		return dbDao.findBySqlName("statisticsUserOnline", queryXmlSql, UserOnline.class);
	}

}
