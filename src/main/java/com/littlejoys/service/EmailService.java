package com.littlejoys.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendEmail(String to, String subject, String message) throws MessagingException{
		MimeMessage msg=javaMailSender.createMimeMessage();
		MimeMessageHelper helper= new MimeMessageHelper(msg, true);
		helper.setTo(to);
		helper.setFrom("Trng2@evolvingsols.com");
		helper.setSubject(subject);
		helper.setText(message, true);
		
		javaMailSender.send(msg);
	}
	
}