package com.project.corporatewebsite.common;

import org.springframework.web.multipart.MultipartFile;

import com.project.corporatewebsite.vo.FileVo;
import com.project.corporatewebsite.vo.TermRelationshipVo;

public class VoHelper {

	public static FileVo prepareFileVo(String ip, String username, MultipartFile file, String channel, String category, String path){
		FileVo fileVo = new FileVo();
		fileVo.setRegIp(ip);
		fileVo.setModIp(ip);
		fileVo.setRegId(username);
		fileVo.setModId(username);
		
		if(file != null){
			fileVo.setCategory(category);
			fileVo.setChannel(channel);
			
			fileVo.setName(channel + "_" + category + "_" + System.currentTimeMillis() + "." + Util.getFileExtension(file.getOriginalFilename()));
			fileVo.setOriginalName(file.getOriginalFilename());
			fileVo.setSize(String.valueOf(file.getSize()));
			fileVo.setPath(path);
		}
		
		return fileVo;
	}
	
	public static TermRelationshipVo prepareTermRelationshipVo(String ip, String username, String termSeq, String channel, String taxonomy){
		TermRelationshipVo termRelationshipVo = new TermRelationshipVo();
		termRelationshipVo.setChannel(channel);
		termRelationshipVo.setTermSeq(termSeq);
		termRelationshipVo.setTaxonomy(taxonomy);

		
		termRelationshipVo.setRegIp(ip);
		termRelationshipVo.setModIp(ip);
		termRelationshipVo.setRegId(username);
		termRelationshipVo.setModId(username);

		return termRelationshipVo;
	}
	
}
