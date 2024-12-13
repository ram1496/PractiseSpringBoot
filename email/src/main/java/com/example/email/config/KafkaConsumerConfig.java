package com.example.email.config;


import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

import com.example.email.dto.EmailDTO;
import com.example.email.service.EmailService;

@EnableKafka
public class KafkaConsumerConfig {

	@Autowired
	private EmailService emailService;
	
    @KafkaListener(topics = "email-topic", groupId = "email-group")
    public void consume(String message) {
        // Split the message and extract details
        String[] parts = message.split(";");
        
        String recipientEmail = parts[0];
        String subject = parts[1];
        String text = parts[2];
        byte[] pdfFile = Base64.getDecoder().decode(parts[3]);
        // Send the email with PDF attached
        emailService.sendEmailWithAttachment(recipientEmail,subject,text,pdfFile);
    }
}
