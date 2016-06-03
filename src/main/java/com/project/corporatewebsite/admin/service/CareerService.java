package com.project.corporatewebsite.admin.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.project.corporatewebsite.vo.CareerVo;

public interface CareerService {	
	public int insert(CareerVo careerVo, List<Map<String, MultipartFile>> fileList, String[] categories, String[] tags);
	public int update(CareerVo careerVo, List<Map<String, MultipartFile>> fileList, String[] categories, String[] tags);
	public int countList(CareerVo careerVo);
	public List<Map<String, String>> list(CareerVo careerVo);
	public Map<String, String> select(CareerVo careerVo);
	public int delete(CareerVo careerVo);
}
