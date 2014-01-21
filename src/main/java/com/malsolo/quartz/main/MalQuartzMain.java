package com.malsolo.quartz.main;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.ArrayList;
import java.util.Date;

import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.malsolo.quartz.hello.DumbJob;
import com.malsolo.quartz.hello.HelloJob;

public class MalQuartzMain {

	private static final Logger logger = LoggerFactory.getLogger(MalQuartzMain.class);

	public static void main(String... args) {
		
		try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();

			logger.debug("Let's play with Quarz...");
			//doSomethingInteresting(scheduler);
			doSomethingCronTriggering(scheduler);
			logger.debug("Let's do play with Quartz. Done.");

			scheduler.shutdown();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}

	}
	
	private static void doSomethingInteresting(Scheduler scheduler) throws SchedulerException {

		logger.debug("Let's do something interesting with Quarz...");

		JobDetail job = newJob(HelloJob.class)
				.withIdentity("malJob", "malGroup1")
				.build();

		Trigger trigger = newTrigger().withIdentity("malTrigger", "malGroup1")
				.startNow()
				.withSchedule(simpleSchedule()
						.withIntervalInSeconds(2)
						.repeatForever())            
						.build();

		JobDetail dumbJob = newJob(DumbJob.class)
				.withIdentity("dumbJob", "malGroup1")
				.usingJobData("jobSays", "Hello World!")
				.usingJobData("myFloatValue", 3.141f)
				.build();

		
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put("state", new ArrayList<Date>());
		
		CronTrigger cronTrigger = newTrigger()
				.withIdentity("dumbTrigger", "malGroup1")
				.withSchedule(cronSchedule("0/5 * * * * ?"))
				.usingJobData(jobDataMap)
				.build();

		scheduler.scheduleJob(job, trigger);
		scheduler.scheduleJob(dumbJob, cronTrigger);

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		logger.debug("Let's do something interesting with Quarz. Done.");
	}
	
	private static void doSomethingCronTriggering(Scheduler scheduler) throws SchedulerException {
		
		logger.debug("Let's do some CRON triggering with Quarz...");

		JobDetail job = newJob(HelloJob.class)
				.withIdentity("malJob", "malGroup1")
				.build();

		CronTrigger cronTrigger = newTrigger()
				.withIdentity("dumbTrigger", "malGroup1")
				//.withSchedule(cronSchedule("0/5 * * * * ?"))
				.withSchedule(cronSchedule("0 0/1 * * * ?"))
				.build();

		scheduler.scheduleJob(job, cronTrigger);

		try {
			Thread.sleep(180000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		logger.debug("Let's do some CRON triggering with Quarz. Done.");

	}

		

}
