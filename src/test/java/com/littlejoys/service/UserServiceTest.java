package com.littlejoys.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.littlejoys.dao.IRoleDao;
import com.littlejoys.dao.IUserDao;
import com.littlejoys.demo.CakeShopApplication;
import com.littlejoys.entity.Role;
import com.littlejoys.entity.User;

@SpringBootTest(classes = CakeShopApplication.class)
class UserServiceTest {

	@Autowired
	private UserService userService;

	@MockBean
	private IUserDao userDao;

	@MockBean
	private IRoleDao roleDao;

	@BeforeEach
	void setup() {
		Role userRole = new Role("user", "default role for user");
		Set<Role> role = new HashSet<>();
		role.add(userRole);
		Optional<User> user1 = Optional
				.of(new User("abc", "abc@mail.com", "abhi@123", "9850909090", "inactive", null, role, null));
		Mockito.when(userDao.findById("abc")).thenReturn(user1);
	}

	@Test
	 void getUserById_Sucess() {
		String name = "abc";
		User user = userService.getUserById(name);
		System.out.println(user.getRole());
		assertEquals(name, user.getName());
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
