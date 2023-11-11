package com.cts.customsvc.base.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestResponseLoggingInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		long startTime = System.currentTimeMillis();
		request.setAttribute("startTime", startTime);
		log.info("====================Request Begins========================");
		log.info("URI     : {}", request.getRequestURI());
		log.info("Method  : {}", request.getMethod());
		log.info("====================Request Ends========================");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		log.info("===============Sent to Handler=====================");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		long startTime = (Long) request.getAttribute("startTime");
		log.info("====================Response Begins========================");
		log.info("Status Code       : {}", response.getStatus());
		log.info("Total Time(msecs) : {}", System.currentTimeMillis() - startTime );
		log.info("====================Response Ends========================");
	}

}
