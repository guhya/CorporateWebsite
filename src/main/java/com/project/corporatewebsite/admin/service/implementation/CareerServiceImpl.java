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
import com.project.corporatewebsite.admin.dao.CareerDAO;
import com.project.corporatewebsite.admin.dao.TermRelationshipDAO;
import com.project.corporatewebsite.admin.service.CareerService;
import com.project.corporatewebsite.common.Constants;
import com.project.corporatewebsite.common.Util;
import com.project.corporatewebsite.common.VoHelper;
import com.project.corporatewebsite.vo.FileVo;
import com.project.corporatewebsite.vo.CareerVo;
import com.project.corporatewebsite.vo.TermRelationshipVo;

@Component
public class CareerServiceImpl implements CareerService {
	
	@Autowired
	private CareerDAO careerDao;
	
	@Autowired
	private FileDAO fileDao;
	
	@Autowired
	private TermRelationshipDAO termRelationshipDao;
	
	private static final Logger logger = LoggerFactory.getLogger(CareerServiceImpl.class);
	
	@Override
	public int insert(CareerVo careerVo, List<Map<String, MultipartFile>> fileList, String[] categories, String[] tags) {
		
		int r = careerDao.insert(careerVo);
		
		for(Map<String, MultipartFile> fileMap : fileList){
			for(Entry<String, MultipartFile> file : fileMap.entrySet()){
				FileVo fileVo = VoHelper.prepareFileVo(	careerVo.getRegIp(), 
														careerVo.getRegId(), 
														file.getValue(), 
														Constants.CAREER, 
														file.getKey(), 
														Constants.UPLOAD_CAREER_PATH
														);
				fileVo.setOwnerSeq(String.valueOf(r));
				if(r > 0){
					if(!"".equals(file.getValue().getOriginalFilename())){
						try {
							Util.writeFile(file.getValue(), fileVo.getName(), Constants.WEB_ROOT_PHYSICAL+Constants.UPLOAD_CAREER_PATH);
							
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
			TermRelationshipVo termRelationshipVo = VoHelper.prepareTermRelationshipVo( careerVo.getRegIp(), 
																						careerVo.getRegId(), 
																						"", 
																						Constants.CAREER, 
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
			TermRelationshipVo termRelationshipVo = VoHelper.prepareTermRelationshipVo( careerVo.getRegIp(), 
																						careerVo.getRegId(), 
																						"", 
																						Constants.CAREER, 
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
	public int update(CareerVo careerVo, List<Map<String, MultipartFile>> fileList, String[] categories, String[] tags) {
		
		int r = careerDao.update(careerVo);
		
		for(Map<String, MultipartFile> fileMap : fileList){
			for(Entry<String, MultipartFile> file : fileMap.entrySet()){
				FileVo fileVo = VoHelper.prepareFileVo(	careerVo.getModIp(), 
														careerVo.getModId(), 
														file.getValue(), 
														Constants.CAREER, 
														file.getKey(), 
														Constants.UPLOAD_CAREER_PATH
														);
				fileVo.setOwnerSeq(careerVo.getSeq());
				if(r > 0){
					if(!"".equals(file.getValue().getOriginalFilename())){
						try {
							Util.writeFile(file.getValue(), fileVo.getName(), Constants.WEB_ROOT_PHYSICAL+Constants.UPLOAD_CAREER_PATH);
							
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
			TermRelationshipVo termRelationshipVo = VoHelper.prepareTermRelationshipVo( careerVo.getRegIp(), 
																						careerVo.getRegId(), 
																						"", 
																						Constants.CAREER, 
																						Constants.TERM_CATEGORY 
																						);
			termRelationshipVo.setOwnerSeq(careerVo.getSeq());
			termRelationshipDao.delete(termRelationshipVo);
			for(String termSeq : categories){
				termRelationshipVo.setTermSeq(termSeq);
				termRelationshipDao.insert(termRelationshipVo);
			}
		}
		
		TermRelationshipVo termRelationshipVo = VoHelper.prepareTermRelationshipVo( careerVo.getRegIp(), 
																					careerVo.getRegId(), 
																					"", 
																					Constants.CAREER, 
																					Constants.TERM_TAG
																					);
		termRelationshipVo.setOwnerSeq(careerVo.getSeq());
		termRelationshipDao.delete(termRelationshipVo);
		for(String termSeq : tags){
			termRelationshipVo.setTermSeq(termSeq);
			termRelationshipDao.insert(termRelationshipVo);
		}
		
		return r;
	}
	
	@Override
	public List<Map<String, String>> list(CareerVo careerVo) {
		return careerDao.list(careerVo);
	}
	
	@Override
	public int countList(CareerVo careerVo) {
		return careerDao.countList(careerVo);
	}

	@Override
	public Map<String, String> select(CareerVo careerVo) {
		Map<String, String> resultMap =  careerDao.select(careerVo);
		TermRelationshipVo termRelationshipVo = VoHelper.prepareTermRelationshipVo( careerVo.getRegIp(), 
																					careerVo.getRegId(), 
																					"", 
																					Constants.CAREER, 
																					Constants.TERM_TAG
																					);
		termRelationshipVo.setOwnerSeq(careerVo.getSeq());
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
	public int delete(CareerVo careerVo) {
		return careerDao.delete(careerVo);
	}

}
