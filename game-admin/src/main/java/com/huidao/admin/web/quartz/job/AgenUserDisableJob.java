package com.huidao.admin.web.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.huidao.common.spring.SpringBeanUtil;

public class AgenUserDisableJob implements Job {


	private static AgenUserServiceImpl ausi;

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.err.println(">>>>>>>>>>>>>>>>>>>>>>执行了任务");
		if(ausi==null) {
			ausi=(AgenUserServiceImpl) SpringBeanUtil.getBean(AgenUserServiceImpl.class);
		}
		ausi.disqualification();
	}

}
