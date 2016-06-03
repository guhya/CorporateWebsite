package com.project.corporatewebsite.vo;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

@SuppressWarnings("serial")
public class LoginVo extends User {
	
	private UserVo userVo;

	public LoginVo(String username, String password, Collection<? extends GrantedAuthority> authorities, UserVo userVo) {
		super(username, password, authorities);
		this.userVo = userVo;
	}
	
	public UserVo getUserVo() {
		return userVo;
	}

	public void setUserVo(UserVo userVo) {
		this.userVo = userVo;
	}	
	

}
