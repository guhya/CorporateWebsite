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
import com.project.corporatewebsite.admin.service.TermService;
import com.project.corporatewebsite.common.Constants;
import com.project.corporatewebsite.common.Util;
import com.project.corporatewebsite.common.VoHelper;
import com.project.corporatewebsite.validator.TermValidator;
import com.project.corporatewebsite.vo.FileVo;
import com.project.corporatewebsite.vo.TermVo;

@Controller
public class TermController {
	private static final Logger logger = LoggerFactory.getLogger(TermController.class);
	
	@Autowired
	private TermService termService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private PagingService pagingService;

	@Autowired
	private TermVo termVo;
	
	@Autowired
	private TermValidator termValidator;
	
	@RequestMapping(value = "/admin/term/list", method = RequestMethod.GET)
	public String list(Model model, HttpServletRequest request) 
	{
		logger.info("List");		
		
		Map<String, String>	pp = Util.buildPaginationUrlWithExtra(request, "category, taxonomy, sortColumn, sortOrder");
		termVo = (TermVo) Util.prepareVoForListing(termVo, pp);
		termVo.setLineage(pp.get("category"));
		termVo.setTaxonomy(pp.get("taxonomy"));
		termVo.setSortColumn(pp.get("sortColumn"));
		termVo.setSortOrder(pp.get("sortOrder"));
		
		int totalRow	= termService.countList(termVo);
		List<Map<String, String>> termList = termService.list(termVo);		
		List<Map<String, String>> catList = termService.selectCategories();
		
		String paging	= pagingService.buildPage(totalRow, termVo.getPageSize(), Integer.parseInt(pp.get(Constants.PARAMETER_PAGE)), "", pp.get(Constants.PARAMETER_ALL));
		
		model.addAttribute(Constants.TERM, 				termList);
		model.addAttribute(Constants.CONTENT_PAGING,	paging);
		model.addAttribute(Constants.CONTENT_PARAM, 	pp);
		model.addAttribute(Constants.CATEGORY, 			catList);		
		
		return "/admin/term/list";
	}

	@RequestMapping(value = "/admin/term/detail", method = RequestMethod.GET)
	public String detail(Model model, HttpServletRequest request) 
	{
		logger.info("Detail");
		
		Map<String, String>	pp = Util.buildPaginationUrlWithExtra(request, "category, taxonomy, sortColumn, sortOrder");

		String seq = request.getParameter("seq");
		termVo.setSeq(seq);
		
		Map<String, String> result = termService.select(termVo);
		
		model.addAttribute(Constants.TERM, 				result);
		model.addAttribute(Constants.CONTENT_PARAM, 	pp);
		
		model.addAttribute("term", result);
		
		return "/admin/term/detail";
	}
	
	@RequestMapping(value = "/admin/term/write", method=RequestMethod.GET)
	public String register(Model model, HttpServletRequest request) 
	{
		logger.info("Write Form");
		
		Map<String, String>	pp = Util.buildPaginationUrlWithExtra(request, "category, taxonomy, sortColumn, sortOrder");
		List<Map<String, String>> catList = termService.selectCategories();
		
		model.addAttribute(Constants.ACTION, 			Constants.WRITE);
		model.addAttribute(Constants.CONTENT_PARAM, 	pp);
		model.addAttribute(Constants.CATEGORY, 			catList);
				
		return "/admin/term/form";
	}
	
	@RequestMapping(value = "/admin/term/edit", method=RequestMethod.GET)
	public String edit(Model model, HttpServletRequest request) 
	{
		logger.info("Edit Form");
		
		Map<String, String>	pp = Util.buildPaginationUrlWithExtra(request, "category, taxonomy, sortColumn, sortOrder");

		String seq = request.getParameter("seq");
		termVo.setSeq(seq);
		
		Map<String, String> result = termService.select(termVo);
		
		model.addAttribute(Constants.TERM, 				result);
		model.addAttribute(Constants.CONTENT_PARAM, 	pp);
		model.addAttribute(Constants.ACTION, 			Constants.EDIT);
		
		model.addAttribute("term", result);
		
		return "/admin/term/form";
	}
	
