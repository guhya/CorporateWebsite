package com.project.corporatewebsite.admin.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.project.corporatewebsite.vo.CatalogVo;

public interface CatalogService {	
	public int insert(CatalogVo catalogVo, List<Map<String, MultipartFile>> fileList, String[] categories, String[] tags);
	public int update(CatalogVo catalogVo, List<Map<String, MultipartFile>> fileList, String[] categories, String[] tags);
	public int countList(CatalogVo catalogVo);
	public List<Map<String, String>> list(CatalogVo catalogVo);
	public Map<String, String> select(CatalogVo catalogVo);
	public int delete(CatalogVo catalogVo);
}
