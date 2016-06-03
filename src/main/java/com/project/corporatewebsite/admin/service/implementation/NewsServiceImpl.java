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
import com.project.corporatewebsite.admin.dao.NewsDAO;
import com.project.corporatewebsite.admin.dao.TermRelationshipDAO;
import com.project.corporatewebsite.admin.service.NewsService;
import com.project.corporatewebsite.common.Constants;
import com.project.corporatewebsite.common.Util;
import com.project.corporatewebsite.common.VoHelper;
import com.project.corporatewebsite.vo.FileVo;
import com.project.corporatewebsite.vo.NewsVo;
import com.project.corporatewebsite.vo.TermRelationshipVo;

@Component
public class NewsServiceImpl implements NewsService {
	
	@Autowired
	private NewsDAO newsDao;
	
	@Autowired
	private FileDAO fileDao;
	
	@Autowired
	private TermRelationshipDAO termRelationshipDao;
	
	private static final Logger logger = LoggerFactory.getLogger(NewsServiceImpl.class);
	
	@Override
	public int insert(NewsVo newsVo, List<Map<String, MultipartFile>> fileList, String[] categories, String[] tags) {
		
		int r = newsDao.insert(newsVo);
		
		for(Map<String, MultipartFile> fileMap : fileList){
			for(Entry<String, MultipartFile> file : fileMap.entrySet()){
				FileVo fileVo = VoHelper.prepareFileVo(	newsVo.getRegIp(), 
														newsVo.getRegId(), 
														file.getValue(), 
														Constants.NEWS, 
														file.getKey(), 
														Constants.UPLOAD_NEWS_PATH
														);
				fileVo.setOwnerSeq(String.valueOf(r));
				if(r > 0){
					if(!"".equals(file.getValue().getOriginalFilename())){
						try {
							Util.writeFile(file.getValue(), fileVo.getName(), Constants.WEB_ROOT_PHYSICAL+Constants.UPLOAD_NEWS_PATH);
							
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
			TermRelationshipVo termRelationshipVo = VoHelper.prepareTermRelationshipVo( newsVo.getRegIp(), 
																						newsVo.getRegId(), 
																						"", 
																						Constants.NEWS, 
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
			TermRelationshipVo termRelationshipVo = VoHelper.prepareTermRelationshipVo( newsVo.getRegIp(), 
																						newsVo.getRegId(), 
																						"", 
																						Constants.NEWS, 
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
	public int update(NewsVo newsVo, List<Map<String, MultipartFile>> fileList, String[] categories, String[] tags) {
		
		int r = newsDao.update(newsVo);
		
		for(Map<String, MultipartFile> fileMap : fileList){
			for(Entry<String, MultipartFile> file : fileMap.entrySet()){
				FileVo fileVo = VoHelper.prepareFileVo(	newsVo.getModIp(), 
														newsVo.getModId(), 
														file.getValue(), 
														Constants.NEWS, 
														file.getKey(), 
														Constants.UPLOAD_NEWS_PATH
														);
				fileVo.setOwnerSeq(newsVo.getSeq());
				if(r > 0){
					if(!"".equals(file.getValue().getOriginalFilename())){
						try {
							Util.writeFile(file.getValue(), fileVo.getName(), Constants.WEB_ROOT_PHYSICAL+Constants.UPLOAD_NEWS_PATH);
							
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
			TermRelationshipVo termRelationshipVo = VoHelper.prepareTermRelationshipVo( newsVo.getRegIp(), 
																						newsVo.getRegId(), 
																						"", 
																						Constants.NEWS, 
																						Constants.TERM_CATEGORY 
																						);
			termRelationshipVo.setOwnerSeq(newsVo.getSeq());
			termRelationshipDao.delete(termRelationshipVo);
			for(String termSeq : categories){
				termRelationshipVo.setTermSeq(termSeq);
				termRelationshipDao.insert(termRelationshipVo);
			}
		}
		
		TermRelationshipVo termRelationshipVo = VoHelper.prepareTermRelationshipVo( newsVo.getRegIp(), 
																					newsVo.getRegId(), 
																					"", 
																					Constants.NEWS, 
																					Constants.TERM_TAG
																					);
		termRelationshipVo.setOwnerSeq(newsVo.getSeq());
		termRelationshipDao.delete(termRelationshipVo);
		for(String termSeq : tags){
			termRelationshipVo.setTermSeq(termSeq);
			termRelationshipDao.insert(termRelationshipVo);
		}
		
		return r;
	}
	
	@Override
	public List<Map<String, String>> list(NewsVo newsVo) {
		return newsDao.list(newsVo);
	}
	
	@Override
	public int countList(NewsVo newsVo) {
		return newsDao.countList(newsVo);
	}

	@Override
	public Map<String, String> select(NewsVo newsVo) {
		Map<String, String> resultMap =  newsDao.select(newsVo);
		TermRelationshipVo termRelationshipVo = VoHelper.prepareTermRelationshipVo( newsVo.getRegIp(), 
																					newsVo.getRegId(), 
																					"", 
																					Constants.NEWS, 
																					Constants.TERM_TAG
																					);
		termRelationshipVo.setOwnerSeq(newsVo.getSeq());
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
	public int delete(NewsVo newsVo) {
		return newsDao.delete(newsVo);
	}

}
