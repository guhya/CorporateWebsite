package com.project.corporatewebsite.vo;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(proxyMode=ScopedProxyMode.TARGET_CLASS , value=WebApplicationContext.SCOPE_REQUEST)
public class NewsVo extends CommonVo{

	private String title;
	private String content;
	
	private String tags;
	private String category;
	
	private String thumbnailImageSeq;
	private String thumbnailImageOriginalName;
	private String mainImageSeq;
	private String mainImageOriginalName;
	private String attachmentSeq;
	private String attachmentOriginalName;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
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
	public String getAttachmentSeq() {
		return attachmentSeq;
	}
	public void setAttachmentSeq(String attachmentSeq) {
		this.attachmentSeq = attachmentSeq;
	}
	public String getAttachmentOriginalName() {
		return attachmentOriginalName;
	}
	public void setAttachmentOriginalName(String attachmentOriginalName) {
		this.attachmentOriginalName = attachmentOriginalName;
	}	
	
	@Override
	public String toString() {
		return "NewsVo [title=" + title + ", content=" + content +  
				", getSeq()=" + getSeq() + ", getRegId()=" + getRegId() +
				", tags =" + tags + ", category =" + category + 
				", thumbnailImageSeq =" + thumbnailImageSeq + ", thumbnailImageOriginalName =" + thumbnailImageOriginalName + 
				", mainImageSeq =" + mainImageSeq + ", mainImageOriginalName =" + mainImageOriginalName + 
				", attachmentSeq =" + attachmentSeq + ", attachmentOriginalName =" + attachmentOriginalName + 
				", getRegIp()=" + getRegIp() + ", getRegDate()=" + getRegDate() + ", getModId()=" + getModId() +
				", getModIp()=" + getModIp() + ", getModDate()=" + getModDate() + ", getDelYn()=" + getDelYn() + "]";
	}
	

}
