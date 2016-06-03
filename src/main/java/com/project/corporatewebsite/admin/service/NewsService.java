package com.project.corporatewebsite.admin.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.project.corporatewebsite.vo.NewsVo;

public interface NewsService {	
	public int insert(NewsVo newsVo, List<Map<String, MultipartFile>> fileList, String[] categories, String[] tags);
	public int update(NewsVo newsVo, List<Map<String, MultipartFile>> fileList, String[] categories, String[] tags);
	public int countList(NewsVo newsVo);
	public List<Map<String, String>> list(NewsVo newsVo);
	public Map<String, String> select(NewsVo newsVo);
	public int delete(NewsVo newsVo);
}
