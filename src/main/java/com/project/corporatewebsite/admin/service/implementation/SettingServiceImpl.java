package com.project.corporatewebsite.admin.service.implementation;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.project.corporatewebsite.admin.dao.FileDAO;
import com.project.corporatewebsite.admin.dao.SettingDAO;
import com.project.corporatewebsite.admin.service.SettingService;
import com.project.corporatewebsite.common.Constants;
import com.project.corporatewebsite.common.Util;
import com.project.corporatewebsite.common.VoHelper;
import com.project.corporatewebsite.vo.FileVo;
import com.project.corporatewebsite.vo.SettingVo;

@Component
public class SettingServiceImpl implements SettingService {
	
	@Autowired
	private SettingDAO settingDao;
	
	@Autowired
	private FileDAO fileDao;
	
	private static final Logger logger = LoggerFactory.getLogger(SettingServiceImpl.class);
	
	@Override
	public int insert(SettingVo settingVo, List<Map<String, MultipartFile>> fileList) {
		
		int r = settingDao.insert(settingVo);
		
		for(Map<String, MultipartFile> fileMap : fileList){
			for(Entry<String, MultipartFile> file : fileMap.entrySet()){
				FileVo fileVo = VoHelper.prepareFileVo(	settingVo.getRegIp(), 
														settingVo.getRegId(), 
														file.getValue(), 
														Constants.SETTING, 
														file.getKey(), 
														Constants.UPLOAD_SETTING_PATH
														);
				fileVo.setOwnerSeq(String.valueOf(r));
				if(r > 0){
					if(!"".equals(file.getValue().getOriginalFilename())){
						try {
							Util.writeFile(file.getValue(), fileVo.getName(), Constants.WEB_ROOT_PHYSICAL+Constants.UPLOAD_SETTING_PATH);
							
							fileDao.delete(fileVo);
							fileDao.insert(fileVo);
							
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
	public int update(SettingVo settingVo, List<Map<String, MultipartFile>> fileList) {
		
		int r = settingDao.update(settingVo);
		
		for(Map<String, MultipartFile> fileMap : fileList){
			for(Entry<String, MultipartFile> file : fileMap.entrySet()){
				FileVo fileVo = VoHelper.prepareFileVo(	settingVo.getModIp(), 
														settingVo.getModId(), 
														file.getValue(), 
														Constants.SETTING, 
														file.getKey(), 
														Constants.UPLOAD_SETTING_PATH
														);
				fileVo.setOwnerSeq(settingVo.getSeq());
				if(r > 0){
					if(!"".equals(file.getValue().getOriginalFilename())){
						try {
							Util.writeFile(file.getValue(), fileVo.getName(), Constants.WEB_ROOT_PHYSICAL+Constants.UPLOAD_SETTING_PATH);
							
							fileDao.delete(fileVo);
							fileDao.insert(fileVo);
							
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
	public List<Map<String, String>> list(SettingVo settingVo) {
		return settingDao.list(settingVo);
	}
	
	@Override
	public int countList(SettingVo settingVo) {
		return settingDao.countList(settingVo);
	}

	@Override
	public Map<String, String> select(SettingVo settingVo) {
		Map<String, String> resultMap =  settingDao.select(settingVo);
		return resultMap;		
	}

	@Override
	public int delete(SettingVo settingVo) {
		return settingDao.delete(settingVo);
	}

	@Override
	public Map<String, String> selectDefaultEvent(SettingVo settingVo) {
		Map<String, String> resultMap =  settingDao.selectDefaultEvent(settingVo);
		return resultMap;		
	}

	@Override
	public Map<String, String> selectCurrentEvent(SettingVo settingVo) {
		Map<String, String> resultMap =  settingDao.selectCurrentEvent(settingVo);
		return resultMap;		
	}

	@Override
	public int clearEvent(SettingVo settingVo) {
		return settingDao.clearEvent(settingVo);
	}

	@Override
	public int setAsEvent(SettingVo settingVo) {
		settingDao.clearEvent(settingVo);
		int rs =  settingDao.setAsEvent(settingVo);
		
		return rs;
	}

	@Override
	public int setAsDefault(SettingVo settingVo) {
		return settingDao.setAsDefault(settingVo);
	}

	@Override
	public int unsetDefault(SettingVo settingVo) {
		return settingDao.unsetDefault(settingVo);
	}

	@Override
	public Map<String, String> getCurrentSetting(SettingVo settingVo) {
		Map<String, String> currentMap 	= settingDao.selectCurrentEvent(settingVo);			
		Map<String, String> defaultMap 	= settingDao.selectDefaultEvent(settingVo);
		Map<String, String> manualMap 	= settingDao.selectManualEvent(settingVo);
		
		
		if(manualMap != null){
			return manualMap; 
		}else{
			if(currentMap != null){
				return currentMap; 
			}else{
				return defaultMap;
			}
		}
	}

}
