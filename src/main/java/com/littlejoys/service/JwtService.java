package com.littlejoys.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.littlejoys.dao.IUserDao;
import com.littlejoys.entity.JwtRequest;
import com.littlejoys.entity.JwtResponse;
import com.littlejoys.entity.User;
import com.littlejoys.exception.InvalidCredentialsException;
import com.littlejoys.exception.UserDisabledException;
import com.littlejoys.util.JwtUtil;

@Service
public class JwtService implements UserDetailsService {
	@Autowired
	private IUserDao userDao;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private OtpService otpService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private AuthenticationManager authenticationManager;

	public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
		String username = jwtRequest.getUsername();
		String password = jwtRequest.getPassword();
		authenticate(username, password);
		final UserDetails userDetails = loadUserByUsername(username);
		String newJwtToken = jwtUtil.generateToken(userDetails);
		User user = userDao.findById(username).get();
		int otp = otpService.generateotp(username);
		String message = ("Your OTP for login is " + otp + " and will be valid for 5 minutes.");
		System.out.println("OTP JWT: " + message);
		// emailService.sendEmail(user.getEmail(), "Otp for login", message);
		return new JwtResponse(user, newJwtToken);
	}

	void authenticate(String username, String password) throws UserDisabledException, InvalidCredentialsException {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new UserDisabledException("User is disabled...! Please contact administrator");
		} catch (BadCredentialsException e) {
			throw new InvalidCredentialsException("Invalid usename and password. Please try again..!");
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findById(username).get();

		if (user != null) {
			return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(),
					getAuthority(user));
		}
		throw new UsernameNotFoundException("Username not found");
	}

	Set getAuthority(User user) {
		Set authorities = new HashSet<>();

		user.getRole().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
		});
		return authorities;
	}

}
