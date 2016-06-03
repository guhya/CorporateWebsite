package com.project.corporatewebsite.admin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.corporatewebsite.vo.NewsVo;

@Repository
public class NewsDAO{
	
	@Autowired
	private SqlSession sqlSession;
	
	public int insert(NewsVo newsVo){
		sqlSession.insert("News.insert", newsVo);
		return Integer.parseInt(newsVo.getSeq());
	}
	
	public int update(NewsVo newsVo){
		return sqlSession.update("News.update", newsVo);
	}
	
	public int countList(NewsVo newsVo) {
		return sqlSession.selectOne("News.countList", newsVo);
	}	
	
	public List<Map<String, String>> list(NewsVo newsVo){
		return sqlSession.selectList("News.list", newsVo);
	}
	
	public Map<String, String> select(NewsVo newsVo){
		return sqlSession.selectOne("News.select", newsVo);
	}
	
	public int delete(NewsVo newsVo) {
		return sqlSession.update("News.delete", newsVo);
	}
	
}
