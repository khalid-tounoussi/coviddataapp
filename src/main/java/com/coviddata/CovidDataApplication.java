package com.coviddata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.coviddata.util.ApiReaderUtility;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class CovidDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(CovidDataApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		ApiReaderUtility.readCSV();
	}

}
