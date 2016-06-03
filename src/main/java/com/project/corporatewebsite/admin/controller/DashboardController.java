package com.project.corporatewebsite.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.project.corporatewebsite.admin.service.CareerService;
import com.project.corporatewebsite.admin.service.CatalogService;
import com.project.corporatewebsite.admin.service.EventService;
import com.project.corporatewebsite.admin.service.NewsService;
import com.project.corporatewebsite.admin.service.SettingService;
import com.project.corporatewebsite.admin.service.TermService;
import com.project.corporatewebsite.admin.service.UserService;
import com.project.corporatewebsite.common.Constants;
import com.project.corporatewebsite.common.Util;
import com.project.corporatewebsite.vo.CareerVo;
import com.project.corporatewebsite.vo.CatalogVo;
import com.project.corporatewebsite.vo.EventVo;
import com.project.corporatewebsite.vo.NewsVo;
import com.project.corporatewebsite.vo.SettingVo;
import com.project.corporatewebsite.vo.TermVo;
import com.project.corporatewebsite.vo.UserVo;

@Controller
public class DashboardController {
	private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);
	
	@Autowired
	private NewsService newsService;

	@Autowired
	private EventService eventService;

	@Autowired
	private CareerService careerService;

	@Autowired
	private CatalogService catalogService;

	@Autowired
	private UserService userService;

	@Autowired
	private TermService termService;

	@Autowired
	private SettingService settingService;

	@Autowired
	private NewsVo newsVo;
	
	@Autowired
	private EventVo eventVo;

	@Autowired
	private CareerVo careerVo;

	@Autowired
	private CatalogVo catalogVo;

	@Autowired
	private UserVo userVo;

	@Autowired
	private TermVo termVo;

	@Autowired
	private SettingVo settingVo;
	
	@RequestMapping(value = "/admin/dashboard", method = RequestMethod.GET)
	public String list(Model model, HttpServletRequest request) 
	{
		logger.info("Dashboard");
		
		Map<String, String>	pp = new HashMap<String, String>();
		pp.put(Constants.PARAMETER_PAGE_SIZE	, "5");
		pp.put(Constants.PARAMETER_PAGE			, "1");
		newsVo 		= (NewsVo) Util.prepareVoForListing(newsVo, pp);
		eventVo 	= (EventVo) Util.prepareVoForListing(eventVo, pp);
		careerVo 	= (CareerVo) Util.prepareVoForListing(careerVo, pp);
		catalogVo 	= (CatalogVo) Util.prepareVoForListing(catalogVo, pp);
		
		newsVo.setCategory("111");
		List<Map<String, String>> newsList 		= newsService.list(newsVo);		
		newsVo.setCategory("112");
		List<Map<String, String>> csrList 		= newsService.list(newsVo);
		
		List<Map<String, String>> eventList 	= eventService.list(eventVo);		
		List<Map<String, String>> careerList 	= careerService.list(careerVo);		
		
		catalogVo.setCategory("89");
		List<Map<String, String>> reportList 	= catalogService.list(catalogVo);		
		catalogVo.setCategory("123");
		List<Map<String, String>> pictureList 	= catalogService.list(catalogVo);		
		catalogVo.setCategory("124");
		List<Map<String, String>> videoList 	= catalogService.list(catalogVo);		
		
		
		model.addAttribute("news", 				newsList);
		model.addAttribute("csr", 				csrList);
		model.addAttribute(Constants.EVENT, 	eventList);
		model.addAttribute(Constants.CAREER, 	careerList);
		model.addAttribute("report", 			reportList);
		model.addAttribute("picture", 			pictureList);
		model.addAttribute("video", 			videoList);
		
		String[] arrModules 	= {"User", "Report", "Picture", "Video", "News", "CSR", "Event", "Career", "Category", "Tag"};
		int[] arrModulesCount	= new int[10];
		
		arrModulesCount[0]	= userService.countList(userVo);

		catalogVo.setCategory("89");		
		arrModulesCount[1]	= catalogService.countList(catalogVo);
		catalogVo.setCategory("123");
		arrModulesCount[2]	= catalogService.countList(catalogVo);
		catalogVo.setCategory("124");
		arrModulesCount[3]	= catalogService.countList(catalogVo);
		
		newsVo.setCategory("111");
		arrModulesCount[4]	= newsService.countList(newsVo);		
		newsVo.setCategory("112");		
		arrModulesCount[5]	= newsService.countList(newsVo);
		
		arrModulesCount[6]	= eventService.countList(eventVo);
		arrModulesCount[7]	= careerService.countList(careerVo);
		
		termVo.setTaxonomy("cat");
		arrModulesCount[8]	= termService.countList(termVo);
		termVo.setTaxonomy("tag");
		arrModulesCount[9]	= termService.countList(termVo);
		
		List<Map<String, Integer>> itemStatsList = new ArrayList<Map<String,Integer>>();
		for(int i=0; i<=arrModulesCount.length-1; i++){
			Map<String, Integer> objModule = new HashMap<String, Integer>();
			objModule.put(arrModules[i], arrModulesCount[i]);
			itemStatsList.add(objModule);
		}
		
		model.addAttribute("itemStatsList", 	itemStatsList);
		
		Map<String, String> currentSetting = settingService.getCurrentSetting(settingVo);
		model.addAttribute("setting", 			currentSetting);
		
		
		return "/admin/dashboard/dashboard";
	}


}
