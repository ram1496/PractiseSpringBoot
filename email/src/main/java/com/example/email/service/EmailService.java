package com.example.email.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.email.dto.EmailDTO;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;

@Service
public class EmailService {

	@Value("${spring.mail.username}")
	private String sender;
	
    @Autowired
    private JavaMailSender emailSender;

    // Method to send email with PDF attached
    public void sendEmailWithAttachment(String to,String subject,String text,byte[] attachment) {
    	System.out.println("Email coming to service sender "+sender +" to "+to+" subject "+subject+" text "+text);
    	
        MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(message, true);
			helper.setFrom(sender);
			helper.setTo(to);
		    helper.setSubject(subject);
		    helper.setText(text);
		 // Attach PDF file
//		    ByteArrayDataSource dataSource = new ByteArrayDataSource(attachment, "application/pdf");
//		    helper.addAttachment("file.pdf", dataSource);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		emailSender.send(message);
		System.out.println("Email sent successfully to " + to);
    }
}

