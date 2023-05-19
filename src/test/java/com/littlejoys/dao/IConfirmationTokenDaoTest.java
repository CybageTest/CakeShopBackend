package com.littlejoys.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.littlejoys.entity.ConfirmationToken;
import com.littlejoys.entity.User;

class IConfirmationTokenDaoTest {

	@Mock
	private IConfirmationTokenDao confirmationTokenDao;

	public IConfirmationTokenDaoTest() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testFindByConfirmationToken_ShouldReturnMatchingConfirmationToken() {
		User user = new User();
		user.setName("testuser");
		user.setEmail("testuser@mail.com");
		user.setStatus("active");
		String token = "abc123";
		ConfirmationToken expectedToken = new ConfirmationToken(user);

		when(confirmationTokenDao.findByConfirmationToken(token)).thenReturn(expectedToken);

		// Act
		ConfirmationToken result = confirmationTokenDao.findByConfirmationToken(token);

		// Assert
		assertNotNull(result);
		assertEquals(expectedToken.getUser(), result.getUser());
		assertEquals(expectedToken.getConfirmationToken(), result.getConfirmationToken());
	}

}
