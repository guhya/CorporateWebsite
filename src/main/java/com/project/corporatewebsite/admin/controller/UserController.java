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
import com.project.corporatewebsite.admin.service.LoginService;
import com.project.corporatewebsite.admin.service.PagingService;
import com.project.corporatewebsite.admin.service.RoleService;
import com.project.corporatewebsite.admin.service.UserService;
import com.project.corporatewebsite.common.Constants;
import com.project.corporatewebsite.common.Util;
import com.project.corporatewebsite.common.VoHelper;
import com.project.corporatewebsite.validator.UserValidator;
import com.project.corporatewebsite.vo.FileVo;
import com.project.corporatewebsite.vo.RoleVo;
import com.project.corporatewebsite.vo.UserVo;

@Controller
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private PagingService pagingService;

	@Autowired
	private FileService fileService;
	
	@Autowired
	private UserVo userVo;
	
	@Autowired
	private UserValidator userValidator;
	
	@RequestMapping(value = "/admin/user/list", method = RequestMethod.GET)
	public String list(Model model, HttpServletRequest request) 
	{
		logger.info("List");		
		
		Map<String, String>	pp = Util.buildPaginationUrl(request);
		userVo = (UserVo) Util.prepareVoForListing(userVo, pp);
		
		int totalRow	= userService.countList(userVo);
		List<Map<String, String>> userList = userService.list(userVo);		
		
		String paging	= pagingService.buildPage(totalRow, userVo.getPageSize(), Integer.parseInt(pp.get(Constants.PARAMETER_PAGE)), "", pp.get(Constants.PARAMETER_ALL));
		
		model.addAttribute(Constants.USER, 				userList);
		model.addAttribute(Constants.CONTENT_PAGING,	paging);
		model.addAttribute(Constants.CONTENT_PARAM, 	pp);
		
		return "/admin/user/list";
	}

	@RequestMapping(value = "/admin/user/detail", method = RequestMethod.GET)
	public String detail(Model model, HttpServletRequest request) 
	{
		logger.info("Detail");
		
		Map<String, String>	pp = Util.buildPaginationUrl(request);		

		String username = request.getParameter("username");
		userVo.setUsername(username);
		
		Map<String, String> result = userService.select(userVo);
		
		model.addAttribute(Constants.USER, 				result);
		model.addAttribute(Constants.CONTENT_PARAM, 	pp);
		
		model.addAttribute("user", result);
		
		return "/admin/user/detail";
	}
	
	@RequestMapping(value = "/admin/user/mydetail", method = RequestMethod.GET)
	public String myDetail(Model model, UserVo userVo) 
	{
		logger.info("My Detail");
		
		userVo = loginService.getLoggedInUser();
		model.addAttribute("user", userVo);
		
		return "/admin/user/detail";
	}

	@RequestMapping(value = "/admin/user/write", method=RequestMethod.GET)
	public String register(Model model) 
	{
		logger.info("Write Form");
		
		model.addAttribute(Constants.ACTION, 			Constants.WRITE);
		
		return "/admin/user/form";
	}
	
	@RequestMapping(value = "/admin/user/edit", method=RequestMethod.GET)
	public String edit(Model model, HttpServletRequest request) 
	{
		logger.info("Edit Form");
		
		Map<String, String>	pp = Util.buildPaginationUrl(request);		

		String username = request.getParameter("username");
		userVo.setUsername(username);
		
		Map<String, String> result = userService.select(userVo);
		
		model.addAttribute(Constants.USER, 				result);
		model.addAttribute(Constants.CONTENT_PARAM, 	pp);
		model.addAttribute(Constants.ACTION, 			Constants.EDIT);
		
		model.addAttribute("user", result);
		
		return "/admin/user/form";
	}
	
	@RequestMapping(value = "/admin/user/write", method=RequestMethod.POST)
	public String writeFormProcessor(Model model, 
									HttpServletRequest request, 
									@ModelAttribute("userVo") UserVo userVo, 
									BindingResult bindingResult, 
									Principal principal) 
	{
		logger.info("Form Processor");
		
		List<Map<String, MultipartFile>> fileList = Util.getFilesFromRequest(request);		
		Map<String, String> errors = new HashMap<String, String>();		
		userValidator.validateWithFile(userVo, fileList, bindingResult, Constants.WRITE);
		
		if(bindingResult.hasErrors()){
			for(ObjectError err : bindingResult.getAllErrors()){
				errors.put(err.getCode(), err.getDefaultMessage());
			}
		}else{
			userVo.setRegIp(request.getRemoteAddr());
			userVo.setRegId(principal.getName());
			
			int success = userService.insert(userVo, fileList);	
			for(String role : Constants.ROLES){
				RoleVo roleVo = new RoleVo(userVo.getUsername(), role);
				success		= roleService.insertRole(roleVo);
			}
			
			if(success > 0){			
				return "redirect:/admin/user/list";
			}
		}
		
		Map<String, String>	pp = Util.buildPaginationUrl(request);
		
		model.addAttribute(Constants.USER, 				userVo);
		model.addAttribute(Constants.CONTENT_PARAM, 	pp);
		model.addAttribute(Constants.ACTION, 			Constants.WRITE);
		model.addAttribute(Constants.ERRORS, 			errors);
		
		return "/admin/user/form";
	}		

	@RequestMapping(value = "/admin/user/edit", method=RequestMethod.POST)
	public String editFormProcessor(Model model, 
									HttpServletRequest request, 
									@ModelAttribute("userVo") UserVo userVo, 
									BindingResult bindingResult, 
									Principal principal) 
	{
		logger.info("Form Processor");
		
		Map<String, String>	pp = Util.buildPaginationUrl(request);

		List<Map<String, MultipartFile>> fileList = Util.getFilesFromRequest(request);		
		Map<String, String> errors = new HashMap<String, String>();		
		userValidator.validateWithFile(userVo, fileList, bindingResult, Constants.EDIT);
		
		if(bindingResult.hasErrors()){
			for(ObjectError err : bindingResult.getAllErrors()){
				errors.put(err.getCode(), err.getDefaultMessage());
			}
		}else{
			this.deleteFile(Util.getDeletedFilesFromRequest(request), userVo.getSeq(), request, principal);
			userVo.setModIp(request.getRemoteAddr());
			userVo.setModId(principal.getName());
			
			int success = userService.update(userVo, fileList);	
			if(success == 1){			
				return "redirect:/admin/user/detail?"+pp.get(Constants.PARAMETER_RETURN_PARAM);
			}
		}
		
		model.addAttribute(Constants.USER, 				userVo);
		model.addAttribute(Constants.CONTENT_PARAM, 	pp);
		model.addAttribute(Constants.ACTION, 			Constants.EDIT);
		model.addAttribute(Constants.ERRORS, 			errors);
		
		return "/admin/user/form";
	}	
	
	@RequestMapping(value = "/admin/user/delete", method=RequestMethod.POST)
	public String delete(Model model, 
			HttpServletRequest request, 
			@ModelAttribute("userVo") UserVo userVo, 
			BindingResult bindingResult, 
			Principal principal) 
	{
		logger.info("Delete");
		
		Map<String, String>	pp = Util.buildPaginationUrl(request);		
		
		userVo.setRegIp(request.getRemoteAddr());
		userVo.setRegId(principal.getName());
		userService.delete(userVo);
		
		return "redirect:/admin/user/list?"+pp.get(Constants.PARAMETER_LINK);
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
