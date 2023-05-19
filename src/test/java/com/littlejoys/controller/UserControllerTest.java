package com.littlejoys.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import java.util.HashSet;
import java.util.Set;

import javax.mail.MessagingException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.test.context.support.WithMockUser;

import com.littlejoys.dto.UserDTO;
import com.littlejoys.entity.JwtRequest;
import com.littlejoys.entity.JwtResponse;
import com.littlejoys.entity.Role;
import com.littlejoys.entity.User;
import com.littlejoys.exception.InvalidOldPasswordException;
import com.littlejoys.exception.ResourceAlreadyExistException;
import com.littlejoys.service.JwtService;
import com.littlejoys.service.OtpService;
import com.littlejoys.service.UserService;

class UserControllerTest {
	private final String username = "testuser";
	private final int validOtp = 123456;
	private final int invalidOtp = 654321;
	private int remainingAttempts = 3;

	@InjectMocks
	private UserController userController;

	@Mock
	private UserService userService;

	@Mock
	private JwtService jwtService;

	@Mock
	private OtpService otpService;

	private User user;
	private UserDTO userDTO;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		Role userRole = new Role("user", "default role for user");
		Set<Role> role = new HashSet<>();
		role.add(userRole);
		user = new User("testuser", "test@mail.com", "testpassword", "9850909898", "active", null, role, null);
		userDTO = new UserDTO("testuser", "test@mail.com", "testpassword", "9850909898", "active", null, role, null);
	}

	@Test
	void testInitRolesAndUsers() {
		userController.initRoleUsers();
		verify(userService).initRolesAndUser();
	}

	@Test
	void testCreateJwtToken() throws Exception {
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzbmVoYWwiLCJleHAiOjE2ODQ0ODUwNTAsImlhdC";
		JwtRequest jwtRequest = new JwtRequest();
		jwtRequest.setUsername("testuser");
		jwtRequest.setPassword("testpassword");

		JwtResponse expectedResponse = new JwtResponse(user, token);

		Mockito.when(jwtService.createJwtToken(Mockito.any(JwtRequest.class))).thenReturn(expectedResponse);

		JwtResponse actualResponse = userController.createJwtToken(jwtRequest);

		assertNotNull(actualResponse);
		assertEquals(expectedResponse.getJwtToken(), actualResponse.getJwtToken());
		assertEquals(expectedResponse.getUser(), actualResponse.getUser());
		Mockito.verify(jwtService, Mockito.times(1)).createJwtToken(Mockito.any(JwtRequest.class));
	}

	@Test
	void testCreateNewUser() throws ResourceAlreadyExistException, MessagingException {

		Mockito.when(userService.createNewUser(Mockito.any(UserDTO.class))).thenReturn(user);

		User actualUser = userController.createNewUser(userDTO);

		assertNotNull(actualUser);
		assertEquals(user.getName(), actualUser.getName());
		assertEquals(user.getEmail(), actualUser.getEmail());
		Mockito.verify(userService, Mockito.times(1)).createNewUser(Mockito.any(UserDTO.class));
	}

	@Test
	@WithMockUser(roles = "admin")
	void testGetAdmins_AccessibleForAdmins() {
		String response = userController.getAdmins();

		assertNotNull(response);
		assertEquals("This URL is accessible only for admins", response);
	}

	@Test
	@WithMockUser(roles = "user")
	void testGetUsers_AccessibleForUsers() {
		String response = userController.getUsers();

		assertNotNull(response);
		assertEquals("This URL is accessible only for users", response);
	}

	@Test
	void testFindUserByEmailOrMobile() {
		String email = "test@example.com";
		String mobile = "9850909898";

		Mockito.when(userService.findUserByEmailOrMobile(email, mobile)).thenReturn(userDTO);

		UserDTO actualUserDto = userController.findUserByEmailOrMobile(email, mobile);

		assertNotNull(actualUserDto);
		assertEquals(userDTO.getEmail(), actualUserDto.getEmail());
		assertEquals(userDTO.getMobile(), actualUserDto.getMobile());
		Mockito.verify(userService, Mockito.times(1)).findUserByEmailOrMobile(email, mobile);
	}

	@Test
	void testChangeUserPassword_ValidOldPassword_ReturnsUserDTO() throws InvalidOldPasswordException {
		String name = "testUser";
		String oldPassword = "oldPassword";
		String newPassword = "newPassword";

		UserDTO expectedUserDto = new UserDTO();
		expectedUserDto.setName(name);
		expectedUserDto.setPassword(newPassword);

		Mockito.when(userService.changeUserPassword(name, oldPassword, newPassword)).thenReturn(expectedUserDto);

		UserDTO actualUserDto = userController.changeUserPassword(name, oldPassword, newPassword);

		assertNotNull(actualUserDto);
		assertEquals(expectedUserDto.getName(), actualUserDto.getName());
		assertEquals(expectedUserDto.getPassword(), actualUserDto.getPassword());
		Mockito.verify(userService, Mockito.times(1)).changeUserPassword(name, oldPassword, newPassword);
	}

	@Test
	void testChangeUserPassword_InvalidOldPassword_ThrowsInvalidOldPasswordException()
			throws InvalidOldPasswordException {
		String name = "testUser";
		String oldPassword = "wrongPassword";
		String newPassword = "newPassword";

		Mockito.when(userService.changeUserPassword(name, oldPassword, newPassword))
				.thenThrow(InvalidOldPasswordException.class);

		assertThrows(InvalidOldPasswordException.class,
				() -> userController.changeUserPassword(name, oldPassword, newPassword));
	}

	@Test
	void testValidateOtp_ValidOtp_ReturnsValidMessage() {
		Mockito.when(otpService.getOtp(username)).thenReturn(validOtp);

		String result = userController.validateOtp(username, validOtp);

		assertEquals("Entered OTP is valid", result);
		Mockito.verify(otpService, Mockito.times(1)).clearOtp(username);
	}

	@Test
	void testValidateOtp_InvalidOtp_ReturnsErrorMessage() {
		remainingAttempts = 2;
		Mockito.when(otpService.getOtp(username)).thenReturn(validOtp);

		String result = userController.validateOtp(username, invalidOtp);
		if (remainingAttempts == 2) {
			remainingAttempts++;
		}

		assertEquals("Entered OTP is wrong! You've " + remainingAttempts + " attempts remaining!", result);
		Mockito.verify(otpService, Mockito.never()).clearOtp(username);
	}

}
