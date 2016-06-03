package com.project.corporatewebsite.admin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.corporatewebsite.vo.FileVo;

@Repository
public class FileDAO{
	
	@Autowired
	private SqlSession sqlSession;
	
	public int insert(FileVo fileVo){
		return sqlSession.insert("File.insert", fileVo);
	}
	
	public Map<String, String> selectById(FileVo fileVo){
		return sqlSession.selectOne("File.selectById", fileVo);
	}
	
	public List<Map<String, String>> list(FileVo fileVo){
		return sqlSession.selectList("File.list", fileVo);
	}
	
	public Map<String, String> selectByOwner(Map<String, String> fileMap){
		return sqlSession.selectOne("File.selectByOwner", fileMap);
	}
	
	public int delete(FileVo fileVo) {
		return sqlSession.update("File.delete", fileVo);
	}
	
}
