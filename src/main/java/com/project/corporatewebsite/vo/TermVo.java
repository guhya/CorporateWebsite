package com.project.corporatewebsite.vo;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(proxyMode=ScopedProxyMode.TARGET_CLASS , value=WebApplicationContext.SCOPE_REQUEST)
public class TermVo extends CommonVo{

	private String name;
	private String description;
	private String taxonomy;
	private String lineage;
	private String parent;
	private String fixYn;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTaxonomy() {
		return taxonomy;
	}
	public void setTaxonomy(String taxonomy) {
		this.taxonomy = taxonomy;
	}
	public String getLineage() {
		return lineage;
	}
	public void setLineage(String lineage) {
		this.lineage = lineage;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getFixYn() {
		return fixYn;
	}
	public void setFixYn(String fixYn) {
		this.fixYn = fixYn;
	}

	@Override
	public String toString() {
		return "TermVo [name=" + name + ", description=" + description + ", lineage=" + lineage + ", parent=" + parent + 
				", taxonomy=" + taxonomy + ", getSeq()=" + getSeq() + ", getRegId()=" + getRegId() + ", fixYn =" + fixYn +
				", getRegIp()=" + getRegIp() + ", getRegDate()=" + getRegDate() + ", getModId()=" + getModId() +
				", getModIp()=" + getModIp() + ", getModDate()=" + getModDate() + ", getDelYn()=" + getDelYn() + "]";
	}
	

}
