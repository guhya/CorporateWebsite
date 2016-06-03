package com.project.corporatewebsite.admin.service;

import com.project.corporatewebsite.vo.UserVo;

public interface LoginService {
	public UserVo getLoggedInUser();
	public UserVo buildPrincipal(String username);

}
