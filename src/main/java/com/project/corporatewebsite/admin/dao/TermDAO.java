package com.project.corporatewebsite.admin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.corporatewebsite.vo.TermVo;

@Repository
public class TermDAO{
	
	@Autowired
	private SqlSession sqlSession;
	
	public int insert(TermVo termVo){
		sqlSession.insert("Term.insert", termVo);
		return Integer.parseInt(termVo.getSeq());
	}
	
	public int insertParentCategory(TermVo termVo){
		sqlSession.insert("Term.insertParentCategory", termVo);
		return Integer.parseInt(termVo.getSeq());
	}

	public int insertChildCategory(TermVo termVo){
		sqlSession.insert("Term.insertChildCategory", termVo);
		return Integer.parseInt(termVo.getSeq());
	}
	
	public int insertParentCategoryUpdate(TermVo termVo){		
		return sqlSession.update("Term.insertParentCategoryUpdate", termVo);
	}

	public int insertChildCategoryUpdate(TermVo termVo){		
		return sqlSession.update("Term.insertChildCategoryUpdate", termVo);
	}
	

	public int update(TermVo termVo){
		return sqlSession.update("Term.update", termVo);
	}
	
	public int countList(TermVo termVo) {
		return sqlSession.selectOne("Term.countList", termVo);
	}	
	
	public List<Map<String, String>> list(TermVo termVo){
		return sqlSession.selectList("Term.list", termVo);
	}
	
	public Map<String, String> select(TermVo termVo){
		return sqlSession.selectOne("Term.select", termVo);
	}
	
	public int delete(TermVo termVo) {
		return sqlSession.update("Term.delete", termVo);
	}

	public int deleteBySeq(TermVo termVo) {
		return sqlSession.update("Term.deleteBySeq", termVo);
	}

	public List<Map<String, String>> selectTags(){
		return sqlSession.selectList("Term.selectTags");
	}

	public List<Map<String, String>> selectCategories(){
		return sqlSession.selectList("Term.selectCategories");
	}

	public List<Map<String, String>> selectCategoriesByParent(TermVo termVo){
		return sqlSession.selectList("Term.selectCategoriesByParent", termVo);
	}

	public List<Map<String, String>> getFullPath(String seq) {
		return sqlSession.selectList("Term.getFullPath", seq);
	}
	
}
