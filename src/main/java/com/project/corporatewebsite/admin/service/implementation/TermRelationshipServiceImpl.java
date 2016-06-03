package com.project.corporatewebsite.admin.service.implementation;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.corporatewebsite.admin.dao.TermRelationshipDAO;
import com.project.corporatewebsite.admin.service.TermRelationshipService;
import com.project.corporatewebsite.vo.TermRelationshipVo;

@Component
public class TermRelationshipServiceImpl implements TermRelationshipService{

	@Autowired
	TermRelationshipDAO termRelationshipDao;

	@Override
	public int insert(TermRelationshipVo termRelationshipVo) {
		return termRelationshipDao.insert(termRelationshipVo);
	}

	@Override
	public Map<String, String> selectBySeq(TermRelationshipVo termRelationshipVo) {
		return termRelationshipDao.selectBySeq(termRelationshipVo);
	}

	@Override
	public List<Map<String, String>> selectByOwner(TermRelationshipVo termRelationshipVo) {
		return termRelationshipDao.selectByOwner(termRelationshipVo);
	}

	@Override
	public int delete(TermRelationshipVo termRelationshipVo) {
		return termRelationshipDao.delete(termRelationshipVo);
	}
	


}
