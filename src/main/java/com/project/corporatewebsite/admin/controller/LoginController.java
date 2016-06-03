package com.project.corporatewebsite.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@RequestMapping(value = "/admin/login")
	public String login(Model model, @RequestParam(value = "error", required = false) String error) 
	{
		logger.info("Login");
		
		if("1".equals(error)){
			model.addAttribute("error", true);
			logger.info(error);
		}
		
		return "/admin/login/login";
	}
	
}
