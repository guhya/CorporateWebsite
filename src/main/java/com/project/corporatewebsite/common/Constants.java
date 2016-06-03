package com.project.corporatewebsite.common;

public class Constants {
	
	public static final int PAGE_SIZE_VAL			= 10;
	
	public static final String WEB_ROOT_PHYSICAL	= Util.loadProperties("web.root.physical");
	public static final String WEB_ROOT				= Util.loadProperties("web.root");
	public static final String ASSET_ROOT			= Util.loadProperties("asset.root");
	
	public static final String ACTION				= "action";
	public static final String WRITE				= "write";
	public static final String EDIT					= "edit";
	
	public static final String FILES				= "files";
	public static final String THUMBNAIL_IMAGE 		= "thumbnailImage";
	public static final String MAIN_IMAGE 			= "mainImage";
	public static final String IMAGE1 				= "image1";
	public static final String IMAGE2 				= "image2";
	public static final String IMAGE3 				= "image3";
	public static final String BROCHURE 			= "brochure";
	public static final String ATTACHMENT			= "attachment";
	
	public static final String PRODUCT				= "product";
	public static final String SERVICE				= "service";
	public static final String NEWS 				= "news";
	public static final String EVENT 				= "event";
	public static final String CONTACT				= "contact";
	public static final String PROJECT				= "project";
	public static final String CATALOG				= "catalog";
	public static final String USER					= "user";
	public static final String CAREER				= "career";
	public static final String TERM					= "term";
	public static final String TAG					= "tag";
	public static final String CATEGORY				= "category";
	public static final String SETTING				= "setting";
	public static final String DASHBOARD			= "dashboard";
	
	public static final String UPLOAD_SERVICE_PATH 	= "/upload/service";
	public static final String UPLOAD_NEWS_PATH 	= "/upload/news";
	public static final String UPLOAD_EVENT_PATH 	= "/upload/event";
	public static final String UPLOAD_CONTACT_PATH 	= "/upload/contact";
	public static final String UPLOAD_PROJECT_PATH 	= "/upload/project";
	public static final String UPLOAD_CATALOG_PATH 	= "/upload/catalog";
	public static final String UPLOAD_USER_PATH 	= "/upload/user";
	public static final String UPLOAD_TERM_PATH		= "/upload/term";
	public static final String UPLOAD_CAREER_PATH	= "/upload/career";
	public static final String UPLOAD_SETTING_PATH	= "/upload/setting";
	
	/* To be used in page & search navigation  (URL Parameter) */
	public static final String PARAMETER_PAGE			= "page";
	public static final String PARAMETER_PAGE_SIZE		= "pageSize";
	public static final String PARAMETER_CONDITION		= "condition";
	public static final String PARAMETER_KEYWORD		= "keyword";
	public static final String PARAMETER_RETURN_PARAM	= "returnParam";
	public static final String PARAMETER_ALL			= "allParam";
	public static final String PARAMETER_LINK 			= "parameterLink";
	
	/* Used as keys in associative array (data) passed to view layer */
	public static final String CONTENT_PAGE_TITLE	= "pageTitle";
	public static final String CONTENT_MENU			= "menu";
	public static final String CONTENT_PARAM		= "parameter";
	public static final String CONTENT_PAGING		= "paging";
	
	public static final String CURRENT				= "current";
	public static final String PAGE_SIZE			= "pageSize";
	public static final String TOTAL_ROWS			= "totalRows";
	
	public static final String DATA					= "data";
	public static final String ERRORS				= "errors";
	
	public static final String[] ROLES				= {"ROLE_ADMIN", "ROLE_USER"};
	public static final String[] IMAGES_MIME_TYPE	= {"image/jpeg", "image/pjpeg", "image/png", "image/gif"};
	public static final String[] FILES_MIME_TYPE	= {"application/pdf", "application/zip", "application/x-zip-compressed", "application/x-rar-compressed"};
	
	public static final String TERM_CATEGORY		= "cat";
	public static final String TERM_TAG				= "tag";
	
	public static final String REQUEST_CATEGORY			= "category";
	public static final String REQUEST_TAGS				= "tags";
	public static final String REQUEST_DELETED_FILES	= "deletedFiles";

}
