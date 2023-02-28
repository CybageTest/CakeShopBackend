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
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import com.littlejoys.dao.IRoleDao;
import com.littlejoys.dao.IUserDao;
import com.littlejoys.dto.UserDTO;
import com.littlejoys.entity.Role;
import com.littlejoys.entity.User;
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

}
