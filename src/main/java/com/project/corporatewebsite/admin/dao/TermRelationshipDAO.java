package com.project.corporatewebsite.admin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.corporatewebsite.vo.TermRelationshipVo;

@Repository
public class TermRelationshipDAO{
	
	@Autowired
	private SqlSession sqlSession;
	
	public int insert(TermRelationshipVo termRelationshipVo){
		sqlSession.insert("TermRelationship.insert", termRelationshipVo);
		return Integer.parseInt(termRelationshipVo.getSeq());		
	}
	
	public Map<String, String> selectBySeq(TermRelationshipVo termRelationshipVo){
		return sqlSession.selectOne("TermRelationship.selectBySeq", termRelationshipVo);
	}
	
	public List<Map<String, String>> selectByOwner(TermRelationshipVo termRelationshipVo){
		return sqlSession.selectList("TermRelationship.selectByOwner", termRelationshipVo);
	}
	
	public int delete(TermRelationshipVo termRelationshipVo){
		return sqlSession.update("TermRelationship.delete", termRelationshipVo);
	}

}
