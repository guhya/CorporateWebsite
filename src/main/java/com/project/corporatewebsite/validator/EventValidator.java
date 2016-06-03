package com.project.corporatewebsite.validator;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import com.project.corporatewebsite.admin.service.EventService;
import com.project.corporatewebsite.admin.service.implementation.EventServiceImpl;
import com.project.corporatewebsite.common.Constants;
import com.project.corporatewebsite.common.Util;

@Component
public class EventValidator implements Validator {
	
	private static final Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);

	@Autowired
	EventService eventService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {		
		ValidationUtils.rejectIfEmpty(errors, "title", "title", "Title is required");
		ValidationUtils.rejectIfEmpty(errors, "content", "content", "Content is required");
	}
	
	public void validateWithFile(Object target, List<Map<String, MultipartFile>> fileList, Errors errors, String mode) {
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
