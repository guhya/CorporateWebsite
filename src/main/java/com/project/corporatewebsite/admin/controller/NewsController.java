package com.project.corporatewebsite.admin.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.project.corporatewebsite.admin.service.FileService;
import com.project.corporatewebsite.admin.service.NewsService;
import com.project.corporatewebsite.admin.service.PagingService;
import com.project.corporatewebsite.admin.service.TermService;
import com.project.corporatewebsite.common.Constants;
import com.project.corporatewebsite.common.Util;
import com.project.corporatewebsite.common.VoHelper;
import com.project.corporatewebsite.validator.NewsValidator;
import com.project.corporatewebsite.vo.FileVo;
import com.project.corporatewebsite.vo.NewsVo;

@Controller
public class NewsController {
	private static final Logger logger = LoggerFactory.getLogger(NewsController.class);
	
	@Autowired
	private NewsService newsService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private PagingService pagingService;

	@Autowired
	private TermService termService;
	
	@Autowired
	private NewsVo newsVo;
	
	@Autowired
	private NewsValidator newsValidator;
	
	@RequestMapping(value = "/admin/news/list", method = RequestMethod.GET)
	public String list(Model model, HttpServletRequest request) 
	{
		logger.info("List");		
		
		Map<String, String>	pp = Util.buildPaginationUrlWithExtra(request, "category, sortColumn, sortOrder");
		newsVo = (NewsVo) Util.prepareVoForListing(newsVo, pp);
		newsVo.setCategory(pp.get("category"));
		newsVo.setSortColumn(pp.get("sortColumn"));
		newsVo.setSortOrder(pp.get("sortOrder"));		
		
		int totalRow	= newsService.countList(newsVo);
		List<Map<String, String>> newsList = newsService.list(newsVo);		
		List<Map<String, String>> catList = termService.selectCategories();
		
		String paging	= pagingService.buildPage(totalRow, newsVo.getPageSize(), Integer.parseInt(pp.get(Constants.PARAMETER_PAGE)), "", pp.get(Constants.PARAMETER_ALL));
		
		model.addAttribute(Constants.NEWS, 				newsList);
		model.addAttribute(Constants.CONTENT_PAGING,	paging);
		model.addAttribute(Constants.CONTENT_PARAM, 	pp);
		model.addAttribute(Constants.CATEGORY, 			catList);		
		
		return "/admin/news/list";
	}

	@RequestMapping(value = "/admin/news/detail", method = RequestMethod.GET)
	public String detail(Model model, HttpServletRequest request) 
	{
		logger.info("Detail");
		
		Map<String, String>	pp = Util.buildPaginationUrlWithExtra(request, "category, sortColumn, sortOrder");

		String seq = request.getParameter("seq");
		newsVo.setSeq(seq);
		
		Map<String, String> result = newsService.select(newsVo);
		
		model.addAttribute(Constants.NEWS, 				result);
		model.addAttribute(Constants.CONTENT_PARAM, 	pp);
		
		model.addAttribute("news", result);
		
		return "/admin/news/detail";
	}
	
	@RequestMapping(value = "/admin/news/write", method=RequestMethod.GET)
	public String register(Model model, HttpServletRequest request) 
	{
		logger.info("Write Form");
		
		Map<String, String>	pp = Util.buildPaginationUrlWithExtra(request, "category, sortColumn, sortOrder");
		
		List<Map<String, String>> catList = termService.selectCategories();
		List<Map<String, String>> tagList = termService.selectTags();
		
		model.addAttribute(Constants.ACTION, 			Constants.WRITE);
		model.addAttribute(Constants.CONTENT_PARAM, 	pp);
		model.addAttribute(Constants.CATEGORY, 			catList);
		model.addAttribute(Constants.TAG, 				tagList);
		
		return "/admin/news/form";
	}
	
