package com.project.corporatewebsite.admin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.corporatewebsite.vo.CatalogVo;

@Repository
public class CatalogDAO{
	
	@Autowired
	private SqlSession sqlSession;
	
	public int insert(CatalogVo catalogVo){
		sqlSession.insert("Catalog.insert", catalogVo);
		return Integer.parseInt(catalogVo.getSeq());
	}
	
	public int update(CatalogVo catalogVo){
		return sqlSession.update("Catalog.update", catalogVo);
	}
	
	public int countList(CatalogVo catalogVo) {
		return sqlSession.selectOne("Catalog.countList", catalogVo);
	}	
	
	public List<Map<String, String>> list(CatalogVo catalogVo){
		return sqlSession.selectList("Catalog.list", catalogVo);
	}
	
	public Map<String, String> select(CatalogVo catalogVo){
		return sqlSession.selectOne("Catalog.select", catalogVo);
	}
	
	public int delete(CatalogVo catalogVo) {
		return sqlSession.update("Catalog.delete", catalogVo);
	}
	
}
