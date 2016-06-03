package com.project.corporatewebsite.admin.service;

public interface PagingService {
	
	public String buildPage(int totalRows, int pageSize, int currentPage, String linkPage, String linkParameter);
	public String firstPage();
	public String prevPage();
	public String listPage();
	public String nextPage();
	public String lastPage();
}
