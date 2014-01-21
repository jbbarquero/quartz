package com.malsolo.quartz.hello;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DumbJob implements Job {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	String jobSays;
	float myFloatValue;
	List<Date> state;

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		
		JobKey key = context.getJobDetail().getKey();

		JobDataMap dataMap = context.getMergedJobDataMap();
		
		DateTime ahora = new DateTime();
		DateTimeFormatter fmt = ISODateTimeFormat.dateTime(); //DateTimeFormat.forPattern("yyyyMMdd");
		
		state.add(ahora.toDate());
		
		logger.info("[{}] Instance {} of DumbJob says: {} , and val is: {} ({})", ahora.toString(fmt), key, jobSays, myFloatValue, dataMap.getFloat("myFloatValue"));

	}

	public void setJobSays(String jobSays) {
		this.jobSays = jobSays;
	}

	public void setMyFloatValue(float myFloatValue) {
		this.myFloatValue = myFloatValue;
	}

	public void setState(List<Date> state) {
		this.state = state;
	}

}
