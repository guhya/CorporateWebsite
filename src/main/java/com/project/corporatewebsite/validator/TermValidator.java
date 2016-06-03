package com.project.corporatewebsite.validator;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import com.project.corporatewebsite.admin.service.TermService;
import com.project.corporatewebsite.admin.service.implementation.TermServiceImpl;

@Component
public class TermValidator implements Validator {
	
	private static final Logger logger = LoggerFactory.getLogger(TermServiceImpl.class);

	@Autowired
	TermService termService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {		
		ValidationUtils.rejectIfEmpty(errors, "name", "name", "Name is required");
	}
	
	public void validateWithFile(Object target, List<Map<String, MultipartFile>> fileList, Errors errors, String mode) {
		validate(target, errors);	
	}
	
}
