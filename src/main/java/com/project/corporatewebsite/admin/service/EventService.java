package com.project.corporatewebsite.admin.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.project.corporatewebsite.vo.EventVo;

public interface EventService {	
	public int insert(EventVo eventVo, List<Map<String, MultipartFile>> fileList, String[] categories, String[] tags);
	public int update(EventVo eventVo, List<Map<String, MultipartFile>> fileList, String[] categories, String[] tags);
	public int countList(EventVo eventVo);
	public List<Map<String, String>> list(EventVo eventVo);
	public Map<String, String> select(EventVo eventVo);
	public int delete(EventVo eventVo);
}
