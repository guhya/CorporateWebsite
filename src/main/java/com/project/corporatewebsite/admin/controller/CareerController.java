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
import com.project.corporatewebsite.admin.service.CareerService;
import com.project.corporatewebsite.admin.service.PagingService;
import com.project.corporatewebsite.admin.service.TermService;
import com.project.corporatewebsite.common.Constants;
import com.project.corporatewebsite.common.Util;
import com.project.corporatewebsite.common.VoHelper;
import com.project.corporatewebsite.validator.CareerValidator;
import com.project.corporatewebsite.vo.FileVo;
import com.project.corporatewebsite.vo.CareerVo;

@Controller
public class CareerController {
	private static final Logger logger = LoggerFactory.getLogger(CareerController.class);
	
	@Autowired
	private CareerService careerService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private PagingService pagingService;

	@Autowired
	private TermService termService;
	
	@Autowired
	private CareerVo careerVo;
	
	@Autowired
	private CareerValidator careerValidator;
	
	@RequestMapping(value = "/admin/career/list", method = RequestMethod.GET)
	public String list(Model model, HttpServletRequest request) 
	{
		logger.info("List");		
		
		Map<String, String>	pp = Util.buildPaginationUrlWithExtra(request, "category, startDate, endDate, sortColumn, sortOrder");
		careerVo = (CareerVo) Util.prepareVoForListing(careerVo, pp);
		careerVo.setCategory(pp.get("category"));
		careerVo.setStartDate(pp.get("startDate"));
		careerVo.setEndDate(pp.get("endDate"));
		careerVo.setSortColumn(pp.get("sortColumn"));
		careerVo.setSortOrder(pp.get("sortOrder"));
		
		int totalRow	= careerService.countList(careerVo);
		List<Map<String, String>> careerList = careerService.list(careerVo);		
		List<Map<String, String>> catList = termService.selectCategories();
		
		String paging	= pagingService.buildPage(totalRow, careerVo.getPageSize(), Integer.parseInt(pp.get(Constants.PARAMETER_PAGE)), "", pp.get(Constants.PARAMETER_ALL));
		
		model.addAttribute(Constants.CAREER, 			careerList);
		model.addAttribute(Constants.CONTENT_PAGING,	paging);
		model.addAttribute(Constants.CONTENT_PARAM, 	pp);
		model.addAttribute(Constants.CATEGORY, 			catList);		
		
		return "/admin/career/list";
	}

	@RequestMapping(value = "/admin/career/detail", method = RequestMethod.GET)
	public String detail(Model model, HttpServletRequest request) 
	{
		logger.info("Detail");
		
		Map<String, String>	pp = Util.buildPaginationUrlWithExtra(request, "category, startDate, endDate, sortColumn, sortOrder");

		String seq = request.getParameter("seq");
		careerVo.setSeq(seq);
		
		Map<String, String> result = careerService.select(careerVo);
		
		model.addAttribute(Constants.CAREER, 			result);
		model.addAttribute(Constants.CONTENT_PARAM, 	pp);
		
		model.addAttribute("career", result);
		
		return "/admin/career/detail";
	}
	
	@RequestMapping(value = "/admin/career/write", method=RequestMethod.GET)
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
		
		return "/admin/career/form";
	}
	
	@RequestMapping(value = "/admin/career/edit", method=RequestMethod.GET)
	public String edit(Model model, HttpServletRequest request) 
	{
		logger.info("Edit Form");
		
		Map<String, String>	pp = Util.buildPaginationUrlWithExtra(request, "category, startDate, endDate, sortColumn, sortOrder");

		String seq = request.getParameter("seq");
		careerVo.setSeq(seq);
		
		Map<String, String> result = careerService.select(careerVo);
		List<Map<String, String>> catList = termService.selectCategories();
		List<Map<String, String>> tagList = termService.selectTags();
		
		model.addAttribute(Constants.CAREER, 			result);
		model.addAttribute(Constants.CONTENT_PARAM, 	pp);
		model.addAttribute(Constants.ACTION, 			Constants.EDIT);
		model.addAttribute(Constants.CATEGORY, 			catList);
		model.addAttribute(Constants.TAG, 				tagList);
		
		model.addAttribute("career", result);
		
		return "/admin/career/form";
	}
	
	@RequestMapping(value = "/admin/career/write", method=RequestMethod.POST)
	public String writeFormProcessor(Model model, 
									HttpServletRequest request, 
									@ModelAttribute("careerVo") CareerVo careerVo, 
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
		careerValidator.validateWithFile(careerVo, fileList, bindingResult, Constants.WRITE);
		
		if(bindingResult.hasErrors()){
			for(ObjectError err : bindingResult.getAllErrors()){
				errors.put(err.getCode(), err.getDefaultMessage());
			}
		}else{
			careerVo.setRegIp(request.getRemoteAddr());
			careerVo.setRegId(principal.getName());
			
			int success = careerService.insert(careerVo, fileList, categories, tags);	
			if(success > 0){			
				return "redirect:/admin/career/list";
			}
		}
		
		Map<String, String>	pp = Util.buildPaginationUrl(request);
		
		model.addAttribute(Constants.CAREER, 			careerVo);
		model.addAttribute(Constants.CONTENT_PARAM, 	pp);
		model.addAttribute(Constants.ACTION, 			Constants.WRITE);
		model.addAttribute(Constants.CATEGORY, 			catList);
		model.addAttribute(Constants.TAG, 				tagList);
		model.addAttribute(Constants.ERRORS, 			errors);
		
		return "/admin/career/form";
	}		

	@RequestMapping(value = "/admin/career/edit", method=RequestMethod.POST)
	public String editFormProcessor(Model model, 
									HttpServletRequest request, 
									@ModelAttribute("careerVo") CareerVo careerVo, 
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
		careerValidator.validateWithFile(careerVo, fileList, bindingResult, Constants.EDIT);
		
		if(bindingResult.hasErrors()){
			for(ObjectError err : bindingResult.getAllErrors()){
				errors.put(err.getCode(), err.getDefaultMessage());
			}
		}else{
			this.deleteFile(Util.getDeletedFilesFromRequest(request), careerVo.getSeq(), request, principal);
			careerVo.setModIp(request.getRemoteAddr());
			careerVo.setModId(principal.getName());
			
			int success = careerService.update(careerVo, fileList, categories, tags);	
			if(success == 1){			
				return "redirect:/admin/career/detail?"+pp.get(Constants.PARAMETER_RETURN_PARAM);
			}
		}
		
		model.addAttribute(Constants.CAREER, 			careerVo);
		model.addAttribute(Constants.CONTENT_PARAM, 	pp);
		model.addAttribute(Constants.ACTION, 			Constants.EDIT);
		model.addAttribute(Constants.CATEGORY, 			catList);
		model.addAttribute(Constants.TAG, 				tagList);
		model.addAttribute(Constants.ERRORS, 			errors);
		
		return "/admin/career/form";
	}	
	
	@RequestMapping(value = "/admin/career/delete", method=RequestMethod.POST)
	public String delete(Model model, 
			HttpServletRequest request, 
			@ModelAttribute("careerVo") CareerVo careerVo, 
			BindingResult bindingResult, 
			Principal principal) 
	{
		logger.info("Delete");
		
		Map<String, String>	pp = Util.buildPaginationUrlWithExtra(request, "category, startDate, endDate, sortColumn, sortOrder");
		
		careerVo.setRegIp(request.getRemoteAddr());
		careerVo.setRegId(principal.getName());
		careerService.delete(careerVo);
		
		return "redirect:/admin/career/list?"+pp.get(Constants.PARAMETER_LINK);
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
