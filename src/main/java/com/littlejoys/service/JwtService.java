package com.littlejoys.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.littlejoys.dao.IUserDao;
import com.littlejoys.entity.User;
import com.littlejoys.util.JwtUtil;

@Service
public class JwtService implements UserDetailsService {
	@Autowired
	private IUserDao userDao;

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findById(username).get();

		if (user != null) {
			return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(),
					getAuthority(user));
		}
		throw new UsernameNotFoundException("Username not found");
	}

	private Set getAuthority(User user) {
		Set authorities = new HashSet<>();

		user.getRole().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
		});
		return authorities;
	}

}
