package com.project.corporatewebsite.interceptor;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.project.corporatewebsite.admin.service.UserService;
import com.project.corporatewebsite.vo.UserVo;

@Component
public class CommonInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserVo userVo;
	
	private static final Logger logger = LoggerFactory.getLogger(CommonInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String uri 			= request.getRequestURI();
		String[] uris		= uri.split("\\/");
		boolean isResource	= uris.length > 0 && "resources".equals(uris[1]);
		
		logger.info(Arrays.deepToString(uris));
		
		if(!isResource){
			logger.info("Executing interceptor to set page title : " + uri);
			
			/*
			userVo.setUsername("Avril");
			@SuppressWarnings("rawtypes")
			Map result = userService.select(userVo);
			logger.info("Is Avril here ? " + result.get("lastName").toString());
			*/
			
			request.setAttribute("pageTitle", "Spring Application");
		}
		
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterCompletion(request, response, handler, ex);
	}
	

}
