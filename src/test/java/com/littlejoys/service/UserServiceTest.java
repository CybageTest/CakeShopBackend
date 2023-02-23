package com.littlejoys.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.littlejoys.dao.IRoleDao;
import com.littlejoys.dao.IUserDao;
import com.littlejoys.demo.CakeShopApplication;
import com.littlejoys.entity.Role;
import com.littlejoys.entity.User;


class UserServiceTest {

	@InjectMocks
	private UserService userService;

	@Mock
	private IUserDao userDao;

	@Mock
	private IRoleDao roleDao;

	@BeforeEach
	void setup() {
		Role userRole = new Role("user", "default role for user");
		Set<Role> role = new HashSet<>();
		role.add(userRole);
		Optional<User> user1 = Optional
				.of(new User("abc", "abc@mail.com", "abhi@123", "9850909090", "inactive", null, role, null));
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
