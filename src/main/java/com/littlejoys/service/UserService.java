package com.littlejoys.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.littlejoys.dao.IRoleDao;
import com.littlejoys.dao.IUserDao;
import com.littlejoys.entity.Role;
import com.littlejoys.entity.User;

@Service
public class UserService {
	@Autowired
	private IUserDao userDao;

	@Autowired
	private IRoleDao roleDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public void initRolesAndUser() {
		Role adminRole = new Role("admin", "Admin Role");
		roleDao.save(adminRole);

		Role userRole = new Role("user", "Default role for newly created");
		roleDao.save(userRole);

		Set<Role> adminRoles = new HashSet<>();
		adminRoles.add(adminRole);

		Set<Role> userRoles = new HashSet<>();
		userRoles.add(userRole);

		User adminUser = new User("admin", "admin@mail.com", getEncodedPassword("admin@123"), "9850909009", "active",
				adminRoles);
		userDao.save(adminUser);

//		User sampleUser= new User("abhi", "abhi@mail.com", getEncodedPassword("abhi@123"), "1234567890", "active", userRoles);		
//		userDao.save(sampleUser);

	}

	public String getEncodedPassword(String password) {
		return passwordEncoder.encode(password);
	}

}