	@RequestMapping(value = "/admin/news/edit", method=RequestMethod.GET)
	public String edit(Model model, HttpServletRequest request) 
	{
		logger.info("Edit Form");
		
		Map<String, String>	pp = Util.buildPaginationUrlWithExtra(request, "category");

		String seq = request.getParameter("seq");
		newsVo.setSeq(seq);
		
		Map<String, String> result = newsService.select(newsVo);
		List<Map<String, String>> catList = termService.selectCategories();
		List<Map<String, String>> tagList = termService.selectTags();
		
		model.addAttribute(Constants.NEWS, 				result);
		model.addAttribute(Constants.CONTENT_PARAM, 	pp);
		model.addAttribute(Constants.ACTION, 			Constants.EDIT);
		model.addAttribute(Constants.CATEGORY, 			catList);
		model.addAttribute(Constants.TAG, 				tagList);
		
		model.addAttribute("news", result);
		
		return "/admin/news/form";
	}
	
	@RequestMapping(value = "/admin/news/write", method=RequestMethod.POST)
	public String writeFormProcessor(Model model, 
									HttpServletRequest request, 
									@ModelAttribute("newsVo") NewsVo newsVo, 
									BindingResult bindingResult, 
									Principal principal) 
	{
		logger.info("Form Processor");
		
		List<Map<String, String>> catList = termService.selectCategories();
		List<Map<String, String>> tagList = termService.selectTags();

		List<Map<String, MultipartFile>> fileList = Util.getFilesFromRequest(request);
		String[] categories = Util.getCategoryFromRequest(request);
		String[] tags = Util.getTagsFromRequest(request);
		
		Map<String, String> errors = new HashMap<String, String>();		
		newsValidator.validateWithFile(newsVo, fileList, bindingResult, Constants.WRITE);
		
		if(bindingResult.hasErrors()){
			for(ObjectError err : bindingResult.getAllErrors()){
				errors.put(err.getCode(), err.getDefaultMessage());
			}
		}else{
			newsVo.setRegIp(request.getRemoteAddr());
			newsVo.setRegId(principal.getName());
			
			int success = newsService.insert(newsVo, fileList, categories, tags);	
			if(success > 0){			
				return "redirect:/admin/news/list?category="+newsVo.getCategory();
			}
		}
		
		Map<String, String>	pp = Util.buildPaginationUrl(request);
		
		model.addAttribute(Constants.NEWS, 				newsVo);
		model.addAttribute(Constants.CONTENT_PARAM, 	pp);
		model.addAttribute(Constants.ACTION, 			Constants.WRITE);
		model.addAttribute(Constants.CATEGORY, 			catList);
		model.addAttribute(Constants.TAG, 				tagList);
		model.addAttribute(Constants.ERRORS, 			errors);
		
		return "/admin/news/form";
	}		

	@RequestMapping(value = "/admin/news/edit", method=RequestMethod.POST)
	public String editFormProcessor(Model model, 
									HttpServletRequest request, 
									@ModelAttribute("newsVo") NewsVo newsVo, 
									BindingResult bindingResult, 
									Principal principal) 
	{
		logger.info("Form Processor");
		
		List<Map<String, String>> catList = termService.selectCategories();
		List<Map<String, String>> tagList = termService.selectTags();

		Map<String, String>	pp = Util.buildPaginationUrl(request);

		List<Map<String, MultipartFile>> fileList = Util.getFilesFromRequest(request);		
		String[] categories = Util.getCategoryFromRequest(request);
		String[] tags = Util.getTagsFromRequest(request);
		
		Map<String, String> errors = new HashMap<String, String>();		
		newsValidator.validateWithFile(newsVo, fileList, bindingResult, Constants.EDIT);
		
		if(bindingResult.hasErrors()){
			for(ObjectError err : bindingResult.getAllErrors()){
				errors.put(err.getCode(), err.getDefaultMessage());
			}
		}else{
			this.deleteFile(Util.getDeletedFilesFromRequest(request), newsVo.getSeq(), request, principal);
			newsVo.setModIp(request.getRemoteAddr());
			newsVo.setModId(principal.getName());
			
			int success = newsService.update(newsVo, fileList, categories, tags);	
			if(success == 1){			
				return "redirect:/admin/news/detail?"+pp.get(Constants.PARAMETER_RETURN_PARAM);
			}
		}
		
		model.addAttribute(Constants.NEWS, 				newsVo);
		model.addAttribute(Constants.CONTENT_PARAM, 	pp);
		model.addAttribute(Constants.ACTION, 			Constants.EDIT);
		model.addAttribute(Constants.CATEGORY, 			catList);
		model.addAttribute(Constants.TAG, 				tagList);
		model.addAttribute(Constants.ERRORS, 			errors);
		
		return "/admin/news/form";
	}	
	
