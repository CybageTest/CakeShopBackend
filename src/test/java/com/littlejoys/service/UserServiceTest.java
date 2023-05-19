package com.littlejoys.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import javax.mail.MessagingException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.littlejoys.dao.IConfirmationTokenDao;
import com.littlejoys.dao.IRoleDao;
import com.littlejoys.dao.IUserDao;
import com.littlejoys.dto.UserDTO;
import com.littlejoys.entity.ConfirmationToken;
import com.littlejoys.entity.Role;
import com.littlejoys.entity.User;
import com.littlejoys.exception.ResourceAlreadyExistException;
import com.littlejoys.exception.ResourceNotFoundException;

class UserServiceTest {

	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private UserService userService;

	@Mock
	private IUserDao userDao;

	@Mock
	private EmailService emailService;

	@Mock
	private IRoleDao roleDao;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private IConfirmationTokenDao confirmationTokenDao;

	private User user;
	private UserDTO userDTO;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		Role userRole = new Role("user", "default role for user");
		Set<Role> role = new HashSet<>();
		role.add(userRole);
		user = new User("testUser", "test@mail.com", "test@123", "9850909090", "inactive", null, role, null);
		userDTO = new UserDTO("testUser", "test@mail.com", "test@123", "9850909090", "inactive", null, role, null);
	}

	@Test
	void testGetUserById_ValidName_UserFound() {
		String username = "testUser";
		when(userDao.findByName(username)).thenReturn(user);

		UserDTO foundUserDTO = userService.getUserById(username);
		UserDTO expectedUserDTO = modelMapper.map(user, UserDTO.class);

		assertEquals(expectedUserDTO, foundUserDTO);
		verify(userDao, times(1)).findByName(username);
	}

	@Test
	void testWhenUserIdNotFound_ShouldThrowException() {
		String name = "wrongName";
		Throwable thrown = assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(name));
		assertEquals("User does not exist", thrown.getMessage());
	}

	@Test
	void testEnableUser_InactiveUser_ShouldEnableUser() throws ResourceAlreadyExistException {
		String userName = "testuser";
		User userToBeFound = new User();
		userToBeFound.setName(userName);
		userToBeFound.setStatus("inactive");

		Mockito.when(userDao.findByName(Mockito.anyString())).thenReturn(userToBeFound);
		Mockito.when(userDao.save(Mockito.any(User.class))).thenReturn(userToBeFound);

		String result = userService.enableUser(userName);

		assertEquals("Status changed to active for " + userName, result);
		assertEquals("active", userToBeFound.getStatus());
		Mockito.verify(userDao, Mockito.times(1)).save(Mockito.any(User.class));
	}

	@Test
	void testEnableUser_ActiveUser_ShouldThrowException() throws ResourceAlreadyExistException {
		String userName = "testuser";
		User userToBeFound = new User();
		userToBeFound.setName(userName);
		userToBeFound.setStatus("active");

		Mockito.when(userDao.findByName(Mockito.anyString())).thenReturn(userToBeFound);

		assertThrows(ResourceAlreadyExistException.class, () -> userService.enableUser(userName));
		Mockito.verify(userDao, Mockito.times(0)).save(Mockito.any(User.class));
	}

	@Test
	void testEnableUser_NonExistingUser_ShouldThrowException() throws ResourceAlreadyExistException {
		String userName = "nonexistinguser";

		Mockito.when(userDao.findByName(Mockito.anyString())).thenReturn(null);

		assertThrows(ResourceNotFoundException.class, () -> userService.enableUser(userName));
		Mockito.verify(userDao, Mockito.times(0)).save(Mockito.any(User.class));
	}

	@Test
	void testDisableUser_NonExistingUser_ShouldThrowException() throws ResourceAlreadyExistException {
		String userName = "nonexistinguser";

		Mockito.when(userDao.findByName(Mockito.anyString())).thenReturn(null);

		assertThrows(ResourceNotFoundException.class, () -> userService.disableUser(userName));
		Mockito.verify(userDao, Mockito.times(0)).save(Mockito.any(User.class));
	}

	@Test
	void testDisableUser_InactiveUser_ShouldThrowException() throws ResourceAlreadyExistException {
		String userName = "testuser";
		User userToBeFound = new User();
		userToBeFound.setName(userName);
		userToBeFound.setStatus("inactive");

		Mockito.when(userDao.findByName(Mockito.anyString())).thenReturn(userToBeFound);

		assertThrows(ResourceAlreadyExistException.class, () -> userService.disableUser(userName));
		Mockito.verify(userDao, Mockito.times(0)).save(Mockito.any(User.class));
	}

	@Test
	void testDisableUser_ActiveUser_ShouldDisableUser() throws ResourceAlreadyExistException {
		String userName = "testuser";
		User userToBeFound = new User();
		userToBeFound.setName(userName);
		userToBeFound.setStatus("active");

		Mockito.when(userDao.findByName(Mockito.anyString())).thenReturn(userToBeFound);
		Mockito.when(userDao.save(Mockito.any(User.class))).thenReturn(userToBeFound);

		String result = userService.disableUser(userName);

		assertEquals("Status changed to inactive for " + userName, result);
		assertEquals("inactive", userToBeFound.getStatus());
		Mockito.verify(userDao, Mockito.times(1)).save(Mockito.any(User.class));
	}

	@Test
	void testGetEncodedPassword() {
		String rawPassword = "myPassword";
		String encodedPassword = passwordEncoder.encode(rawPassword);

		Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn(encodedPassword);

		String result = userService.getEncodedPassword(rawPassword);

		assertEquals(encodedPassword, result);
	}

	@Test
	void testCreateAndSendConfirmationTokenViaEmail() throws MessagingException {
		ConfirmationToken confirmationToken = new ConfirmationToken(user);
		String expectedEmailBody = "To confirm your account, please click here : "
				+ "http://172.27.232.112:8080/api/user/confirm-account?token="
				+ confirmationToken.getConfirmationToken();
		String expectedEmailSubject = "Complete Registration!";
		when(confirmationTokenDao.save(any(ConfirmationToken.class))).thenReturn(confirmationToken);

		userService.createAndSendConfirmationTokenViaEmail(user);

		verify(confirmationTokenDao, times(1)).save(any(ConfirmationToken.class));
	}

	@Test
	void testCheckIfValidOldPassword_ValidPassword_ReturnsTrue() {
		String oldPasswordToMatch = "oldPassword";
		when(passwordEncoder.matches(eq(oldPasswordToMatch), eq(user.getPassword()))).thenReturn(true);

		boolean result = userService.checkIfValidOldPassword(user, oldPasswordToMatch);

		assertTrue(result);
		verify(passwordEncoder, times(1)).matches(eq(oldPasswordToMatch), eq(user.getPassword()));
	}

	@Test
	void testCheckIfValidOldPassword_InvalidPassword_ReturnsFalse() {
		String oldPasswordToMatch = "oldPassword";
		when(passwordEncoder.matches(eq(oldPasswordToMatch), eq(user.getPassword()))).thenReturn(false);

		boolean result = userService.checkIfValidOldPassword(user, oldPasswordToMatch);

		assertFalse(result);
		verify(passwordEncoder, times(1)).matches(eq(oldPasswordToMatch), eq(user.getPassword()));
	}

}
