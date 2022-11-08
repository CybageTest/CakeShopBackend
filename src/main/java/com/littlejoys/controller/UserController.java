package com.littlejoys.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	@GetMapping("/forUsers")
	@PreAuthorize("hasRole('user')")
	public String getUsers() {
		return "This URL is accessible only for users";
	}

	@GetMapping("/forAdmins")
	@PreAuthorize("hasRole('admin')")
	public String getAdmins() {
		return "This URL is accessible only for admins";
	}
	
	@GetMapping("/findByEmailOrMobile")
	public User findUserByEmailOrMobile(@RequestParam String email, @RequestParam String mobile) {
		return userService.findUserByEmailOrMobile(email, mobile);
	}

}
