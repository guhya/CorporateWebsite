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
import com.project.corporatewebsite.admin.service.PagingService;
import com.project.corporatewebsite.admin.service.SettingService;
import com.project.corporatewebsite.common.Constants;
import com.project.corporatewebsite.common.Util;
import com.project.corporatewebsite.common.VoHelper;
import com.project.corporatewebsite.validator.SettingValidator;
import com.project.corporatewebsite.vo.FileVo;
import com.project.corporatewebsite.vo.SettingVo;

@Controller
public class SettingController {
	private static final Logger logger = LoggerFactory.getLogger(SettingController.class);
	
	@Autowired
	private SettingService settingService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private PagingService pagingService;

	@Autowired
	private SettingVo settingVo;
	
	@Autowired
	private SettingValidator settingValidator;
	
	@RequestMapping(value = "/admin/setting/list", method = RequestMethod.GET)
	public String list(Model model, HttpServletRequest request) 
	{
		logger.info("List");		
		
		Map<String, String>	pp = Util.buildPaginationUrlWithExtra(request, "startDate, endDate, sortColumn, sortOrder");
		settingVo = (SettingVo) Util.prepareVoForListing(settingVo, pp);
		settingVo.setStartDate(pp.get("startDate"));
		settingVo.setEndDate(pp.get("endDate"));
		settingVo.setSortColumn(pp.get("sortColumn"));
		settingVo.setSortOrder(pp.get("sortOrder"));
		
		int totalRow	= settingService.countList(settingVo);
		List<Map<String, String>> settingList = settingService.list(settingVo);		
		
		String paging	= pagingService.buildPage(totalRow, settingVo.getPageSize(), Integer.parseInt(pp.get(Constants.PARAMETER_PAGE)), "", pp.get(Constants.PARAMETER_ALL));
		
		model.addAttribute(Constants.SETTING, 			settingList);
		model.addAttribute(Constants.CONTENT_PAGING,	paging);
		model.addAttribute(Constants.CONTENT_PARAM, 	pp);
		
		return "/admin/setting/list";
	}

	@RequestMapping(value = "/admin/setting/detail", method = RequestMethod.GET)
	public String detail(Model model, HttpServletRequest request) 
	{
		logger.info("Detail");
		
		Map<String, String>	pp = Util.buildPaginationUrlWithExtra(request, "category, startDate, endDate, sortColumn, sortOrder");

		String seq = request.getParameter("seq");
		settingVo.setSeq(seq);
		
		Map<String, String> result = settingService.select(settingVo);
		
		model.addAttribute(Constants.SETTING, 			result);
		model.addAttribute(Constants.CONTENT_PARAM, 	pp);
		
		model.addAttribute("setting", result);
		
		return "/admin/setting/detail";
	}
	
	@RequestMapping(value = "/admin/setting/write", method=RequestMethod.GET)
	public String register(Model model, HttpServletRequest request) 
	{
		logger.info("Write Form");
		
		Map<String, String>	pp = Util.buildPaginationUrlWithExtra(request, "category, startDate, endDate, sortColumn, sortOrder");
		
		model.addAttribute(Constants.ACTION, 			Constants.WRITE);
		model.addAttribute(Constants.CONTENT_PARAM, 	pp);
		
		return "/admin/setting/form";
	}
	
	@RequestMapping(value = "/admin/setting/edit", method=RequestMethod.GET)
	public String edit(Model model, HttpServletRequest request) 
	{
		logger.info("Edit Form");
		
		Map<String, String>	pp = Util.buildPaginationUrlWithExtra(request, "category, startDate, endDate, sortColumn, sortOrder");

		String seq = request.getParameter("seq");
		settingVo.setSeq(seq);
		
		Map<String, String> result = settingService.select(settingVo);
		
		model.addAttribute(Constants.SETTING, 			result);
		model.addAttribute(Constants.CONTENT_PARAM, 	pp);
		model.addAttribute(Constants.ACTION, 			Constants.EDIT);
		
		model.addAttribute("setting", result);
		
		return "/admin/setting/form";
	}
	
	@RequestMapping(value = "/admin/setting/write", method=RequestMethod.POST)
	public String writeFormProcessor(Model model, 
									HttpServletRequest request, 
									@ModelAttribute("settingVo") SettingVo settingVo, 
									BindingResult bindingResult, 
									Principal principal) 
	{
		logger.info("Form Processor");
		
		List<Map<String, MultipartFile>> fileList = Util.getFilesFromRequest(request);
		
		Map<String, String> errors = new HashMap<String, String>();		
		settingValidator.validateWithFile(settingVo, fileList, bindingResult, Constants.WRITE);
		
		if(bindingResult.hasErrors()){
			for(ObjectError err : bindingResult.getAllErrors()){
				errors.put(err.getCode(), err.getDefaultMessage());
			}
		}else{
			settingVo.setRegIp(request.getRemoteAddr());
			settingVo.setRegId(principal.getName());
			
			int success = settingService.insert(settingVo, fileList);	
			if(success > 0){			
				return "redirect:/admin/setting/list";
			}
		}
		
		Map<String, String>	pp = Util.buildPaginationUrl(request);
		
		model.addAttribute(Constants.SETTING, 			settingVo);
		model.addAttribute(Constants.CONTENT_PARAM, 	pp);
		model.addAttribute(Constants.ACTION, 			Constants.WRITE);
		model.addAttribute(Constants.ERRORS, 			errors);
		
		return "/admin/setting/form";
	}		

