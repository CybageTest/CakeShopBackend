package com.littlejoys.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.littlejoys.dao.IRoleDao;
import com.littlejoys.dao.IUserDao;
import com.littlejoys.dto.UserDTO;
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
	private IRoleDao roleDao;

	@Mock
	private PasswordEncoder passwordEncoder;

	private User user;
	private UserDTO userDTO;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		Role userRole = new Role("user", "default role for user");
		Set<Role> role = new HashSet<>();
		role.add(userRole);
		user = new User("testUser", "test@mail.com", "test@123", "9850909090", "inactive", null, role, null);
		userDTO = new UserDTO("testUserDto", "testDto@mail.com", "testDto@123", "9850909090", "inactive", null, role,
				null);
	}

	@Test
	void testGetUserById() {
		String name = "testUser";
		when(userDao.findByName(name)).thenReturn(user);
		UserDTO mappedUser = modelMapper.map(user, UserDTO.class);
		UserDTO expectedUser = userService.getUserById(name);
		assertEquals(expectedUser, mappedUser);
	}

	@Test
	void testWhenUserIdNotFound_ShouldThrowException() {
		String name = "wrongName";
		Throwable thrown = assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(name));
		assertEquals("User does not exist", thrown.getMessage());
	}

	@Test
	void testEnableUser_InactiveUser_ShouldEnableUser() throws ResourceAlreadyExistException {
		// Arrange
		String userName = "testuser";
		User userToBeFound = new User();
		userToBeFound.setName(userName);
		userToBeFound.setStatus("inactive");

		Mockito.when(userDao.findByName(Mockito.anyString())).thenReturn(userToBeFound);
		Mockito.when(userDao.save(Mockito.any(User.class))).thenReturn(userToBeFound);

		// Act
		String result = userService.enableUser(userName);

		// Assert
		assertEquals("Status changed to active for " + userName, result);
		assertEquals("active", userToBeFound.getStatus());
		Mockito.verify(userDao, Mockito.times(1)).save(Mockito.any(User.class));
	}

	@Test
	void testEnableUser_ActiveUser_ShouldThrowException() throws ResourceAlreadyExistException {
		// Arrange
		String userName = "testuser";
		User userToBeFound = new User();
		userToBeFound.setName(userName);
		userToBeFound.setStatus("active");

		Mockito.when(userDao.findByName(Mockito.anyString())).thenReturn(userToBeFound);

		// Act and Assert
		assertThrows(ResourceAlreadyExistException.class, () -> userService.enableUser(userName));
		Mockito.verify(userDao, Mockito.times(0)).save(Mockito.any(User.class));
	}

	@Test
	void testEnableUser_NonExistingUser_ShouldThrowException() throws ResourceAlreadyExistException {
		// Arrange
		String userName = "nonexistinguser";

		Mockito.when(userDao.findByName(Mockito.anyString())).thenReturn(null);

		// Act and Assert
		assertThrows(ResourceNotFoundException.class, () -> userService.enableUser(userName));
		Mockito.verify(userDao, Mockito.times(0)).save(Mockito.any(User.class));
	}

	@Test
	public void testDisableUser_NonExistingUser_ShouldThrowException() throws ResourceAlreadyExistException {
		// Arrange
		String userName = "nonexistinguser";

		Mockito.when(userDao.findByName(Mockito.anyString())).thenReturn(null);

		// Act and Assert
		assertThrows(ResourceNotFoundException.class, () -> userService.disableUser(userName));
		Mockito.verify(userDao, Mockito.times(0)).save(Mockito.any(User.class));
	}

	@Test
	public void testDisableUser_InactiveUser_ShouldThrowException() throws ResourceAlreadyExistException {
		// Arrange
		String userName = "testuser";
		User userToBeFound = new User();
		userToBeFound.setName(userName);
		userToBeFound.setStatus("inactive");

		Mockito.when(userDao.findByName(Mockito.anyString())).thenReturn(userToBeFound);

		// Act and Assert
		assertThrows(ResourceAlreadyExistException.class, () -> userService.disableUser(userName));
		Mockito.verify(userDao, Mockito.times(0)).save(Mockito.any(User.class));
	}

	@Test
	void testDisableUser_ActiveUser_ShouldDisableUser() throws ResourceAlreadyExistException {
		// Arrange
		String userName = "testuser";
		User userToBeFound = new User();
		userToBeFound.setName(userName);
		userToBeFound.setStatus("active");

		Mockito.when(userDao.findByName(Mockito.anyString())).thenReturn(userToBeFound);
		Mockito.when(userDao.save(Mockito.any(User.class))).thenReturn(userToBeFound);

		// Act
		String result = userService.disableUser(userName);

		// Assert
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

}
