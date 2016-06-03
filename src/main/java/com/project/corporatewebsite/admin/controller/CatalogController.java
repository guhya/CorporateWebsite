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
import com.project.corporatewebsite.admin.service.CatalogService;
import com.project.corporatewebsite.admin.service.PagingService;
import com.project.corporatewebsite.admin.service.TermService;
import com.project.corporatewebsite.common.Constants;
import com.project.corporatewebsite.common.Util;
import com.project.corporatewebsite.common.VoHelper;
import com.project.corporatewebsite.validator.CatalogValidator;
import com.project.corporatewebsite.vo.FileVo;
import com.project.corporatewebsite.vo.CatalogVo;

@Controller
public class CatalogController {
	private static final Logger logger = LoggerFactory.getLogger(CatalogController.class);
	
	@Autowired
	private CatalogService catalogService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private PagingService pagingService;

	@Autowired
	private TermService termService;
	
	@Autowired
	private CatalogVo catalogVo;
	
	@Autowired
	private CatalogValidator catalogValidator;
	
	@RequestMapping(value = "/admin/catalog/list", method = RequestMethod.GET)
	public String list(Model model, HttpServletRequest request, @ModelAttribute("catalogVo") CatalogVo catalogVo) 
	{
		logger.info("List");		
		
		Map<String, String>	pp = Util.buildPaginationUrlWithExtra(request, "category, sortColumn, sortOrder");
		catalogVo = (CatalogVo) Util.prepareVoForListing(catalogVo, pp);
		catalogVo.setCategory(pp.get("category"));
		catalogVo.setSortColumn(pp.get("sortColumn"));
		catalogVo.setSortOrder(pp.get("sortOrder"));		
		
		int totalRow	= catalogService.countList(catalogVo);
		List<Map<String, String>> catalogList = catalogService.list(catalogVo);		
		List<Map<String, String>> catList = termService.selectCategories();
		
		String paging	= pagingService.buildPage(totalRow, catalogVo.getPageSize(), Integer.parseInt(pp.get(Constants.PARAMETER_PAGE)), "", pp.get(Constants.PARAMETER_ALL));
		
		model.addAttribute(Constants.CATALOG, 			catalogList);
		model.addAttribute(Constants.CONTENT_PAGING,	paging);
		model.addAttribute(Constants.CONTENT_PARAM, 	pp);
		model.addAttribute(Constants.CATEGORY, 			catList);		
		
		return "/admin/catalog/list";
	}

	@RequestMapping(value = "/admin/catalog/detail", method = RequestMethod.GET)
	public String detail(Model model, HttpServletRequest request) 
	{
		logger.info("Detail");
		
		Map<String, String>	pp = Util.buildPaginationUrlWithExtra(request, "category, sortColumn, sortOrder");

		String seq = request.getParameter("seq");
		catalogVo.setSeq(seq);
		
		Map<String, String> result = catalogService.select(catalogVo);
		
		model.addAttribute(Constants.CATALOG, 			result);
		model.addAttribute(Constants.CONTENT_PARAM, 	pp);
		
		model.addAttribute("catalog", result);
		
		return "/admin/catalog/detail";
	}
	
	@RequestMapping(value = "/admin/catalog/write", method=RequestMethod.GET)
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
		
