package com.malsolo.quartz.spring;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class GoodByeJob extends QuartzJobBean {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {

		logger.info("Good Bye World! - " + new Date());

	}
	
}
