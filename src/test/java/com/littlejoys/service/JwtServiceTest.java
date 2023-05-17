package com.littlejoys.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import com.littlejoys.dao.IUserDao;
import com.littlejoys.entity.JwtRequest;
import com.littlejoys.entity.JwtResponse;
import com.littlejoys.entity.Role;
import com.littlejoys.entity.User;
import com.littlejoys.exception.InvalidCredentialsException;
import com.littlejoys.util.JwtUtil;

class JwtServiceTest {

	@Mock
	private IUserDao userDao;

	@Mock
	private JwtUtil jwtUtil;

	@Mock
	private AuthenticationManager authenticationManager;

	@Mock
	private OtpService otpService;

	@InjectMocks
	private JwtService jwtService;

	@InjectMocks
	private UserService userService;

	public JwtServiceTest() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreateJwtToken() throws Exception {
		JwtRequest jwtRequest = new JwtRequest();
		jwtRequest.setUsername("testuser");
		jwtRequest.setPassword("testpassword");

		User userToBeTested = new User();
		userToBeTested.setName("testuser");
		userToBeTested.setPassword("testpassword");

		Role role = new Role();
		role.setRoleName("testRole");

		Set<Role> roles = new HashSet<>();
		roles.add(role);
		userToBeTested.setRole(roles);

		Mockito.when(userDao.findById("testuser")).thenReturn(Optional.of(userToBeTested));
		Mockito.when(jwtUtil.generateToken(Mockito.any())).thenReturn("jwtToken");
		Mockito.when(otpService.generateotp("")).thenReturn(12345);

		JwtResponse jwtResponse = jwtService.createJwtToken(jwtRequest);

		assertEquals(userToBeTested, jwtResponse.getUser());
		assertEquals("jwtToken", jwtResponse.getJwtToken());

	}

	@Test
	void testLoadUserByUsername() {
		User user = new User();
		user.setName("testuser");
		user.setPassword("testpassword");

		Role role = new Role();
		role.setRoleName("testRole");

		Set<Role> roles = new HashSet<>();
		roles.add(role);
		user.setRole(roles);

		when(userDao.findById(Mockito.anyString())).thenReturn(java.util.Optional.of(user));

		UserDetails userDetails = jwtService.loadUserByUsername("testuser");

		assertEquals(user.getName(), userDetails.getUsername());
		assertEquals(user.getPassword(), userDetails.getPassword());
	}

	@Test
	void testGetAuthority_SingleRole_ShouldReturnSingleAuthority() {
		// Arrange
		User user = new User();
		Role role = new Role();
		role.setRoleName("testRole");

		Set<Role> roles = new HashSet<>();
		roles.add(role);
		user.setRole(roles);
		// Set other properties of the role as needed

		// Act
		Set authorities = jwtService.getAuthority(user);

		// Assert
		assertEquals(1, authorities.size());
	}

	@Test
	void testAuthenticate_InvlidUser_ShouldThrowException() {
		// Arrange
		String username = "testuser";
		String password = "testpassword";

		User user = new User();
		user.setName(username);
		user.setPassword(password);

		Role role = new Role();
		role.setRoleName("testRole");

		Set<Role> roles = new HashSet<>();
		roles.add(role);
		user.setRole(roles);

		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
				.thenThrow(new InvalidCredentialsException("Invalid usename and password. Please try again..!"));

		assertThrows(InvalidCredentialsException.class, () -> jwtService.authenticate(username, "wrongPassword"));

	}

}
