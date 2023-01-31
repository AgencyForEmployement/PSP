package com.agency.psp;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class PspApplication {

	final static Logger log = Logger.getLogger(PspApplication.class.getName());

	public static void main(String[] args) {
		SpringApplication.run(PspApplication.class, args);
		log.info("Hello from Psp Application! Welcome back!");
	}

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
