package com.locker.service;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class EmailService {
    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

	@Value("${spring.mail.username}")
	private String MESSAGE_FROM;

    @Value("${locker-app.host}")
    private String APPLICATION_HOST;

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

    public void sendActivationLink(String email, String activationCode) throws UnsupportedEncodingException {
        SimpleMailMessage message = null;

        String path = String.format("activation/%s", activationCode);
        String url = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(APPLICATION_HOST)
                .port(8080)
                .path(path).build()
                .encode("UTF-8").toUriString();

        try {
            message = new SimpleMailMessage();
            message.setFrom(MESSAGE_FROM);
            message.setTo(email);
            message.setSubject("Need to activate");
            message.setText("Dear " + email + "! \n \n You need to activate yourself to validate your email.\n\n" +
            "Activation link is:\n" + url);
            javaMailSender.send(message);

            log.info("Activation email sent successfully to: " + email);

        } catch (Exception e) {
            log.error("Exception occured during activation email sending to: " + email + "  " + e);
        }
    }


}
