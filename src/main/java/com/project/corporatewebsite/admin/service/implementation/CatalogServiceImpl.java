package com.project.corporatewebsite.admin.service.implementation;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.project.corporatewebsite.admin.dao.FileDAO;
import com.project.corporatewebsite.admin.dao.CatalogDAO;
import com.project.corporatewebsite.admin.dao.TermRelationshipDAO;
import com.project.corporatewebsite.admin.service.CatalogService;
import com.project.corporatewebsite.common.Constants;
import com.project.corporatewebsite.common.Util;
import com.project.corporatewebsite.common.VoHelper;
import com.project.corporatewebsite.vo.FileVo;
import com.project.corporatewebsite.vo.CatalogVo;
import com.project.corporatewebsite.vo.TermRelationshipVo;

@Component
public class CatalogServiceImpl implements CatalogService {
	
	@Autowired
	private CatalogDAO catalogDao;
	
	@Autowired
	private FileDAO fileDao;
	
	@Autowired
	private TermRelationshipDAO termRelationshipDao;
	
	private static final Logger logger = LoggerFactory.getLogger(CatalogServiceImpl.class);
	
	@Override
	public int insert(CatalogVo catalogVo, List<Map<String, MultipartFile>> fileList, String[] categories, String[] tags) {
		
		int r = catalogDao.insert(catalogVo);
		
		for(Map<String, MultipartFile> fileMap : fileList){
			for(Entry<String, MultipartFile> file : fileMap.entrySet()){
				FileVo fileVo = VoHelper.prepareFileVo(	catalogVo.getRegIp(), 
														catalogVo.getRegId(), 
														file.getValue(), 
														Constants.CATALOG, 
														file.getKey(), 
														Constants.UPLOAD_CATALOG_PATH
														);
				fileVo.setOwnerSeq(String.valueOf(r));
				if(r > 0){
					if(!"".equals(file.getValue().getOriginalFilename())){
						try {
							Util.writeFile(file.getValue(), fileVo.getName(), Constants.WEB_ROOT_PHYSICAL+Constants.UPLOAD_CATALOG_PATH);
							
							fileDao.delete(fileVo);
							fileDao.insert(fileVo);
							
						} catch (Exception e) {
							logger.info(e.getMessage());
						}
					}
				}
			}
		}
		
		if(categories.length > 0){
			TermRelationshipVo termRelationshipVo = VoHelper.prepareTermRelationshipVo( catalogVo.getRegIp(), 
																						catalogVo.getRegId(), 
																						"", 
																						Constants.CATALOG, 
																						Constants.TERM_CATEGORY 
																						);
			termRelationshipVo.setOwnerSeq(String.valueOf(r));
			termRelationshipDao.delete(termRelationshipVo);
			for(String termSeq : categories){
				termRelationshipVo.setTermSeq(termSeq);
				termRelationshipDao.insert(termRelationshipVo);
			}			
		}
		
		if(tags.length > 0){
			TermRelationshipVo termRelationshipVo = VoHelper.prepareTermRelationshipVo( catalogVo.getRegIp(), 
																						catalogVo.getRegId(), 
																						"", 
																						Constants.CATALOG, 
																						Constants.TERM_TAG
																						);
			termRelationshipVo.setOwnerSeq(String.valueOf(r));
			termRelationshipDao.delete(termRelationshipVo);
			for(String termSeq : tags){
				termRelationshipVo.setTermSeq(termSeq);
				termRelationshipDao.insert(termRelationshipVo);
			}
		}
		
		return r;
		
	}

	@Override
	public int update(CatalogVo catalogVo, List<Map<String, MultipartFile>> fileList, String[] categories, String[] tags) {
		
		int r = catalogDao.update(catalogVo);
		
		for(Map<String, MultipartFile> fileMap : fileList){
			for(Entry<String, MultipartFile> file : fileMap.entrySet()){
				FileVo fileVo = VoHelper.prepareFileVo(	catalogVo.getModIp(), 
														catalogVo.getModId(), 
														file.getValue(), 
														Constants.CATALOG, 
														file.getKey(), 
														Constants.UPLOAD_CATALOG_PATH
														);
				fileVo.setOwnerSeq(catalogVo.getSeq());
				if(r > 0){
					if(!"".equals(file.getValue().getOriginalFilename())){
						try {
							Util.writeFile(file.getValue(), fileVo.getName(), Constants.WEB_ROOT_PHYSICAL+Constants.UPLOAD_CATALOG_PATH);
							
							fileDao.delete(fileVo);
							fileDao.insert(fileVo);
							
						} catch (Exception e) {
							logger.info(e.getMessage());
						}
					}
				}
			}
		}
		
		if(categories.length > 0){
			TermRelationshipVo termRelationshipVo = VoHelper.prepareTermRelationshipVo( catalogVo.getRegIp(), 
																						catalogVo.getRegId(), 
																						"", 
																						Constants.CATALOG, 
																						Constants.TERM_CATEGORY 
																						);
			termRelationshipVo.setOwnerSeq(catalogVo.getSeq());
			termRelationshipDao.delete(termRelationshipVo);
			for(String termSeq : categories){
				termRelationshipVo.setTermSeq(termSeq);
				termRelationshipDao.insert(termRelationshipVo);
			}
		}
		
		TermRelationshipVo termRelationshipVo = VoHelper.prepareTermRelationshipVo( catalogVo.getRegIp(), 
																					catalogVo.getRegId(), 
																					"", 
																					Constants.CATALOG, 
																					Constants.TERM_TAG
																					);
		termRelationshipVo.setOwnerSeq(catalogVo.getSeq());
		termRelationshipDao.delete(termRelationshipVo);
		for(String termSeq : tags){
			termRelationshipVo.setTermSeq(termSeq);
			termRelationshipDao.insert(termRelationshipVo);
		}
		
		return r;
	}
	
	@Override
	public List<Map<String, String>> list(CatalogVo catalogVo) {
		return catalogDao.list(catalogVo);
	}
	
	@Override
	public int countList(CatalogVo catalogVo) {
		return catalogDao.countList(catalogVo);
	}

	@Override
	public Map<String, String> select(CatalogVo catalogVo) {
		Map<String, String> resultMap =  catalogDao.select(catalogVo);
		TermRelationshipVo termRelationshipVo = VoHelper.prepareTermRelationshipVo( catalogVo.getRegIp(), 
																					catalogVo.getRegId(), 
																					"", 
																					Constants.CATALOG, 
																					Constants.TERM_TAG
																					);
		termRelationshipVo.setOwnerSeq(catalogVo.getSeq());
		List<Map<String, String>> tagList = termRelationshipDao.selectByOwner(termRelationshipVo);
		if(!tagList.isEmpty()){
			StringBuilder tags = new StringBuilder();
			for(Map<String, String> tagMap : tagList){
				tags.append(String.valueOf(tagMap.get("seq")));
				tags.append("|");
				tags.append(tagMap.get("name").toString());
				tags.append(",");					
			}
			String sTags = tags.substring(0, tags.length()-1);				
			resultMap.put("tags", sTags);
		}
		
		return resultMap;		
	}

	@Override
	public int delete(CatalogVo catalogVo) {
		return catalogDao.delete(catalogVo);
	}

}
