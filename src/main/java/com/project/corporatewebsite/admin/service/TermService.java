package com.project.corporatewebsite.admin.service;

import java.util.List;
import java.util.Map;

import com.project.corporatewebsite.vo.TermVo;

public interface TermService {	
	public int insert(TermVo termVo);
	public int update(TermVo termVo);
	public int countList(TermVo termVo);
	public List<Map<String, String>> list(TermVo termVo);
	public Map<String, String> select(TermVo termVo);
	public int delete(TermVo termVo);
	public List<Map<String, String>> selectTags();
	public List<Map<String, String>> selectCategories();
	public List<Map<String, String>> selectCategoriesByParent(TermVo termVo);
}
