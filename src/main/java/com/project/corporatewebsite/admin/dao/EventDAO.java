package com.project.corporatewebsite.admin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.corporatewebsite.vo.EventVo;

@Repository
public class EventDAO{
	
	@Autowired
	private SqlSession sqlSession;
	
	public int insert(EventVo eventVo){
		sqlSession.insert("Event.insert", eventVo);
		return Integer.parseInt(eventVo.getSeq());
	}
	
	public int update(EventVo eventVo){
		return sqlSession.update("Event.update", eventVo);
	}
	
	public int countList(EventVo eventVo) {
		return sqlSession.selectOne("Event.countList", eventVo);
	}	
	
	public List<Map<String, String>> list(EventVo eventVo){
		return sqlSession.selectList("Event.list", eventVo);
	}
	
	public Map<String, String> select(EventVo eventVo){
		return sqlSession.selectOne("Event.select", eventVo);
	}
	
	public int delete(EventVo eventVo) {
		return sqlSession.update("Event.delete", eventVo);
	}
	
}
