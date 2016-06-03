package com.project.corporatewebsite.vo;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(proxyMode=ScopedProxyMode.TARGET_CLASS , value=WebApplicationContext.SCOPE_REQUEST)
public class SettingVo extends CommonVo{

	private String title;
	private String startDate;
	private String endDate;
	private String useYn;
	private String defaultYn;
	
	private String thumbnailImageSeq;
	private String thumbnailImageOriginalName;
	private String mainImageSeq;
	private String mainImageOriginalName;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getDefaultYn() {
		return defaultYn;
	}
	public void setDefaultYn(String defaultYn) {
		this.defaultYn = defaultYn;
	}
	
	public String getThumbnailImageSeq() {
		return thumbnailImageSeq;
	}
	public void setThumbnailImageSeq(String thumbnailImageSeq) {
		this.thumbnailImageSeq = thumbnailImageSeq;
	}
	public String getThumbnailImageOriginalName() {
		return thumbnailImageOriginalName;
	}
	public void setThumbnailImageOriginalName(String thumbnailImageOriginalName) {
		this.thumbnailImageOriginalName = thumbnailImageOriginalName;
	}
	public String getMainImageSeq() {
		return mainImageSeq;
	}
	public void setMainImageSeq(String mainImageSeq) {
		this.mainImageSeq = mainImageSeq;
	}
	public String getMainImageOriginalName() {
		return mainImageOriginalName;
	}
	public void setMainImageOriginalName(String mainImageOriginalName) {
		this.mainImageOriginalName = mainImageOriginalName;
	}
	
	@Override
	public String toString() {
		return "SettingVo [title=" + title +  
				", getSeq()=" + getSeq() + ", getRegId()=" + getRegId() +
				", startDate =" + startDate + ", endDate=" + endDate + ", useYn=" + useYn + ", defaultYn=" + defaultYn +
				", thumbnailImageSeq =" + thumbnailImageSeq + ", thumbnailImageOriginalName =" + thumbnailImageOriginalName + 
				", mainImageSeq =" + mainImageSeq + ", mainImageOriginalName =" + mainImageOriginalName + 
				", getSortColumn()=" + getSortColumn() + ", getSortOrder()=" + getSortOrder() + 
				", getRegIp()=" + getRegIp() + ", getRegDate()=" + getRegDate() + ", getModId()=" + getModId() +
				", getModIp()=" + getModIp() + ", getModDate()=" + getModDate() + ", getDelYn()=" + getDelYn() + "]";
	}
	

}
