package com.project.corporatewebsite.admin.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.corporatewebsite.admin.dao.TermDAO;
import com.project.corporatewebsite.admin.service.TermService;
import com.project.corporatewebsite.common.Constants;
import com.project.corporatewebsite.vo.TermVo;

@Component
public class TermServiceImpl implements TermService{

	@Autowired
	TermDAO termDao;
	
	@Override
	public int insert(TermVo termVo) {
		int r = 0;
		/* 
		 * If category insert, treat differently
		 * Because we need to store lineage/path of category
		 */
		if(Constants.TERM_CATEGORY.equals(termVo.getTaxonomy())){
			if("0".equals(termVo.getParent())){
				/*
				 * For parent categories, lineage is the sequence itself 
				 * padded 4 times with 0 and ended by a slash 
				 */
				r = termDao.insertParentCategory(termVo);
				termVo.setSeq(String.valueOf(r));
				r = termDao.insertParentCategoryUpdate(termVo);
			}else{
				/*
				 * For child categories, lineage is the lineage of parent
				 * plus the sequence of this category padded 4 times with 0 ended by a slash
				 */
				r = termDao.insertChildCategory(termVo);
				termVo.setSeq(String.valueOf(r));
				r = termDao.insertChildCategoryUpdate(termVo);
			}
		}else{
			r = termDao.insert(termVo);
		}
		
		return r;
	}

	@Override
	public int update(TermVo termVo) {
		return termDao.update(termVo);		
	}

	@Override
	public int countList(TermVo termVo) {
		return termDao.countList(termVo);
	}

	@Override
	public List<Map<String, String>> list(TermVo termVo) {
		List<Map<String, String>> resultList = termDao.list(termVo);
		
		for(Map<String, String> resultMap : resultList){
			if(!"".equals(resultMap.get("lineage"))){
				StringBuilder fullPath = new StringBuilder();
				List<Map<String, String>> fullPathList = new ArrayList<Map<String,String>>();
				fullPathList = termDao.getFullPath(String.valueOf(resultMap.get("seq")));
				if(!fullPathList.isEmpty()){
					for(Map<String, String> fullPathMap : fullPathList){
						fullPath.append(String.valueOf(fullPathMap.get("seq")));
						fullPath.append("|");
						fullPath.append(fullPathMap.get("name").toString());
						fullPath.append(",");					
					}
					String sFullPath = fullPath.substring(0, fullPath.length()-1);				
					resultMap.put("fullPath", sFullPath);
				}
			}
		}
		
		return resultList;
	}

	@Override
	public Map<String, String> select(TermVo termVo) {
		Map<String, String> resultMap = termDao.select(termVo);
		if(!"".equals(resultMap.get("lineage"))){
			StringBuilder fullPath = new StringBuilder();
			List<Map<String, String>> fullPathList = new ArrayList<Map<String,String>>();
			fullPathList = termDao.getFullPath(String.valueOf(resultMap.get("seq")));
			if(!fullPathList.isEmpty()){
				for(Map<String, String> fullPathMap : fullPathList){
					fullPath.append(String.valueOf(fullPathMap.get("seq")));
					fullPath.append("|");
					fullPath.append(fullPathMap.get("name").toString());
					fullPath.append(",");					
				}
				String sFullPath = fullPath.substring(0, fullPath.length()-1);				
				resultMap.put("fullPath", sFullPath);
			}
		}
		
		return resultMap;
	}

	@Override
	public int delete(TermVo termVo) {
		if(Constants.TERM_CATEGORY.equals(termVo.getTaxonomy())){
			return termDao.delete(termVo);
		}else{
			return termDao.deleteBySeq(termVo);			
		}
	}

	@Override
	public List<Map<String, String>> selectTags() {
		return termDao.selectTags();				
	}

	@Override
	public List<Map<String, String>> selectCategories() {
		return termDao.selectCategories();
	}

	@Override
	public List<Map<String, String>> selectCategoriesByParent(TermVo termVo) {
		return termDao.selectCategoriesByParent(termVo);
	}

}
