package com.project.corporatewebsite.admin.service.implementation;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.corporatewebsite.admin.service.CommonService;
import com.project.corporatewebsite.common.Util;

@Component
public class CommonServiceIimpl implements CommonService{
	
	private Logger logger = LoggerFactory.getLogger(CommonServiceIimpl.class);
	
	@Override
	public boolean verifyCaptcha(String response, String remoteIp) {
		logger.info(
			"Verifying " + Util.loadProperties("google.recaptcha.secret") 
			+ ", to " + Util.loadProperties("google.recaptcha.verifyUrl")
			+ ", by " + remoteIp
		);
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("secret", Util.loadProperties("google.recaptcha.secret"));
		map.add("response", response);
		map.add("remoteip", remoteIp);
		
		RestTemplate restTemplate = new RestTemplate();
		String jsonResponse = restTemplate.postForObject(Util.loadProperties("google.recaptcha.verifyUrl"), map, String.class);
		
		Map<String, Object> jsonMap = readJsonString(jsonResponse); 		
		String success = jsonMap.get("success").toString();		
		
		logger.info("ReCaptcha verification : " + success);
		return "true".equals(success);
		
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, Object> readJsonString(String jsonResponse){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> jsonMap = new HashMap<String, Object>(); 
		try {
			jsonMap = mapper.readValue(jsonResponse, Map.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jsonMap;
	}
	

	

}
