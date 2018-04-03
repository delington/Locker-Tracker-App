package com.locker.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

	@Value("${spring.mail.username}")
	private String MESSAGE_FROM;

	private JavaMailSender javaMailSender;

	@Autowired
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public void sendRegistrationSuccessfullMessage(String email) {
		SimpleMailMessage message = null;

		try {
			message = new SimpleMailMessage();
			message.setFrom(MESSAGE_FROM);
			message.setTo(email);
			message.setSubject("Successfull registration");
			message.setText("Dear " + email + "! \n \n Thank you for registering to the application!");
			javaMailSender.send(message);

			log.info("Registration email sent successfully to: " + email);

		} catch (Exception e) {
			log.error("Exception occured during email sending to: " + email + "  " + e);
		}


	}


}
