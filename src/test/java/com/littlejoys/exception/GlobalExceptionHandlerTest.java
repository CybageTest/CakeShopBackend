package com.littlejoys.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class GlobalExceptionHandlerTest {

	@InjectMocks
	private GlobalExceptionHandler globalExceptionHandler;

	private static final HttpStatus UNAUTHORIZED = HttpStatus.UNAUTHORIZED;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testHandleExceptionResourceAlreadyExistException() {
		ResourceAlreadyExistException exception = new ResourceAlreadyExistException("Resource already exists");
		ResponseEntity<String> expectedResponse = new ResponseEntity<>("Resource already exists", HttpStatus.CONFLICT);

		ResponseEntity<String> actualResponse = globalExceptionHandler.handleException(exception);

		assertEquals(expectedResponse, actualResponse);
		assertEquals(HttpStatus.CONFLICT, actualResponse.getStatusCode());
		assertEquals("Resource already exists", actualResponse.getBody());
	}

	@Test
	void testHandleExceptionResourceNotFoundException() {
		ResourceNotFoundException exception = new ResourceNotFoundException("Resource does not exist");
		ResponseEntity<String> expectedResponse = new ResponseEntity<>("Resource does not exist", HttpStatus.NOT_FOUND);
		ResponseEntity<String> actualResponse = globalExceptionHandler.handleException(exception);

		assertEquals(expectedResponse, actualResponse);
		assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
		assertEquals("Resource does not exist", actualResponse.getBody());
	}

	@Test
	void testHandleExceptionResourceCannotBeNullException() {
		ResourceCannotBeNullException exception = new ResourceCannotBeNullException("Resource cannot be empty");
		ResponseEntity<String> expectedResponse = new ResponseEntity<>("Resource cannot be empty",
				HttpStatus.BAD_REQUEST);
		ResponseEntity<String> actualResponse = globalExceptionHandler.handleException(exception);

		assertEquals(expectedResponse, actualResponse);
		assertEquals(HttpStatus.BAD_REQUEST, actualResponse.getStatusCode());
		assertEquals("Resource cannot be empty", actualResponse.getBody());
	}

	@Test
	void testHandleExceptionInvalidCredentialsException() {
		InvalidCredentialsException exception = new InvalidCredentialsException("Credentials does not match");
		ResponseEntity<String> expectedResponse = new ResponseEntity<>("Credentials does not match", UNAUTHORIZED);
		ResponseEntity<String> actualResponse = globalExceptionHandler.handleException(exception);

		assertEquals(expectedResponse, actualResponse);
		assertEquals(UNAUTHORIZED, actualResponse.getStatusCode());
		assertEquals("Credentials does not match", actualResponse.getBody());
	}

	@Test
	void testHandleExceptionInvalidOldPasswordException() {
		InvalidOldPasswordException exception = new InvalidOldPasswordException("Old password does not match");
		ResponseEntity<String> expectedResponse = new ResponseEntity<>("Old password does not match",
				HttpStatus.CONFLICT);
		ResponseEntity<String> actualResponse = globalExceptionHandler.handleException(exception);

		assertEquals(expectedResponse, actualResponse);
		assertEquals(HttpStatus.CONFLICT, actualResponse.getStatusCode());
		assertEquals("Old password does not match", actualResponse.getBody());
	}

	@Test
	void testHandleExceptionUserDisabledException() {
		UserDisabledException exception = new UserDisabledException("User is disabled for this account");
		ResponseEntity<String> expectedResponse = new ResponseEntity<>("User is disabled for this account",
				UNAUTHORIZED);
		ResponseEntity<String> actualResponse = globalExceptionHandler.handleException(exception);

		assertEquals(expectedResponse, actualResponse);
		assertEquals(UNAUTHORIZED, actualResponse.getStatusCode());
		assertEquals("User is disabled for this account", actualResponse.getBody());
	}

}
