package com.project.corporatewebsite.admin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.corporatewebsite.vo.RoleVo;
import com.project.corporatewebsite.vo.UserVo;

@Repository
public class UserDAO{
	
	@Autowired
	private SqlSession sqlSession;
	
	public int insert(UserVo userVo){
		sqlSession.insert("User.insert", userVo);
		return Integer.parseInt(userVo.getSeq());
	}
	
	public int update(UserVo userVo){
		return sqlSession.update("User.update", userVo);
	}
	
	public int countList(UserVo userVo) {
		return sqlSession.selectOne("User.countList", userVo);
	}	
	
	public List<Map<String, String>> list(UserVo userVo){
		return sqlSession.selectList("User.list", userVo);
	}
	
	public Map<String, String> select(UserVo userVo){
		return sqlSession.selectOne("User.select", userVo);
	}
	
	public int insertRole(RoleVo roleVo){
		return sqlSession.insert("User.insertRole", roleVo);
	}
	
	public List<String> listUserRoles(UserVo userVo){
		return sqlSession.selectList("User.listUserRoles", userVo);
	}

	public int delete(UserVo userVo) {
		return sqlSession.update("User.delete", userVo);
	}
	
}
