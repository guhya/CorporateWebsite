package com.project.corporatewebsite.admin.service;

import java.util.List;

import com.project.corporatewebsite.vo.RoleVo;
import com.project.corporatewebsite.vo.UserVo;

public interface RoleService {
	
	public int insertRole(RoleVo roleVo);
	List<String> listUserRoles(UserVo userVo);

}
