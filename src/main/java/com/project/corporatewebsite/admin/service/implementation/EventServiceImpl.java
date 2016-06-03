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
import com.project.corporatewebsite.admin.dao.EventDAO;
import com.project.corporatewebsite.admin.dao.TermRelationshipDAO;
import com.project.corporatewebsite.admin.service.EventService;
import com.project.corporatewebsite.common.Constants;
import com.project.corporatewebsite.common.Util;
import com.project.corporatewebsite.common.VoHelper;
import com.project.corporatewebsite.vo.FileVo;
import com.project.corporatewebsite.vo.EventVo;
import com.project.corporatewebsite.vo.TermRelationshipVo;

@Component
public class EventServiceImpl implements EventService {
	
	@Autowired
	private EventDAO eventDao;
	
	@Autowired
	private FileDAO fileDao;
	
	@Autowired
	private TermRelationshipDAO termRelationshipDao;
	
	private static final Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);
	
	@Override
	public int insert(EventVo eventVo, List<Map<String, MultipartFile>> fileList, String[] categories, String[] tags) {
		
		int r = eventDao.insert(eventVo);
		
		for(Map<String, MultipartFile> fileMap : fileList){
			for(Entry<String, MultipartFile> file : fileMap.entrySet()){
				FileVo fileVo = VoHelper.prepareFileVo(	eventVo.getRegIp(), 
														eventVo.getRegId(), 
														file.getValue(), 
														Constants.EVENT, 
														file.getKey(), 
														Constants.UPLOAD_EVENT_PATH
														);
				fileVo.setOwnerSeq(String.valueOf(r));
				if(r > 0){
					if(!"".equals(file.getValue().getOriginalFilename())){
						try {
							Util.writeFile(file.getValue(), fileVo.getName(), Constants.WEB_ROOT_PHYSICAL+Constants.UPLOAD_EVENT_PATH);
							
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
			TermRelationshipVo termRelationshipVo = VoHelper.prepareTermRelationshipVo( eventVo.getRegIp(), 
																						eventVo.getRegId(), 
																						"", 
																						Constants.EVENT, 
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
			TermRelationshipVo termRelationshipVo = VoHelper.prepareTermRelationshipVo( eventVo.getRegIp(), 
																						eventVo.getRegId(), 
																						"", 
																						Constants.EVENT, 
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
	public int update(EventVo eventVo, List<Map<String, MultipartFile>> fileList, String[] categories, String[] tags) {
		
		int r = eventDao.update(eventVo);
		
		for(Map<String, MultipartFile> fileMap : fileList){
			for(Entry<String, MultipartFile> file : fileMap.entrySet()){
				FileVo fileVo = VoHelper.prepareFileVo(	eventVo.getModIp(), 
														eventVo.getModId(), 
														file.getValue(), 
														Constants.EVENT, 
														file.getKey(), 
														Constants.UPLOAD_EVENT_PATH
														);
				fileVo.setOwnerSeq(eventVo.getSeq());
				if(r > 0){
					if(!"".equals(file.getValue().getOriginalFilename())){
						try {
							Util.writeFile(file.getValue(), fileVo.getName(), Constants.WEB_ROOT_PHYSICAL+Constants.UPLOAD_EVENT_PATH);
							
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
			TermRelationshipVo termRelationshipVo = VoHelper.prepareTermRelationshipVo( eventVo.getRegIp(), 
																						eventVo.getRegId(), 
																						"", 
																						Constants.EVENT, 
																						Constants.TERM_CATEGORY 
																						);
			termRelationshipVo.setOwnerSeq(eventVo.getSeq());
			termRelationshipDao.delete(termRelationshipVo);
			for(String termSeq : categories){
				termRelationshipVo.setTermSeq(termSeq);
				termRelationshipDao.insert(termRelationshipVo);
			}
		}
		
		TermRelationshipVo termRelationshipVo = VoHelper.prepareTermRelationshipVo( eventVo.getRegIp(), 
																					eventVo.getRegId(), 
																					"", 
																					Constants.EVENT, 
																					Constants.TERM_TAG
																					);
		termRelationshipVo.setOwnerSeq(eventVo.getSeq());
		termRelationshipDao.delete(termRelationshipVo);
		for(String termSeq : tags){
			termRelationshipVo.setTermSeq(termSeq);
			termRelationshipDao.insert(termRelationshipVo);
		}
		
		return r;
	}
	
	@Override
	public List<Map<String, String>> list(EventVo eventVo) {
		return eventDao.list(eventVo);
	}
	
	@Override
	public int countList(EventVo eventVo) {
		return eventDao.countList(eventVo);
	}

	@Override
	public Map<String, String> select(EventVo eventVo) {
		Map<String, String> resultMap =  eventDao.select(eventVo);
		TermRelationshipVo termRelationshipVo = VoHelper.prepareTermRelationshipVo( eventVo.getRegIp(), 
																					eventVo.getRegId(), 
																					"", 
																					Constants.EVENT, 
																					Constants.TERM_TAG
																					);
		termRelationshipVo.setOwnerSeq(eventVo.getSeq());
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
	public int delete(EventVo eventVo) {
		return eventDao.delete(eventVo);
	}

}