	@RequestMapping(value = "/admin/setting/edit", method=RequestMethod.POST)
	public String editFormProcessor(Model model, 
									HttpServletRequest request, 
									@ModelAttribute("settingVo") SettingVo settingVo, 
									BindingResult bindingResult, 
									Principal principal) 
	{
		logger.info("Form Processor");
		
		Map<String, String>	pp = Util.buildPaginationUrl(request);

		List<Map<String, MultipartFile>> fileList = Util.getFilesFromRequest(request);		
		
		Map<String, String> errors = new HashMap<String, String>();		
		settingValidator.validateWithFile(settingVo, fileList, bindingResult, Constants.EDIT);
		
		if(bindingResult.hasErrors()){
			for(ObjectError err : bindingResult.getAllErrors()){
				errors.put(err.getCode(), err.getDefaultMessage());
			}
		}else{
			this.deleteFile(Util.getDeletedFilesFromRequest(request), settingVo.getSeq(), request, principal);
			settingVo.setModIp(request.getRemoteAddr());
			settingVo.setModId(principal.getName());
			
			int success = settingService.update(settingVo, fileList);	
			if(success == 1){			
				return "redirect:/admin/setting/detail?"+pp.get(Constants.PARAMETER_RETURN_PARAM);
			}
		}
		
		model.addAttribute(Constants.SETTING, 			settingVo);
		model.addAttribute(Constants.CONTENT_PARAM, 	pp);
		model.addAttribute(Constants.ACTION, 			Constants.EDIT);
		model.addAttribute(Constants.ERRORS, 			errors);
		
		return "/admin/setting/form";
	}	
	
	@RequestMapping(value = "/admin/setting/delete", method=RequestMethod.POST)
	public String delete(Model model, 
			HttpServletRequest request, 
			@ModelAttribute("settingVo") SettingVo settingVo, 
			BindingResult bindingResult, 
			Principal principal) 
	{
		logger.info("Delete");
		
		Map<String, String>	pp = Util.buildPaginationUrlWithExtra(request, "category, startDate, endDate, sortColumn, sortOrder");
		
		settingVo.setRegIp(request.getRemoteAddr());
		settingVo.setRegId(principal.getName());
		settingService.delete(settingVo);
		
		return "redirect:/admin/setting/list?"+pp.get(Constants.PARAMETER_LINK);
	}
	
	@RequestMapping(value = "/admin/setting/setDefault", method=RequestMethod.GET)
	public String setDefault(Model model, 
			HttpServletRequest request, 
			@ModelAttribute("settingVo") SettingVo settingVo, 
			BindingResult bindingResult, 
			Principal principal) 
	{
		logger.info("Set Default");
		
		Map<String, String>	pp = Util.buildPaginationUrlWithExtra(request, "category, startDate, endDate, sortColumn, sortOrder");
		
		String seq = request.getParameter("seq");
		settingVo.setSeq(seq);
		settingVo.setRegIp(request.getRemoteAddr());
		settingVo.setRegId(principal.getName());
		settingService.setAsDefault(settingVo);
		
		return "redirect:/admin/setting/list?"+pp.get(Constants.PARAMETER_LINK);
	}
	
	@RequestMapping(value = "/admin/setting/unsetDefault", method=RequestMethod.GET)
	public String unsetDefault(Model model, 
			HttpServletRequest request, 
			@ModelAttribute("settingVo") SettingVo settingVo, 
			BindingResult bindingResult, 
			Principal principal) 
	{
		logger.info("Unset Default");
		
		Map<String, String>	pp = Util.buildPaginationUrlWithExtra(request, "category, startDate, endDate, sortColumn, sortOrder");
		
		String seq = request.getParameter("seq");
		settingVo.setSeq(seq);
		settingVo.setRegIp(request.getRemoteAddr());
		settingVo.setRegId(principal.getName());
		settingService.unsetDefault(settingVo);
		
		return "redirect:/admin/setting/list?"+pp.get(Constants.PARAMETER_LINK);
	}
	
	@RequestMapping(value = "/admin/setting/setCurrent", method=RequestMethod.GET)
	public String setCurrent(Model model, 
			HttpServletRequest request, 
			@ModelAttribute("settingVo") SettingVo settingVo, 
			BindingResult bindingResult, 
			Principal principal) 
	{
		logger.info("Set Current");
		
		Map<String, String>	pp = Util.buildPaginationUrlWithExtra(request, "category, startDate, endDate, sortColumn, sortOrder");
		
		String seq = request.getParameter("seq");
		settingVo.setSeq(seq);
		settingVo.setRegIp(request.getRemoteAddr());
		settingVo.setRegId(principal.getName());
		settingService.setAsEvent(settingVo);
		
		return "redirect:/admin/setting/list?"+pp.get(Constants.PARAMETER_LINK);
	}

	@RequestMapping(value = "/admin/setting/unsetCurrent", method=RequestMethod.GET)
	public String unsetCurrent(Model model, 
			HttpServletRequest request, 
			@ModelAttribute("settingVo") SettingVo settingVo, 
			BindingResult bindingResult, 
			Principal principal) 
	{
		logger.info("Unset Current");
		
		Map<String, String>	pp = Util.buildPaginationUrlWithExtra(request, "category, startDate, endDate, sortColumn, sortOrder");
		
		String seq = request.getParameter("seq");
		settingVo.setSeq(seq);
		settingVo.setRegIp(request.getRemoteAddr());
		settingVo.setRegId(principal.getName());
		settingService.clearEvent(settingVo);
		
		return "redirect:/admin/setting/list?"+pp.get(Constants.PARAMETER_LINK);
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
