package com.project.corporatewebsite.admin.service;

import java.util.List;
import java.util.Map;

import com.project.corporatewebsite.vo.FileVo;

public interface FileService {	
	public int insert(FileVo fileVo);
	public Map<String, String> selectById(FileVo fileVo);
	public Map<String, String> selectByOwner(Map<String, String> fileMap);
	public List<Map<String, String>> list(FileVo fileVo);
	public int delete(FileVo fileVo);
}
