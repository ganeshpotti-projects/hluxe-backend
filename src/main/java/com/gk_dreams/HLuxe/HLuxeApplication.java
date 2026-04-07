package com.gk_dreams.HLuxe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HLuxeApplication {

	public static void main(String[] args) {
		SpringApplication.run(HLuxeApplication.class, args);
	}

}
