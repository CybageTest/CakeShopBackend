package com.littlejoys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.littlejoys.dao.IRoleDao;
import com.littlejoys.entity.Role;

@Service
public class RoleService {
	
	@Autowired
	private IRoleDao roleDao;
	
	public Role createNewRole(Role role) {
		return roleDao.save(role);
	}
}
