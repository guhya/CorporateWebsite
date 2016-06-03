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
import com.project.corporatewebsite.admin.service.EventService;
import com.project.corporatewebsite.admin.service.PagingService;
import com.project.corporatewebsite.admin.service.TermService;
import com.project.corporatewebsite.common.Constants;
import com.project.corporatewebsite.common.Util;
import com.project.corporatewebsite.common.VoHelper;
import com.project.corporatewebsite.validator.EventValidator;
import com.project.corporatewebsite.vo.FileVo;
import com.project.corporatewebsite.vo.EventVo;

@Controller
public class EventController {
	private static final Logger logger = LoggerFactory.getLogger(EventController.class);
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private PagingService pagingService;

	@Autowired
	private TermService termService;
	
	@Autowired
	private EventVo eventVo;
	
	@Autowired
	private EventValidator eventValidator;
	
	@RequestMapping(value = "/admin/event/list", method = RequestMethod.GET)
	public String list(Model model, HttpServletRequest request) 
	{
		logger.info("List");		
		
		Map<String, String>	pp = Util.buildPaginationUrlWithExtra(request, "category, startDate, endDate, sortColumn, sortOrder");
		eventVo = (EventVo) Util.prepareVoForListing(eventVo, pp);
		eventVo.setCategory(pp.get("category"));
		eventVo.setStartDate(pp.get("startDate"));
		eventVo.setEndDate(pp.get("endDate"));
		eventVo.setSortColumn(pp.get("sortColumn"));
		eventVo.setSortOrder(pp.get("sortOrder"));
		
		int totalRow	= eventService.countList(eventVo);
		List<Map<String, String>> eventList = eventService.list(eventVo);		
		List<Map<String, String>> catList = termService.selectCategories();
		
		String paging	= pagingService.buildPage(totalRow, eventVo.getPageSize(), Integer.parseInt(pp.get(Constants.PARAMETER_PAGE)), "", pp.get(Constants.PARAMETER_ALL));
		
		model.addAttribute(Constants.EVENT, 			eventList);
		model.addAttribute(Constants.CONTENT_PAGING,	paging);
		model.addAttribute(Constants.CONTENT_PARAM, 	pp);
		model.addAttribute(Constants.CATEGORY, 			catList);		
		
		return "/admin/event/list";
	}

	@RequestMapping(value = "/admin/event/detail", method = RequestMethod.GET)
	public String detail(Model model, HttpServletRequest request) 
	{
		logger.info("Detail");
		
		Map<String, String>	pp = Util.buildPaginationUrlWithExtra(request, "category, startDate, endDate, sortColumn, sortOrder");

		String seq = request.getParameter("seq");
		eventVo.setSeq(seq);
		
		Map<String, String> result = eventService.select(eventVo);
		
		model.addAttribute(Constants.EVENT, 			result);
		model.addAttribute(Constants.CONTENT_PARAM, 	pp);
		
		model.addAttribute("event", result);
		
		return "/admin/event/detail";
	}
	
	@RequestMapping(value = "/admin/event/write", method=RequestMethod.GET)
	public String register(Model model, HttpServletRequest request) 
	{
		logger.info("Write Form");
		
		Map<String, String>	pp = Util.buildPaginationUrlWithExtra(request, "category, startDate, endDate, sortColumn, sortOrder");
		
		List<Map<String, String>> catList = termService.selectCategories();
		List<Map<String, String>> tagList = termService.selectTags();
		
		model.addAttribute(Constants.ACTION, 			Constants.WRITE);
		model.addAttribute(Constants.CONTENT_PARAM, 	pp);
		model.addAttribute(Constants.CATEGORY, 			catList);
		model.addAttribute(Constants.TAG, 				tagList);
		
		return "/admin/event/form";
	}
	
	@RequestMapping(value = "/admin/event/edit", method=RequestMethod.GET)
	public String edit(Model model, HttpServletRequest request) 
	{
		logger.info("Edit Form");
		
		Map<String, String>	pp = Util.buildPaginationUrlWithExtra(request, "category, startDate, endDate, sortColumn, sortOrder");

		String seq = request.getParameter("seq");
		eventVo.setSeq(seq);
		
		Map<String, String> result = eventService.select(eventVo);
		List<Map<String, String>> catList = termService.selectCategories();
		List<Map<String, String>> tagList = termService.selectTags();
		
		model.addAttribute(Constants.EVENT, 			result);
		model.addAttribute(Constants.CONTENT_PARAM, 	pp);
		model.addAttribute(Constants.ACTION, 			Constants.EDIT);
		model.addAttribute(Constants.CATEGORY, 			catList);
		model.addAttribute(Constants.TAG, 				tagList);
		
		model.addAttribute("event", result);
		
		return "/admin/event/form";
	}
	
