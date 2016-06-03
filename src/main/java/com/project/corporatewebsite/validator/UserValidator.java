package com.project.corporatewebsite.validator;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import com.project.corporatewebsite.admin.service.UserService;
import com.project.corporatewebsite.admin.service.implementation.UserServiceImpl;
import com.project.corporatewebsite.common.Constants;
import com.project.corporatewebsite.common.Util;
import com.project.corporatewebsite.vo.UserVo;

@Component
public class UserValidator implements Validator {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	UserService userService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}
	
	private String mode;

	@Override
	public void validate(Object target, Errors errors) {		
		ValidationUtils.rejectIfEmpty(errors, "username", "username", "Username is required");
		ValidationUtils.rejectIfEmpty(errors, "password", "password", "Password is required");

		if(!Constants.WRITE.equals(mode)){
			UserVo userVo = (UserVo) target;
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String rawPassword = userVo.getOldPassword();
					
			Map<String, String> userMap = userService.select(userVo);
			String oldHashedPassword = userMap.get("password");
			
			if(!passwordEncoder.matches(rawPassword, oldHashedPassword)){
				errors.reject("oldPassword", "Old password does not match");
			}
		}
	}
	
	public void validateWithFile(Object target, List<Map<String, MultipartFile>> fileList, Errors errors, String mode) {
		this.mode = mode;
		validate(target, errors);
		
		String key = "";
		boolean passThumbnail = false, passMainImage = false, passAttachment = false;
		
		for(Map<String, MultipartFile> fileMap : fileList){
			for(Entry<String, MultipartFile> file : fileMap.entrySet()){
				key = file.getKey();
				if(Constants.THUMBNAIL_IMAGE.equalsIgnoreCase(key)){
					String mimeType = file.getValue().getContentType();
					if(!Util.validateMime(mimeType, Constants.IMAGES_MIME_TYPE)){
						errors.reject(Constants.THUMBNAIL_IMAGE, "Only (GIF, JPG, PNG)");
					}else{
						passThumbnail = true;
					}
				
				}else if(Constants.MAIN_IMAGE.equalsIgnoreCase(key)){
					String mimeType = file.getValue().getContentType();
					if(!Util.validateMime(mimeType, Constants.IMAGES_MIME_TYPE)){
						errors.reject(Constants.MAIN_IMAGE, "Only (GIF, JPG, PNG)");
					}else{
						passMainImage = true;
					}
					
				}else if(Constants.ATTACHMENT.equalsIgnoreCase(key)){					
					String mimeType = file.getValue().getContentType();
					if(!Util.validateMime(mimeType, Constants.FILES_MIME_TYPE)){
						errors.reject(Constants.ATTACHMENT, "Only (PDF, ZIP, RAR)");
					}else{
						passAttachment = true;
					}
					
				}
			}
		}
		
		if(Constants.WRITE.equals(mode)){
		}

		
	}
	
}
