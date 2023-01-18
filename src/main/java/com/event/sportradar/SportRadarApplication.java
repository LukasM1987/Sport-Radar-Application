package com.event.sportradar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude =  {DataSourceAutoConfiguration.class })
public class SportRadarApplication {

	public static void main(String[] args) {
		SpringApplication.run(SportRadarApplication.class, args);
	}
}
