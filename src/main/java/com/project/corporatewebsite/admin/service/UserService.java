package com.project.corporatewebsite.admin.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.project.corporatewebsite.vo.UserVo;

public interface UserService {	
	public int insert(UserVo userVo, List<Map<String, MultipartFile>> fileList);
	public int update(UserVo userVo, List<Map<String, MultipartFile>> fileList);
	public int countList(UserVo userVo);
	public List<Map<String, String>> list(UserVo userVo);
	public Map<String, String> select(UserVo userVo);
	public int delete(UserVo userVo);
}
