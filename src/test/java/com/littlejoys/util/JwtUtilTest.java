package com.littlejoys.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

class JwtUtilTest {

	@Mock
	private UserDetails userDetails;

	@InjectMocks
	private JwtUtil jwtUtil;

	private String username = "testuser";
	private static final String SECRET_KEY = "Secretkeytolearn";
	private static final int TOKEN_VALIDITY = 3600 * 5;

	public JwtUtilTest() {
		MockitoAnnotations.openMocks(this);
	}

	private String generateValidToken() {
		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
	}

	private UserDetails createMockUserDetails(String username) {
		return new User(username, "testpassword", Collections.emptyList());
	}

	@Test
	void testGetUsernameFromToken_ValidToken_ShouldReturnUsername() {
		String token = generateValidToken();

		String username = jwtUtil.getUsernameFromToken(token);

		assertEquals("testuser", username);
	}

	@Test
	void testGetExpirationDateFromToken_ValidToken_ShouldReturnExpirationDate() {
		String token = generateValidToken();

		Date expirationDate = jwtUtil.getExpirationDateFromToken(token);

		assertNotNull(expirationDate);
	}

	@Test
	void testIsTokenExpired_ValidTokenNotExpired_ShouldReturnFalse() {
		String token = generateValidToken();

		boolean isExpired = jwtUtil.isTokenExpired(token);

		assertFalse(isExpired);
	}

	@Test
	void testValidateToken_ValidTokenAndMatchingUserDetails_ShouldReturnTrue() {
		String token = generateValidToken();
		UserDetails userDetails = createMockUserDetails(username);

		boolean isValid = jwtUtil.validateToken(token, userDetails);

		assertTrue(isValid);
	}

	@Test
	void testGenerateToken_ValidUserDetails_ShouldReturnToken() {
		UserDetails userDetails = createMockUserDetails(username);

		String token = jwtUtil.generateToken(userDetails);
		
		assertNotNull(token);
	}
}
