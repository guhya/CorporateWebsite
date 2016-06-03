package com.project.corporatewebsite.admin.service;

import java.util.List;
import java.util.Map;

import com.project.corporatewebsite.vo.TermRelationshipVo;

public interface TermRelationshipService {	
	public int insert(TermRelationshipVo termRelationshipVo);
	public Map<String, String> selectBySeq(TermRelationshipVo termRelationshipVo);
	public List<Map<String, String>> selectByOwner(TermRelationshipVo termRelationshipVo);
	public int delete(TermRelationshipVo termRelationshipVo);
}
