package com.project.corporatewebsite.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.project.corporatewebsite.vo.CommonVo;

public class Util {
	
	private static Properties prop = new Properties();
	
	public static String loadProperties(String key){
		String value = "";
		try {
			InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("/properties/app.properties");
			prop.load(stream);
			value = (String) prop.get(key);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return value;
	}
	
	public static Map<String, String> buildPaginationUrl(HttpServletRequest request){
		String p	= request.getParameter(Constants.PARAMETER_PAGE) != null ? request.getParameter(Constants.PARAMETER_PAGE) : "1";
		String c	= request.getParameter(Constants.PARAMETER_CONDITION) != null ? request.getParameter(Constants.PARAMETER_CONDITION) : "";
		String k	= request.getParameter(Constants.PARAMETER_KEYWORD) != null ? request.getParameter(Constants.PARAMETER_KEYWORD) : "";
		String ps	= request.getParameter(Constants.PARAMETER_PAGE_SIZE) != null ? request.getParameter(Constants.PARAMETER_PAGE_SIZE) : String.valueOf(Constants.PAGE_SIZE_VAL);
		
		String allParam = "&"+Constants.PARAMETER_CONDITION+"="+c+"&"+Constants.PARAMETER_KEYWORD+"="+k+"&"+Constants.PARAMETER_PAGE_SIZE+"="+ps;
		
		Map<String, String> param = new HashMap<String, String>();		
		param.put(Constants.PARAMETER_PAGE, p);
		param.put(Constants.PARAMETER_PAGE_SIZE, ps);
		param.put(Constants.PARAMETER_CONDITION, c);
		param.put(Constants.PARAMETER_KEYWORD, k);
		param.put(Constants.PARAMETER_ALL, allParam);	
		param.put(Constants.PARAMETER_LINK, Constants.PARAMETER_PAGE+"="+p+allParam);
		
		String rp	= request.getParameter(Constants.PARAMETER_RETURN_PARAM) != null ? request.getParameter(Constants.PARAMETER_RETURN_PARAM) : request.getQueryString();
		param.put(Constants.PARAMETER_RETURN_PARAM, rp);
		
		return param;
	}
	
	public static Map<String, String> buildPaginationUrlWithExtra(HttpServletRequest request, String extra){
		String[] arrExtra = extra.split("\\,"); 
		Map<String, String> param = buildPaginationUrl(request);
		for(String k : arrExtra){
			k = k.trim();
			String v = request.getParameter(k) != null ? request.getParameter(k) : "";
			param.put(k, v);
			String allParam = param.get(Constants.PARAMETER_ALL)+"&"+k+"="+v;
			String paramaterLink = param.get(Constants.PARAMETER_LINK)+"&"+k+"="+v;
			param.put(Constants.PARAMETER_ALL, allParam);
			param.put(Constants.PARAMETER_LINK, paramaterLink);
		}
		
		return param;
	}
	
	public static CommonVo prepareVoForListing(CommonVo commonVo, Map<String, String> pp){
		commonVo.setSearchCondition(pp.get(Constants.PARAMETER_CONDITION));
		commonVo.setSearchKeyword(pp.get(Constants.PARAMETER_KEYWORD));
		commonVo.setPageSize(Integer.parseInt(pp.get(Constants.PARAMETER_PAGE_SIZE)));
		int cp = Integer.parseInt(pp.get(Constants.PARAMETER_PAGE));
		commonVo.setStartRow((cp - 1) * commonVo.getPageSize());
		
		return commonVo;
	}
	
	public static List<Map<String, MultipartFile>> getFilesFromRequest(HttpServletRequest request){
		List<Map<String, MultipartFile>> fileList = new ArrayList<Map<String,MultipartFile>>();
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Iterator<String> it = multipartRequest.getFileNames();
		while(it.hasNext()){
			String key = it.next();
			if(!multipartRequest.getFile(key).isEmpty()){
				Map<String, MultipartFile> file = new HashMap<String, MultipartFile>();
				file.put(key, multipartRequest.getFile(key));			
				fileList.add(file);
			}
		}
		
		return fileList;
	}
	
	public static String[] getCategoryFromRequest(HttpServletRequest request){
		String[] categoRY = {};
		if(request.getParameterValues(Constants.REQUEST_CATEGORY) != null){
			categoRY = request.getParameterValues(Constants.REQUEST_CATEGORY);			
		}
		
		return categoRY;
	}

	public static String[] getTagsFromRequest(HttpServletRequest request){
		String[] tags = {};
		if(request.getParameterValues(Constants.REQUEST_TAGS) != null){
			tags = request.getParameterValues(Constants.REQUEST_TAGS);			
		}
		
		return tags;
	}
	
	public static String[] getDeletedFilesFromRequest(HttpServletRequest request){
		String rawDeletedFiles;
		String[] deletedFiles = {};
		if(request.getParameterValues(Constants.REQUEST_DELETED_FILES) != null){
			rawDeletedFiles = request.getParameter(Constants.REQUEST_DELETED_FILES);
			deletedFiles = rawDeletedFiles.split("\\,");			
		}
		
		return deletedFiles;
	}
	
	public static String getFileExtension(String fileName){
		String ext = "";
		if(fileName.indexOf(".") == -1){
			ext = "";
		}else{
			String[] arrFile = fileName.split("\\.");
			ext = arrFile[arrFile.length-1];
		}

		return ext;
	}
	
	public static boolean validateMime(String mimeType, String[] allowedMimeType){
		for(String allowed : allowedMimeType){
			if(mimeType.equalsIgnoreCase(allowed)){
				return true;
			}
		}		
		return false;
	}
	
	public static String stripTags(String raw){
		String cooked = "";
		if(!"".equals(raw)){
			String pattern = "<[^>]+>";
			cooked = raw.replaceAll(pattern, "");
			cooked = cooked.replace("&nbsp;", " ");
			cooked = cooked.replace("&quot;", "\"");
			cooked = cooked.replace("&ndash;", "-");		
		}
		
		return cooked;
	}
	
	public static String stringCut(String raw, int length){
		String cooked = "";
		if(!"".equals(raw)){
			if(raw.length() > length){
				cooked = raw.substring(0, length) + "...";
			}else{
				cooked = raw;
			}
		}
		
		return cooked;
	}	
	
	public static String formatDate(String raw, String pattern){
		String cooked = "";
		pattern = pattern == null ? "yyyy-MM-dd" : pattern;
		if(!"".equals(raw)){
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			try {
				Date date = sdf.parse(raw);
				cooked = sdf.format(date);
			} catch (ParseException e) {
				cooked =  "";
			}
		}
		
		return cooked;
	}	
	
	public static void writeFile(MultipartFile file, String fileName, String filePath) throws Exception {
		InputStream stream = null;
		OutputStream bos = null;

		try {
			stream = file.getInputStream();
			File cFile = new File(filePath);

			if (!cFile.isDirectory())
				cFile.mkdirs();

			bos = new FileOutputStream(filePath + File.separator + fileName);

			int bytesRead = 0;
			byte[] buffer = new byte[8096];

			while ((bytesRead = stream.read(buffer, 0, 8096)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (Exception ignore) {
				}
			}
			if (stream != null) {
				try {
					stream.close();
				} catch (Exception ignore) {
				}
			}
		}
	}
}
