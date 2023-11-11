package com.cts.typicode.placeholder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClient {
	
	private static final Integer connectionTimeOut = 180000;
	
	private static final Integer readTimeOut = 180000;
	
	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(getClientHttpRequestFactory()));
		return restTemplate;
	}
	
	private SimpleClientHttpRequestFactory getClientHttpRequestFactory() {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setConnectTimeout(connectionTimeOut);
		factory.setReadTimeout(readTimeOut);
		return factory;
	}

}
