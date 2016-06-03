package com.project.corporatewebsite.admin.service.implementation;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.corporatewebsite.admin.dao.FileDAO;
import com.project.corporatewebsite.admin.service.FileService;
import com.project.corporatewebsite.vo.FileVo;

@Component
public class FileServiceImpl implements FileService {
	
	@Autowired
	private FileDAO fileDao;
	
	@Override
	public int insert(FileVo fileVo) {
		return fileDao.insert(fileVo);
	}

	@Override
	public Map<String, String> selectById(FileVo fileVo) {
		return fileDao.selectById(fileVo);
	}

	@Override
	public Map<String, String> selectByOwner(Map<String, String> fileMap) {
		return fileDao.selectByOwner(fileMap);
	}

	@Override
	public int delete(FileVo fileVo) {
		return fileDao.delete(fileVo);
	}

	@Override
	public List<Map<String, String>> list(FileVo fileVo) {
		return fileDao.list(fileVo);
	}

}
