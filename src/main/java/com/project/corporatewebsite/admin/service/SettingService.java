package com.project.corporatewebsite.admin.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.project.corporatewebsite.vo.SettingVo;

public interface SettingService {	
	public int insert(SettingVo settingVo, List<Map<String, MultipartFile>> fileList);
	public int update(SettingVo settingVo, List<Map<String, MultipartFile>> fileList);
	public int countList(SettingVo settingVo);
	public List<Map<String, String>> list(SettingVo settingVo);
	public Map<String, String> select(SettingVo settingVo);
	public int delete(SettingVo settingVo);
	public Map<String, String> selectDefaultEvent(SettingVo settingVo);
	public Map<String, String> selectCurrentEvent(SettingVo settingVo);
	public int clearEvent(SettingVo settingVo);
	public int setAsEvent(SettingVo settingVo);
	public int setAsDefault(SettingVo settingVo);
	public int unsetDefault(SettingVo settingVo);
	public Map<String, String> getCurrentSetting(SettingVo settingVo);	
}
