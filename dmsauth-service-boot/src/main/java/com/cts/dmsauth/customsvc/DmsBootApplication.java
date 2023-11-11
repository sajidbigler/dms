package com.cts.dmsauth.customsvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@EnableFeignClients(basePackages = { "com.cts.dmsauth.api" })
@ComponentScan(basePackages = {"com.cts.*"})
public class DmsBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(DmsBootApplication.class, args);
	}

}
