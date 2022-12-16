package com.coviddata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.coviddata.util.ApiReaderUtility;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.logging.Logger;

/**
 * The type Covid data application.
 */
@SpringBootApplication
@EnableScheduling
public class CovidDataApplication {

	/**
	 * The Logger.
	 */
// Create a Logger with class name GFG
	Logger logger
			= Logger.getLogger(CovidDataApplication.class.getName());

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(CovidDataApplication.class, args);
	}

	/**
	 * Do something after startup.
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		ApiReaderUtility.readCSV();
	}

	/**
	 * Saving csv job.
	 *
	 * @throws InterruptedException the interrupted exception
	 */
	@Scheduled(fixedRate = 28800000)
	public void savingCsvJob() throws InterruptedException{
		logger.info("Job current time is " + new Date());
		ApiReaderUtility.saveCSV();
	}

}
