package com.littlejoys.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.littlejoys.entity.User;

class IUserDaoTest {

	@Mock
	private IUserDao userDao;

	private User user;
	private String name = "testuser";
	private String mobile = "testuser@mail.com";
	private String email = "9850909898";

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		user = new User();
		user.setName(name);
		user.setEmail(email);
		user.setMobile(mobile);
	}

	@Test
	void testFindByName_ShouldReturnUserWithMatchingName() {
		when(userDao.findByName(name)).thenReturn(user);

		User result = userDao.findByName(name);

		// Assert
		assertNotNull(result);
		assertEquals(user.getName(), result.getName());
	}

	@Test
	void testFindByMobile_ShouldReturnUserWithMatchingMobile() {
		when(userDao.findByMobile(mobile)).thenReturn(user);

		User result = userDao.findByMobile(mobile);

		assertNotNull(result);
		assertEquals(user.getMobile(), result.getMobile());
	}

	@Test
	void testFindByEmailOrMobile_ShouldReturnUserWithMatchingEmailOrMobile() {

		when(userDao.findByEmailOrMobile(email, mobile)).thenReturn(user);

		User result = userDao.findByEmailOrMobile(email, mobile);

		assertNotNull(result);
		assertEquals(user.getEmail(), result.getEmail());
		assertEquals(user.getMobile(), result.getMobile());
	}

	@Test
	void testFindByEmail_ShouldReturnUserWithMatchingEmail() {

		when(userDao.findByEmail(email)).thenReturn(user);

		User result = userDao.findByEmail(email);

		assertNotNull(result);
		assertEquals(user.getEmail(), result.getEmail());
	}

}
