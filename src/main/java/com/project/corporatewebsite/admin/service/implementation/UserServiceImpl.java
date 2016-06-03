package com.project.corporatewebsite.admin.service.implementation;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.project.corporatewebsite.admin.dao.FileDAO;
import com.project.corporatewebsite.admin.dao.UserDAO;
import com.project.corporatewebsite.admin.service.UserService;
import com.project.corporatewebsite.common.Constants;
import com.project.corporatewebsite.common.Util;
import com.project.corporatewebsite.common.VoHelper;
import com.project.corporatewebsite.vo.FileVo;
import com.project.corporatewebsite.vo.UserVo;

@Component
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private FileDAO fileDao;
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Override
	public int insert(UserVo userVo, List<Map<String, MultipartFile>> fileList) {
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(userVo.getPassword());
		
		logger.info("Bcrypt hashed password : " + hashedPassword);
		userVo.setPassword(hashedPassword);
		
		int r = userDao.insert(userVo);
		
		for(Map<String, MultipartFile> fileMap : fileList){
			for(Entry<String, MultipartFile> file : fileMap.entrySet()){
				FileVo fileVo = VoHelper.prepareFileVo(	userVo.getRegIp(), 
														userVo.getRegId(), 
														file.getValue(), 
														Constants.USER, 
														file.getKey(), 
														Constants.UPLOAD_USER_PATH
														);
				fileVo.setOwnerSeq(String.valueOf(r));
				if(r > 0){
					if(!"".equals(file.getValue().getOriginalFilename())){
						try {
							Util.writeFile(file.getValue(), fileVo.getName(), Constants.WEB_ROOT_PHYSICAL+Constants.UPLOAD_USER_PATH);
							
							r = fileDao.delete(fileVo);
							r = fileDao.insert(fileVo);
							
						} catch (Exception e) {
							logger.info(e.getMessage());
						}
					}
				}
			}
		}
		
		return r;
	}

	@Override
	public int update(UserVo userVo, List<Map<String, MultipartFile>> fileList) {
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(userVo.getPassword());
		
		logger.info("Bcrypt hashed password : " + hashedPassword);
		userVo.setPassword(hashedPassword);
		
		int r = userDao.update(userVo);
		
		for(Map<String, MultipartFile> fileMap : fileList){
			for(Entry<String, MultipartFile> file : fileMap.entrySet()){
				FileVo fileVo = VoHelper.prepareFileVo(	userVo.getModIp(), 
														userVo.getModId(), 
														file.getValue(), 
														Constants.USER, 
														file.getKey(), 
														Constants.UPLOAD_USER_PATH
														);
				fileVo.setOwnerSeq(userVo.getSeq());
				if(r > 0){
					if(!"".equals(file.getValue().getOriginalFilename())){
						try {
							Util.writeFile(file.getValue(), fileVo.getName(), Constants.WEB_ROOT_PHYSICAL+Constants.UPLOAD_USER_PATH);
							
							r = fileDao.delete(fileVo);
							r = fileDao.insert(fileVo);
							
						} catch (Exception e) {
							logger.info(e.getMessage());
						}
					}
				}
			}
		}
		
		return r;
	}
	
	@Override
	public List<Map<String, String>> list(UserVo userVo) {
		return userDao.list(userVo);
	}
	
	@Override
	public int countList(UserVo userVo) {
		return userDao.countList(userVo);
	}

	@Override
	public Map<String, String> select(UserVo userVo) {
		return userDao.select(userVo);
	}

	@Override
	public int delete(UserVo userVo) {
		return userDao.delete(userVo);
	}

}
