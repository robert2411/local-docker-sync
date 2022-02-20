package com.github.robert2411.localdockersync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class LocalDockerSyncApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocalDockerSyncApplication.class, args);
	}

}
