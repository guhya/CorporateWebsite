package com.project.corporatewebsite.vo;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(proxyMode=ScopedProxyMode.TARGET_CLASS , value=WebApplicationContext.SCOPE_REQUEST)
public class CommonVo{

	private String seq;
	private String regId;
	private String regIp;
	private String regDate;
	private String modId;
	private String modIp;
	private String modDate;
	private String delYn;
	
	/* Search related field */
	private String searchCondition;
	private String searchKeyword;
	private int startRow;
	private int endRow;
	private int pageSize;	
	
	/* Sort related field */
	private String sortColumn;
	private String sortOrder;
	
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getRegIp() {
		return regIp;
	}
	public void setRegIp(String regIp) {
		this.regIp = regIp;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getModId() {
		return modId;
	}
	public void setModId(String modId) {
		this.modId = modId;
	}
	public String getModIp() {
		return modIp;
	}
	public void setModIp(String modIp) {
		this.modIp = modIp;
	}
	public String getModDate() {
		return modDate;
	}
	public void setModDate(String modDate) {
		this.modDate = modDate;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	
	public String getSearchCondition() {
		return searchCondition;
	}
	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}
	public String getSearchKeyword() {
		return searchKeyword;
	}
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}
	public int getStartRow() {
		return startRow;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	public int getEndRow() {
		return endRow;
	}
	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public String getSortColumn() {
		return sortColumn;
	}
	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String toString() {
        return "Common [seq=" + seq 
        		+ ", searchCondition=" + searchCondition + ", searchKeyword=" + searchKeyword  
        		+ ", startRow=" + startRow + ", endRow=" + endRow + ", pageSize=" + pageSize
        		+ ", sortColumn=" + sortColumn + ", sortOrder=" + sortOrder  
        		+ ", regId=" + regId + ", regIp=" + regIp + ", regDate=" + regDate  
        		+ ", modId=" + modId + ", modIp=" + modIp + ", modDate=" + modDate  
        		+ ", delYn=" + delYn + "]";
	}

}
