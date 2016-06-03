package com.project.corporatewebsite.admin.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.corporatewebsite.admin.service.FileService;
import com.project.corporatewebsite.common.Constants;
import com.project.corporatewebsite.vo.FileVo;

@Controller
public class CommonController {
	private static final Logger logger = LoggerFactory.getLogger(CommonController.class);
	
	@Autowired
	FileService fileService;

	@RequestMapping(value = "/image/{seq}")
	public void image(Model model,
						@ModelAttribute("fileVo") FileVo fileVo,
						@PathVariable("seq") String seq,
						HttpServletRequest request, HttpServletResponse response) 
	{
		logger.info("Image");
		
		fileVo.setSeq(seq);
		Map<String, String> result = fileService.selectById(fileVo);
		if(result != null){
			String path	= Constants.WEB_ROOT_PHYSICAL+result.get("path")+"/"+result.get("name");
			File file = new File(path);
			
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new BufferedInputStream(new FileInputStream(file));
				out = new BufferedOutputStream(response.getOutputStream());
				
				response.setContentLength((int) file.length());				
				
				int i = 0;
				byte buffer[] = new byte[8192];
				while ((i = in.read(buffer)) != -1) { 
					out.write(buffer, 0, i);					
				}	  
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if(out != null){
						out.flush();
						out.close();
						return;
					}
					if(in != null) in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	@RequestMapping(value = "/download/{seq}")
	public void download(Model model,
						@ModelAttribute("fileVo") FileVo fileVo,
						@PathVariable("seq") String seq,
						HttpServletRequest request, HttpServletResponse response) 
	{
		logger.info("Download");
		
		fileVo.setSeq(seq);
		Map<String, String> result = fileService.selectById(fileVo);
		if(result != null){
			String path	= Constants.WEB_ROOT_PHYSICAL+result.get("path")+"/"+result.get("name");
			File file = new File(path);
			
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new BufferedInputStream(new FileInputStream(file));
				out = new BufferedOutputStream(response.getOutputStream());
				
				String originalName = result.get("originalName");
				boolean ie = request.getHeader("User-Agent").indexOf("MSIE") > -1;
				if (ie){
					originalName = URLEncoder.encode(originalName, "utf-8");
				} else {
					originalName = new String(originalName.getBytes("UTF-8"), "iso-8859-1");
				}
				
				response.setContentType("application/octet-stream; charset=UTF-8");
				response.setHeader("Content-Disposition", "attachment; filename=\"" + originalName +"\"");
				response.setHeader("Content-Transfer-Encoding", "binary");
				response.setContentLength((int) file.length());				
				
				int i = 0;
				byte buffer[] = new byte[8192];
				while ((i = in.read(buffer)) != -1) { 
					out.write(buffer, 0, i);					
				}
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if(out != null){
						out.flush();
						out.close();
						return;
					}
					if(in != null) in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	@RequestMapping(value = "/error/404")
	public String login() 
	{
		logger.info("404 Error");
		
		return "/404";
	}
	
}