	@RequestMapping(value = "/admin/term/write", method=RequestMethod.POST)
	public String writeFormProcessor(Model model, 
									HttpServletRequest request, 
									@ModelAttribute("termVo") TermVo termVo, 
									BindingResult bindingResult, 
									Principal principal) 
	{
		logger.info("Form Processor");
		
		List<Map<String, MultipartFile>> fileList = Util.getFilesFromRequest(request);		
		Map<String, String> errors = new HashMap<String, String>();		
		termValidator.validateWithFile(termVo, fileList, bindingResult, Constants.WRITE);
		
		if(bindingResult.hasErrors()){
			for(ObjectError err : bindingResult.getAllErrors()){
				errors.put(err.getCode(), err.getDefaultMessage());
			}
		}else{
			termVo.setRegIp(request.getRemoteAddr());
			termVo.setRegId(principal.getName());
			
			int success = termService.insert(termVo);	
			if(success > 0){			
				return "redirect:/admin/term/list?taxonomy="+termVo.getTaxonomy();
			}
		}
		
		Map<String, String>	pp = Util.buildPaginationUrl(request);

		model.addAttribute(Constants.TERM, 				termVo);
		model.addAttribute(Constants.CONTENT_PARAM, 	pp);
		model.addAttribute(Constants.ACTION, 			Constants.WRITE);
		model.addAttribute(Constants.ERRORS, 			errors);
		
		return "/admin/term/form";
	}		

	@RequestMapping(value = "/admin/term/edit", method=RequestMethod.POST)
	public String editFormProcessor(Model model, 
									HttpServletRequest request, 
									@ModelAttribute("termVo") TermVo termVo, 
									BindingResult bindingResult, 
									Principal principal) 
	{
		logger.info("Form Processor");
		
		Map<String, String>	pp = Util.buildPaginationUrl(request);

		List<Map<String, MultipartFile>> fileList = Util.getFilesFromRequest(request);		
		Map<String, String> errors = new HashMap<String, String>();		
		termValidator.validateWithFile(termVo, fileList, bindingResult, Constants.EDIT);
		
		if(bindingResult.hasErrors()){
			for(ObjectError err : bindingResult.getAllErrors()){
				errors.put(err.getCode(), err.getDefaultMessage());
			}
		}else{
			this.deleteFile(request.getParameterValues("deletedFiles"), termVo.getSeq(), request, principal);
			termVo.setModIp(request.getRemoteAddr());
			termVo.setModId(principal.getName());
			
			int success = termService.update(termVo);	
			if(success == 1){			
				return "redirect:/admin/term/detail?"+pp.get(Constants.PARAMETER_RETURN_PARAM);
			}
		}
		
		model.addAttribute(Constants.TERM, 				termVo);
		model.addAttribute(Constants.CONTENT_PARAM, 	pp);
		model.addAttribute(Constants.ACTION, 			Constants.EDIT);
		model.addAttribute(Constants.ERRORS, 			errors);
		
		return "/admin/term/form";
	}	
	
	@RequestMapping(value = "/admin/term/delete", method=RequestMethod.POST)
	public String delete(Model model, 
			HttpServletRequest request, 
			@ModelAttribute("termVo") TermVo termVo, 
			BindingResult bindingResult, 
			Principal principal) 
	{
		logger.info("Delete");
		
		Map<String, String>	pp = Util.buildPaginationUrlWithExtra(request, "category, taxonomy, sortColumn, sortOrder");
		
		termVo.setRegIp(request.getRemoteAddr());
		termVo.setRegId(principal.getName());
		termService.delete(termVo);
		
		return "redirect:/admin/term/list?"+pp.get(Constants.PARAMETER_LINK);
	}
	
	private void deleteFile(String[] deletedFiles, String ownerSeq, HttpServletRequest request, Principal principal){
		if (deletedFiles == null) return;
		
		for(String deletedSeq : deletedFiles){
			FileVo fileVo = VoHelper.prepareFileVo(request.getRemoteAddr(), principal.getName(), null, "", "", "");
			fileVo.setSeq(deletedSeq);
			fileVo.setOwnerSeq(ownerSeq);
			
			fileService.delete(fileVo);
		}
	}
}