		return "/admin/catalog/form";
	}
	
	@RequestMapping(value = "/admin/catalog/edit", method=RequestMethod.GET)
	public String edit(Model model, HttpServletRequest request) 
	{
		logger.info("Edit Form");
		
		Map<String, String>	pp = Util.buildPaginationUrlWithExtra(request, "category");

		String seq = request.getParameter("seq");
		catalogVo.setSeq(seq);
		
		Map<String, String> result = catalogService.select(catalogVo);
		List<Map<String, String>> catList = termService.selectCategories();
		List<Map<String, String>> tagList = termService.selectTags();
		
		model.addAttribute(Constants.CATALOG, 			result);
		model.addAttribute(Constants.CONTENT_PARAM, 	pp);
		model.addAttribute(Constants.ACTION, 			Constants.EDIT);
		model.addAttribute(Constants.CATEGORY, 			catList);
		model.addAttribute(Constants.TAG, 				tagList);
		
		model.addAttribute("catalog", result);
		
		return "/admin/catalog/form";
	}
	
	@RequestMapping(value = "/admin/catalog/write", method=RequestMethod.POST)
	public String writeFormProcessor(Model model, 
									HttpServletRequest request, 
									@ModelAttribute("catalogVo") CatalogVo catalogVo, 
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
		catalogValidator.validateWithFile(catalogVo, fileList, bindingResult, Constants.WRITE);
		
		if(bindingResult.hasErrors()){
			for(ObjectError err : bindingResult.getAllErrors()){
				errors.put(err.getCode(), err.getDefaultMessage());
			}
		}else{
			catalogVo.setRegIp(request.getRemoteAddr());
			catalogVo.setRegId(principal.getName());
			
			int success = catalogService.insert(catalogVo, fileList, categories, tags);	
			if(success > 0){			
				return "redirect:/admin/catalog/list?category="+catalogVo.getCategory();
			}
		}
		
		Map<String, String>	pp = Util.buildPaginationUrl(request);
		
		model.addAttribute(Constants.CATALOG, 			catalogVo);
		model.addAttribute(Constants.CONTENT_PARAM, 	pp);
		model.addAttribute(Constants.ACTION, 			Constants.WRITE);
		model.addAttribute(Constants.CATEGORY, 			catList);
		model.addAttribute(Constants.TAG, 				tagList);
		model.addAttribute(Constants.ERRORS, 			errors);
		
		return "/admin/catalog/form";
	}		

	@RequestMapping(value = "/admin/catalog/edit", method=RequestMethod.POST)
	public String editFormProcessor(Model model, 
									HttpServletRequest request, 
									@ModelAttribute("catalogVo") CatalogVo catalogVo, 
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
		catalogValidator.validateWithFile(catalogVo, fileList, bindingResult, Constants.EDIT);
		
		if(bindingResult.hasErrors()){
			for(ObjectError err : bindingResult.getAllErrors()){
				errors.put(err.getCode(), err.getDefaultMessage());
			}
		}else{
			this.deleteFile(Util.getDeletedFilesFromRequest(request), catalogVo.getSeq(), request, principal);
			catalogVo.setModIp(request.getRemoteAddr());
			catalogVo.setModId(principal.getName());
			
			int success = catalogService.update(catalogVo, fileList, categories, tags);	
			if(success == 1){			
				return "redirect:/admin/catalog/detail?"+pp.get(Constants.PARAMETER_RETURN_PARAM);
			}
		}
		
		model.addAttribute(Constants.CATALOG, 			catalogVo);
		model.addAttribute(Constants.CONTENT_PARAM, 	pp);
		model.addAttribute(Constants.ACTION, 			Constants.EDIT);
		model.addAttribute(Constants.CATEGORY, 			catList);
		model.addAttribute(Constants.TAG, 				tagList);
		model.addAttribute(Constants.ERRORS, 			errors);
		
		return "/admin/catalog/form";
	}	
	
	@RequestMapping(value = "/admin/catalog/delete", method=RequestMethod.POST)
	public String delete(Model model, 
			HttpServletRequest request, 
			@ModelAttribute("catalogVo") CatalogVo catalogVo, 
			BindingResult bindingResult, 
			Principal principal) 
	{
		logger.info("Delete");
		
		Map<String, String>	pp = Util.buildPaginationUrlWithExtra(request, "category, sortColumn, sortOrder");
		
		catalogVo.setRegIp(request.getRemoteAddr());
		catalogVo.setRegId(principal.getName());
		catalogService.delete(catalogVo);
		
		return "redirect:/admin/catalog/list?"+pp.get(Constants.PARAMETER_LINK);
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
