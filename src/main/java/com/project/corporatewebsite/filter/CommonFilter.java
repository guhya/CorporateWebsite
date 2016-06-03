package com.project.corporatewebsite.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

public class CommonFilter extends GenericFilterBean {
	
	private static final Logger logger = LoggerFactory.getLogger(CommonFilter.class);

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		
		HttpServletRequest request 		= (HttpServletRequest) servletRequest;
		HttpServletResponse response 	= (HttpServletResponse) servletResponse;
		
		String userIp 		= request.getRemoteAddr();
		String uri 			= request.getRequestURI();
		String[] uris		= uri.split("\\/");
		boolean isResource	= uris.length > 0 && "resources".equals(uris[1]);
		boolean isNoFilter	= uris.length > 0 && "ipBlocked".equals(uris[1]);
		
		logger.info("Executing custom filter before Spring Filter Chain " + userIp + " " + request.getRequestURI());
		filterChain.doFilter(request, response);

		/*		
		 * 
		if(!isNoFilter && !"61.250.176.96".equals(userIp)){
			response.sendRedirect("http://61.250.176.96:8081/ipBlocked");
		}else{
			filterChain.doFilter(request, response);
		}
 		*/
	}

}
