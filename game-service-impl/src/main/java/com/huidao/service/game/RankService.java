package com.huidao.service.game;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.huidao.common.dto.RankDto;
import com.huidao.common.interfaces.game.IRankService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.DBDao;
import com.yehebl.orm.data.common.xml.QueryXmlSql;

@Service
@Component
public class RankService implements IRankService {

	@Autowired
	private DBDao dbDao;

	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public MsgDto<List<RankDto>> findRankActiveByDate(Integer dateType) {
		Date d = new Date();
		String endStr = null;
		String startStr = null;
		if (dateType!=null&&dateType == 1) {
			endStr = sdf.format(d);
			d = new Date(d.getTime() - 24  * 60 * 60 * 1000);
			startStr = sdf.format(d);
		} else {
			startStr = sdf.format(d);
			d = new Date(d.getTime() + 24  * 60 * 60 * 1000);
			endStr= sdf.format(d);
		}
		List<RankDto> list = dbDao.findBySqlName("findRankActiveByDate", QueryXmlSql.createQuery()
				.addParams("startTime", startStr).addParams("endTime", endStr).addParams("currentTime", new Date()),
				RankDto.class);
		return MsgFactory.success(list);
	}

	@Override
	public MsgDto<List<RankDto>> findRankPayByDate(Integer dateType) {
		Date d = new Date();
		String endStr = null;
		String startStr = null;
		if (dateType!=null&&dateType == 1) {
			endStr = sdf.format(d);
			d = new Date(d.getTime() - 24  * 60 * 60 * 1000);
			startStr = sdf.format(d);
		} else {
			startStr = sdf.format(d);
			d = new Date(d.getTime() + 24  * 60 * 60 * 1000);
			endStr = sdf.format(d);
		}
		List<RankDto> list = dbDao.findBySqlName("findRankPayByDate",
				QueryXmlSql.createQuery().addParams("startTime", startStr).addParams("endTime", endStr), RankDto.class);
		return MsgFactory.success(list);
	}

}
