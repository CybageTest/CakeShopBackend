package com.littlejoys.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class OtpServiceTest {

	@InjectMocks
	private OtpService otpService;

	public OtpServiceTest() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGenerateOTP() {
		int otp = otpService.generateotp("test-key");
		assertTrue(otp >= 100000 && otp <= 999999);
	}

	@Test
	void testGetOTP() {
		int otp = otpService.generateotp("test-key");
		int retrievedOtp = otpService.getOtp("test-key");
		assertEquals(otp, retrievedOtp);
	}

	@Test
	void testGetNonExistingOtp() {
		OtpService otpService = new OtpService();
		int otp = otpService.getOtp("non-existing-key");
		assertEquals(0, otp);
	}

	@Test
	void testClearOtp() {
		OtpService otpService = new OtpService();
		otpService.clearOtp("test-key");
		int clearedOtp = otpService.getOtp("test-key");
		assertEquals(0, clearedOtp);
	}

	@Test
	void testOtpExpiration() throws InterruptedException {
		OtpService otpService = new OtpService();
		int otp = otpService.generateotp("test-key");
		Thread.sleep(1 * 60 * 1000); // wait for 1 minutes, cache should expire after 1 minutes
		int expiredOtp = otpService.getOtp("test-key");
		assertEquals(otp, expiredOtp);
	}

}
