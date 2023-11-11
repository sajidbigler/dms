package com.cts.customsvc.base.constants;

public class BaseConstants  {

	public static final String STATUS_FAILED = "500";
	
	public static final String STATUS_DOC_DELETED = "Document deleted successfully";
	
	public static final String[] AUTH_WHITELIST = {
	        // -- Swagger UI v2
	        "/v2/api-docs",
	        "/swagger-resources",
	        "/swagger-resources/**",
	        "/configuration/ui",
	        "/configuration/security",
	        "/swagger-ui.html",
	        "/webjars/**",
	        // -- Swagger UI v3 (OpenAPI)
	        "/v3/api-docs/**",
	        "/swagger-ui/**",
	        // other public endpoints
	        "/h2-console/**",
	        "/h2/**",
	        "/authenticate", "/register"
	 };
}
