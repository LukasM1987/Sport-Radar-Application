package com.event.sportradar;

import com.event.sportradar.Service.AppService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SportRadarApplication {

	public static void main(String[] args) {
		//SpringApplication.run(SportRadarApplication.class, args);
		new AppService().compareProbability(10);
	}
}
