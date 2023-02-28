package com.littlejoys.service;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import com.littlejoys.dao.IRoleDao;
import com.littlejoys.dao.IUserDao;
import com.littlejoys.dto.UserDTO;
import com.littlejoys.entity.Role;
import com.littlejoys.entity.User;

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
		Role userRole = new Role("user", "default role for user");
		Set<Role> role = new HashSet<>();
		role.add(userRole);
		user = new User("testUser", "test@mail.com", "test@123", "9850909090", "inactive", null, role, null);
		userDTO = new UserDTO("testUserDto", "testDto@mail.com", "testDto@123", "9850909090", "inactive", null, role,
				null);
	}

	@Test
	void getUserById_Sucess() {

	}

//	@Test
//	public void declineUserIfSameUserName() {
//		User userToBeTested = new User("abc", "abc@mail.com", "abhi@123", "9850909090", "inactive", null, null, null);
//		Mockito.when(userDao.save(userToBeTested)).thenReturn(userToBeTested);
//		Optional<User> findUserByName = userDao.findById("abc");
//		if (findUserByName != null) {
//			Exception exception = assertThrows(ResourceAlreadyExistException.class, () -> {
//				assertEquals(userToBeTested, userService.createNewUser(userToBeTested));
//			});
//			String expectedMessage = "User does not exist";
//			String actualMessage = exception.getMessage();
//
//			assertTrue(actualMessage.contains(expectedMessage));
//		}
//	}
}
