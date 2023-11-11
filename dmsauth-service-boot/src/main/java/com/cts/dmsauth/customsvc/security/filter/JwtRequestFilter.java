package com.cts.dmsauth.customsvc.security.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cts.customsvc.base.constants.BaseConstants;
import com.cts.dmsauth.customsvc.security.service.JwtUserDetailsService;
import com.cts.dmsauth.customsvc.util.JwtTokenUtil;

import io.jsonwebtoken.ExpiredJwtException;


@Component
public class JwtRequestFilter extends OncePerRequestFilter {


	private List<String> excludeUrlPatterns = new ArrayList<String>(Arrays.asList(BaseConstants.AUTH_WHITELIST));

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		try {

			final String requestTokenHeader = request.getHeader("Authorization");

			String username = null;
			String jwtToken = null;
			// JWT Token is in the form "Bearer token". Remove Bearer word and get
			// only the Token
			if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
				jwtToken = requestTokenHeader.substring(7);
				try {
					username = jwtTokenUtil.getUsernameFromToken(jwtToken);
				} catch (IllegalArgumentException e) {
					System.out.println("Unable to get JWT Token");
				} catch (ExpiredJwtException e) {
					System.out.println("JWT Token has expired");
				}
			} else {
				logger.warn("JWT Token does not begin with Bearer String");
			}

			// Once we get the token validate it.
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

				UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

				// if token is valid configure Spring Security to manually set
				// authentication
				if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken
					.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					// After setting the Authentication in the context, we specify
					// that the current user is authenticated. So it passes the
					// Spring Security Configurations successfully.
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
					MDC.put("USERID",username );
					MDC.put("TXNID", UUID.randomUUID().toString());
					MDC.put("SESSID", UUID.randomUUID().toString());
					MDC.put("TRACEID", UUID.randomUUID().toString());

				}
			}
			chain.doFilter(request, response);
		}	finally {
			MDC.remove("USERID");
			MDC.remove("TXNID");
			MDC.remove("SESSID");
			MDC.remove("TRACEID");
		}
	}


	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String path = request.getServletPath();
		if (excludeUrlPatterns.contains(path)) {
			return true;
		} else {
			return false;
		}
	}

}
//https://www.javainuse.com/spring/boot-jwt