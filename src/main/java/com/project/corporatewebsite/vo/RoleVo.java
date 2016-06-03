package com.project.corporatewebsite.vo;

public class RoleVo extends CommonVo{
	
	private String username;
	private String role;
	
	public RoleVo(String username, String role) {
		this.username = username;
		this.role = role;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
}
