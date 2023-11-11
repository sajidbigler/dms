package com.cts.typicode.placeholder.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;

@Configuration
@PropertySource(ignoreResourceNotFound = true, value = {"classpath:/${spring.profiles.active}/dmsauth-application.properties" })
@PropertySource(ignoreResourceNotFound = true, value = { "classpath:/dmsauth-application.properties" })
@ConfigurationProperties(prefix="placeholder")
@Getter
public class PropertiesConfig {

	private Map<String, String> baseUrlsMap = new HashMap<>();
	
}
