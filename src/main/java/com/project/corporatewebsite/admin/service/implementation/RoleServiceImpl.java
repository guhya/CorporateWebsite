package com.project.corporatewebsite.admin.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.corporatewebsite.admin.dao.UserDAO;
import com.project.corporatewebsite.admin.service.RoleService;
import com.project.corporatewebsite.vo.RoleVo;
import com.project.corporatewebsite.vo.UserVo;

@Component
public class RoleServiceImpl implements RoleService {

	@Autowired
	private UserDAO userDao;

	@Override
	public int insertRole(RoleVo roleVo) {
		return userDao.insertRole(roleVo);
	}
	
	public List<String> listUserRoles(UserVo userVo) {
		return (List<String>) userDao.listUserRoles(userVo);
	}

}
