package com.project.corporatewebsite.admin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.corporatewebsite.vo.CareerVo;

@Repository
public class CareerDAO{
	
	@Autowired
	private SqlSession sqlSession;
	
	public int insert(CareerVo careerVo){
		sqlSession.insert("Career.insert", careerVo);
		return Integer.parseInt(careerVo.getSeq());
	}
	
	public int update(CareerVo careerVo){
		return sqlSession.update("Career.update", careerVo);
	}
	
	public int countList(CareerVo careerVo) {
		return sqlSession.selectOne("Career.countList", careerVo);
	}	
	
	public List<Map<String, String>> list(CareerVo careerVo){
		return sqlSession.selectList("Career.list", careerVo);
	}
	
	public Map<String, String> select(CareerVo careerVo){
		return sqlSession.selectOne("Career.select", careerVo);
	}
	
	public int delete(CareerVo careerVo) {
		return sqlSession.update("Career.delete", careerVo);
	}
	
}
