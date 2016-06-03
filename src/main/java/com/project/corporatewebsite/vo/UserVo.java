package com.project.corporatewebsite.vo;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
public class UserVo extends CommonVo{

	private String username;
	private String password;
	private String oldPassword;
	private String firstName;
	private String lastName;
	private String enabled;
	private String email;
	
	private String roleString;

	private String thumbnailImageSeq;
	private String thumbnailImageOriginalName;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public String getRoleString() {
		return roleString;
	}
	public void setRoleString(String roleString) {
		this.roleString = roleString;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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

	@Override
	public String toString() {
		return "UserVo [username=" + username + ", password=" + password + ", firstName=" + firstName + ", lastName=" + lastName + 
				", oldPassword =" + oldPassword + ", email =" + email + 
				", thumbnailImageSeq =" + thumbnailImageSeq + ", thumbnailImageOriginalName =" + thumbnailImageOriginalName + 
				", enabled=" + enabled + ", getSeq()=" + getSeq() + ", getRegId()=" + getRegId() +
				", getRegIp()=" + getRegIp() + ", getRegDate()=" + getRegDate() + ", getModId()=" + getModId() +
				", getModIp()=" + getModIp() + ", getModDate()=" + getModDate() + ", getDelYn()=" + getDelYn() + "]";
	}
	

}
