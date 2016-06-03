package com.project.corporatewebsite.admin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.corporatewebsite.vo.SettingVo;

@Repository
public class SettingDAO{
	
	@Autowired
	private SqlSession sqlSession;
	
	public int insert(SettingVo settingVo){
		sqlSession.insert("Setting.insert", settingVo);
		return Integer.parseInt(settingVo.getSeq());
	}
	
	public int update(SettingVo settingVo){
		return sqlSession.update("Setting.update", settingVo);
	}
	
	public int countList(SettingVo settingVo) {
		return sqlSession.selectOne("Setting.countList", settingVo);
	}	
	
	public List<Map<String, String>> list(SettingVo settingVo){
		return sqlSession.selectList("Setting.list", settingVo);
	}
	
	public Map<String, String> select(SettingVo settingVo){
		return sqlSession.selectOne("Setting.select", settingVo);
	}
	
	public int delete(SettingVo settingVo) {
		return sqlSession.update("Setting.delete", settingVo);
	}
	
	public Map<String, String> selectDefaultEvent(SettingVo settingVo){
		return sqlSession.selectOne("Setting.selectDefaultEvent", settingVo);
	}

	public Map<String, String> selectCurrentEvent(SettingVo settingVo){
		return sqlSession.selectOne("Setting.selectCurrentEvent", settingVo);
	}

	public Map<String, String> selectManualEvent(SettingVo settingVo){
		return sqlSession.selectOne("Setting.selectManualEvent", settingVo);
	}

	public int clearEvent(SettingVo settingVo){
		return sqlSession.update("Setting.clearEvent", settingVo);
	}

	public int setAsEvent(SettingVo settingVo){
		return sqlSession.update("Setting.setAsEvent", settingVo);
	}
	
	public int setAsDefault(SettingVo settingVo){
		return sqlSession.update("Setting.setAsDefault", settingVo);
	}

	public int unsetDefault(SettingVo settingVo){
		return sqlSession.update("Setting.unsetDefault", settingVo);
	}
	
}
