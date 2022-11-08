package com.littlejoys.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.littlejoys.entity.JwtRequest;
import com.littlejoys.entity.JwtResponse;
import com.littlejoys.entity.User;
import com.littlejoys.service.JwtService;
import com.littlejoys.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtService jwtService;
	
	@PostConstruct
	public void initRoleUsers() {
		userService.initRolesAndUser();
	}
	
	@PostMapping({ "/authenticate" })
	public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
		return jwtService.createJwtToken(jwtRequest);
	}
	
	@PostMapping("/adduser")
	public User createNewUser(@RequestBody User user) throws Exception {
		return userService.createNewUser(user);
	}
	
	
}
