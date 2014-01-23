package com.malsolo.quartz.main;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.malsolo.quartz.spring.GoodByeJob;

@Configuration
@PropertySource("classpath:config.properties")
@ImportResource({"classpath:META-INF/spring/applicationContext-quartz.xml"})
public class SpringQuartzMain {
	
	private static final long TIEMPO_ESPERA_POR_QUARTZ = TimeUnit.SECONDS.toMillis(15);

	final static Logger logger = LoggerFactory.getLogger(SpringQuartzMain.class);
	
	@Autowired
	private Environment environment;
	
	@Bean
	public String message() {
		return environment.getProperty("config.message");
	}
	
	public static void main(String... args) {
		
		logger.info("##### BEGIN #####");
		
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(SpringQuartzMain.class);
		ctx.scan(GoodByeJob.class.getPackage().toString(), SpringQuartzMain.class.getPackage().toString());
		ctx.refresh();
		
		String message = ctx.getBean("message", String.class);
		logger.info("Message: {}", message);
		
		SpringQuartzMain springQuartzMain = ctx.getBean(SpringQuartzMain.class);
		
		springQuartzMain.waitingForQuartz();
		
		ctx.close();
		
		logger.info("##### END #####");
	}
	
	public void waitingForQuartz() {
		try {
			logger.warn("A esperar {} ms ", TIEMPO_ESPERA_POR_QUARTZ);
			Thread.sleep(TIEMPO_ESPERA_POR_QUARTZ);
			logger.warn("Wake up!");
		} catch (InterruptedException e) {
			logger.warn("Interrupted!");
			Thread.currentThread().interrupt();
		}
		
	}

}
