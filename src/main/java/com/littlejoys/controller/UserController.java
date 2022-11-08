package com.littlejoys.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.littlejoys.entity.JwtRequest;
import com.littlejoys.entity.JwtResponse;
import com.littlejoys.entity.User;
import com.littlejoys.service.JwtService;
import com.littlejoys.service.OtpService;
import com.littlejoys.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	private int attempts = 3;

	@Autowired
	private UserService userService;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private OtpService otpService;

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

	@PatchMapping("/changePassword/{name}/{oldPassword}/{newPassword}")
	public User changeUserPassword(@PathVariable String name, @PathVariable String oldPassword,
			@PathVariable String newPassword) throws Exception {
		return userService.changeUserPassword(name, oldPassword, newPassword);
	}

	@GetMapping(value = "/validateOtp/{username}/{otp}")
	public String validateOtp(@PathVariable String username, @PathVariable("otp") int otpnum) {
		System.out.println("Validate Otp Username: " + username);
		// Validate the OTP
		if (attempts != 0) {
			if (otpnum >= 0) {
				int serverOtp = otpService.getOtp(username);
				if (serverOtp > 0) {
					if (otpnum == serverOtp) {
						otpService.clearOtp(username);
						System.out.println("Valid Box Server: " + serverOtp);
						System.out.println("Valid Box OtpNum: " + otpnum);
						return "Entered OTP is valid";
					} else {
						System.out.println("Invalid Box Server: " + serverOtp);
						System.out.println("Invalid Box OtpNum: " + otpnum);
						attempts--;
						return ("Entered OTP is wrong! You've " + (attempts + 1) + " attempts remaining!");
					}
				} else {
					return "Please login again";
				}
			} else {
				return "Please enter valid OTP";
			}
		} else {
			attempts = 3;
			otpService.clearOtp(username);
			return "You're account is blocked. Please try again after 5 minutes.";
		}
	}

}
