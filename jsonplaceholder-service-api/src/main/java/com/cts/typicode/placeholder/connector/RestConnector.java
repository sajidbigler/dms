package com.cts.typicode.placeholder.connector;

import java.net.URI;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RestConnector {
	
	@Autowired
	private RestTemplate restTemplate;
		
	@SuppressWarnings("unchecked")
	public <T> T postForEntity(String serviceUrl, Object request, Class<T> clazz) {
		ResponseEntity<?> response = restTemplate.postForEntity(serviceUrl, request, clazz);
		if (Objects.nonNull(response) && response.getStatusCode() == HttpStatus.CREATED) {
			log.info("Post/Comment created successfully");
			return (T) response.getBody();
		}else {
			throw new RuntimeException("Post/Comment creation failed");
		}
	}
	
	@SuppressWarnings("unchecked")
	@Retryable(value = {Exception.class}, maxAttempts = 3, backoff = @Backoff(200))
	public <T> T getForEntity(URI serviceUrl, Class<T> clazz) {
		ResponseEntity<?> response = restTemplate.getForEntity(serviceUrl, clazz);
		if (Objects.nonNull(response) && response.getStatusCode() == HttpStatus.OK) {
			log.info("Post/Comments fetched successfully");
			return (T) response.getBody();
		}
		return null;
	}

}
