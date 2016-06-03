package com.project.corporatewebsite.admin.service.implementation;

import org.springframework.stereotype.Component;

import com.project.corporatewebsite.admin.service.PagingService;
import com.project.corporatewebsite.common.Constants;

@Component
public class PagingServiceImpl implements PagingService{
	private int totalRows;
	private int pageSize;
	private int currentPage;
	private int totalPages;
	
	private String linkPage;
	private String linkParameter;
	
    private String strPrev 	= "<";
	private String strNext	= ">";                                                                
    private String strStart	= "<<";
	private String strEnd	= ">>";
	
	public String buildPage(int totalRows, int pageSize, int currentPage, String linkPage, String linkParameter)
	{		
		this.totalRows 		= totalRows;
		this.pageSize		= pageSize;
		this.currentPage	= currentPage;
		
		this.linkPage		= linkPage;
		this.linkParameter	= linkParameter;
		
		if(this.totalRows > this.pageSize){
			double tp		= (double) this.totalRows / this.pageSize;
			this.totalPages	= (int) Math.ceil(tp);
		}else{
			this.totalPages	= 0;
		}
		
		//Early exit if paging is non existent
		if (this.totalPages == 0){
			return "";
		}
		
		StringBuilder result  = new StringBuilder();
		result.append(this.firstPage());		
		result.append(this.prevPage());
		result.append(this.listPage());
		result.append(this.nextPage());		
		result.append(this.lastPage());
		
		return result.toString();
	}
	
	public String firstPage(){
		String result = "";
		if(this.currentPage > 1){
			result += "<li><a href="+this.linkPage+"?"+Constants.PARAMETER_PAGE+"=1"+this.linkParameter+" class='prev'>"+this.strStart+"</a></li>"; 
		}else{
			result += "<li><a href='javascript:void(0);' class='prev'>"+this.strStart+"</a></li>";				
		}
		
		return result;
	}
	
	public String prevPage(){
		String result = "";
		if(this.currentPage > 1){
			result += "<li><a href="+this.linkPage+"?"+Constants.PARAMETER_PAGE+"="+(this.currentPage-1)+this.linkParameter+" class='prev'>"+this.strPrev+"</a></li>";
		}else{
			result += "<li><a href='javascript:void(0);' class='prev'>"+this.strPrev+"</a></li>";
		}
		
		return result;		
	}
	
	public String listPage(){
		String result = "";
		for(int i=1; i<=this.totalPages; i++){
			if(this.currentPage != i){
				result += "<li><a href="+this.linkPage+"?"+Constants.PARAMETER_PAGE+"="+i+this.linkParameter+">"+i+"</a></li>";				
			}else{
				result += "<li class='active'><a href='javascript:void(0);'>"+i+"</a></li>";				
			}
		}
		
		return result;
	}
	
	public String nextPage(){
		String result = "";
		if(this.currentPage < this.totalPages){
			result += "<li><a href="+this.linkPage+"?"+Constants.PARAMETER_PAGE+"="+(this.currentPage+1)+this.linkParameter+" class='next'>"+this.strNext+"</a></li>";
		}else{
			result += "<li><a href='javascript:void(0);' class='next'>"+this.strNext+"</a></li>";
		}
		
		return result;		
	}
	
	public String lastPage(){
		String result = "";
		if(this.currentPage < this.totalPages){
			result += "<li><a href="+this.linkPage+"?"+Constants.PARAMETER_PAGE+"="+this.totalPages+this.linkParameter+" class='next'>"+this.strEnd+"</a></li>";
		}else{
			result += "<li><a href='javascript:void(0);' class='next'>"+this.strEnd+"</a></li>";
		}
		
		return result;		
	}
	
}
