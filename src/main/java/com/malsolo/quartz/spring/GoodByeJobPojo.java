package com.malsolo.quartz.spring;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GoodByeJobPojo {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public void doIt() {
		logger.info("Good Bye World! - " + new Date());
	}

}