	@RequestMapping(value = "/admin/news/delete", method=RequestMethod.POST)
	public String delete(Model model, 
			HttpServletRequest request, 
			@ModelAttribute("newsVo") NewsVo newsVo, 
			BindingResult bindingResult, 
			Principal principal) 
	{
		logger.info("Delete");
		
		Map<String, String>	pp = Util.buildPaginationUrlWithExtra(request, "category, sortColumn, sortOrder");
		
		newsVo.setRegIp(request.getRemoteAddr());
		newsVo.setRegId(principal.getName());
		newsService.delete(newsVo);
		
		return "redirect:/admin/news/list?"+pp.get(Constants.PARAMETER_LINK);
	}
	
	private void deleteFile(String[] deletedFiles, String ownerSeq, HttpServletRequest request, Principal principal){		
		for(String deletedSeq : deletedFiles){
			FileVo fileVo = VoHelper.prepareFileVo(request.getRemoteAddr(), principal.getName(), null, "", "", "");
			fileVo.setSeq(deletedSeq);
			fileVo.setOwnerSeq(ownerSeq);
			
			fileService.delete(fileVo);
		}
	}
	
	
	
	
	@RequestMapping(value = "/admin/news/writeMulti", method=RequestMethod.GET)
	public String registerMulti(Model model, HttpServletRequest request) 
	{
		logger.info("Write Form Multi Image");
		
		Map<String, String>	pp = Util.buildPaginationUrlWithExtra(request, "category, sortColumn, sortOrder");
		
		List<Map<String, String>> catList = termService.selectCategories();
		List<Map<String, String>> tagList = termService.selectTags();
		
		model.addAttribute(Constants.ACTION, 			Constants.WRITE);
		model.addAttribute(Constants.CONTENT_PARAM, 	pp);
		model.addAttribute(Constants.CATEGORY, 			catList);
		model.addAttribute(Constants.TAG, 				tagList);
		
		return "/admin/news/formMulti";
	}
	
	@RequestMapping(value = "/admin/news/writeMulti", method=RequestMethod.POST)
	public String writeMultiFormProcessor(Model model, 
									HttpServletRequest request, 
									@ModelAttribute("newsVo") NewsVo newsVo, 
									BindingResult bindingResult, 
									Principal principal) 
	{
		logger.info("Form Processor");
		
		List<Map<String, String>> catList = termService.selectCategories();
		List<Map<String, String>> tagList = termService.selectTags();

		List<Map<String, MultipartFile>> fileList = Util.getFilesFromRequest(request);
		String[] categories = Util.getCategoryFromRequest(request);
		String[] tags = Util.getTagsFromRequest(request);
		
		Map<String, String> errors = new HashMap<String, String>();		
		newsValidator.validateWithFile(newsVo, fileList, bindingResult, Constants.WRITE);
		
		if(bindingResult.hasErrors()){
			for(ObjectError err : bindingResult.getAllErrors()){
				errors.put(err.getCode(), err.getDefaultMessage());
			}
		}else{
			newsVo.setRegIp(request.getRemoteAddr());
			newsVo.setRegId(principal.getName());
			
			int success = newsService.insert(newsVo, fileList, categories, tags);	
			if(success > 0){			
				return "redirect:/admin/news/list?category="+newsVo.getCategory();
			}
		}
		
		Map<String, String>	pp = Util.buildPaginationUrl(request);
		
		model.addAttribute(Constants.NEWS, 				newsVo);
		model.addAttribute(Constants.CONTENT_PARAM, 	pp);
		model.addAttribute(Constants.ACTION, 			Constants.WRITE);
		model.addAttribute(Constants.CATEGORY, 			catList);
		model.addAttribute(Constants.TAG, 				tagList);
		model.addAttribute(Constants.ERRORS, 			errors);
		
		return "/admin/news/form";
	}			
}
