package com.project.corporatewebsite.vo;

import org.springframework.stereotype.Component;

public class FileVo extends CommonVo{

	private String category;
	private String channel;
	private String ownerSeq;
	private String name;
	private String originalName;
	private String size;
	private String path;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getOwnerSeq() {
		return ownerSeq;
	}

	public void setOwnerSeq(String ownerSeq) {
		this.ownerSeq = ownerSeq;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "FileVo [category=" + category + ", channel=" + channel + ", ownerSeq=" + ownerSeq + ", name =" + name + 
				", originalName =" + originalName + ", size =" + size + 
				", path =" + path + 
				", getSeq()=" + getSeq() + ", getRegId()=" + getRegId() +
				", getRegIp()=" + getRegIp() + ", getRegDate()=" + getRegDate() + ", getModId()=" + getModId() +
				", getModIp()=" + getModIp() + ", getModDate()=" + getModDate() + ", getDelYn()=" + getDelYn() + "]";
	}
	

}