	@RequestMapping(value = "/admin/event/write", method=RequestMethod.POST)
	public String writeFormProcessor(Model model, 
									HttpServletRequest request, 
									@ModelAttribute("eventVo") EventVo eventVo, 
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
		eventValidator.validateWithFile(eventVo, fileList, bindingResult, Constants.WRITE);
		
		if(bindingResult.hasErrors()){
			for(ObjectError err : bindingResult.getAllErrors()){
				errors.put(err.getCode(), err.getDefaultMessage());
			}
		}else{
			eventVo.setRegIp(request.getRemoteAddr());
			eventVo.setRegId(principal.getName());
			
			int success = eventService.insert(eventVo, fileList, categories, tags);	
			if(success > 0){			
				return "redirect:/admin/event/list";
			}
		}
		
		Map<String, String>	pp = Util.buildPaginationUrl(request);
		
		model.addAttribute(Constants.EVENT, 			eventVo);
		model.addAttribute(Constants.CONTENT_PARAM, 	pp);
		model.addAttribute(Constants.ACTION, 			Constants.WRITE);
		model.addAttribute(Constants.CATEGORY, 			catList);
		model.addAttribute(Constants.TAG, 				tagList);
		model.addAttribute(Constants.ERRORS, 			errors);
		
		return "/admin/event/form";
	}		

	@RequestMapping(value = "/admin/event/edit", method=RequestMethod.POST)
	public String editFormProcessor(Model model, 
									HttpServletRequest request, 
									@ModelAttribute("eventVo") EventVo eventVo, 
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
		eventValidator.validateWithFile(eventVo, fileList, bindingResult, Constants.EDIT);
		
		if(bindingResult.hasErrors()){
			for(ObjectError err : bindingResult.getAllErrors()){
				errors.put(err.getCode(), err.getDefaultMessage());
			}
		}else{
			this.deleteFile(Util.getDeletedFilesFromRequest(request), eventVo.getSeq(), request, principal);
			eventVo.setModIp(request.getRemoteAddr());
			eventVo.setModId(principal.getName());
			
			int success = eventService.update(eventVo, fileList, categories, tags);	
			if(success == 1){			
				return "redirect:/admin/event/detail?"+pp.get(Constants.PARAMETER_RETURN_PARAM);
			}
		}
		
		model.addAttribute(Constants.EVENT, 			eventVo);
		model.addAttribute(Constants.CONTENT_PARAM, 	pp);
		model.addAttribute(Constants.ACTION, 			Constants.EDIT);
		model.addAttribute(Constants.CATEGORY, 			catList);
		model.addAttribute(Constants.TAG, 				tagList);
		model.addAttribute(Constants.ERRORS, 			errors);
		
		return "/admin/event/form";
	}	
	
	@RequestMapping(value = "/admin/event/delete", method=RequestMethod.POST)
	public String delete(Model model, 
			HttpServletRequest request, 
			@ModelAttribute("eventVo") EventVo eventVo, 
			BindingResult bindingResult, 
			Principal principal) 
	{
		logger.info("Delete");
		
		Map<String, String>	pp = Util.buildPaginationUrlWithExtra(request, "category, startDate, endDate, sortColumn, sortOrder");
		
		eventVo.setRegIp(request.getRemoteAddr());
		eventVo.setRegId(principal.getName());
		eventService.delete(eventVo);
		
		return "redirect:/admin/event/list?"+pp.get(Constants.PARAMETER_LINK);
	}
	
	private void deleteFile(String[] deletedFiles, String ownerSeq, HttpServletRequest request, Principal principal){		
		for(String deletedSeq : deletedFiles){
			FileVo fileVo = VoHelper.prepareFileVo(request.getRemoteAddr(), principal.getName(), null, "", "", "");
			fileVo.setSeq(deletedSeq);
			fileVo.setOwnerSeq(ownerSeq);
			
			fileService.delete(fileVo);
		}
	}
}
