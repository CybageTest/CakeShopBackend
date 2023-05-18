package com.littlejoys.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

class EmailServiceTest {

	@Mock
	private JavaMailSender javaMailSender;

	@Mock
	private MimeMessage mimeMessage;

	@Mock
	private MimeMessageHelper mimeMessageHelper;

	@InjectMocks
	private EmailService emailService;

	public EmailServiceTest() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testSendEmail_ValidEmail_ShouldSendEmail() throws MessagingException {
		// Arrange
		String to = "test@example.com";
		String subject = "Test Subject";
		String message = "Test Message";

		Mockito.when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

		// Act
		emailService.sendEmail(to, subject, message);

		// Assert
		Mockito.verify(javaMailSender, Mockito.times(1)).send(mimeMessage);
	}

}
