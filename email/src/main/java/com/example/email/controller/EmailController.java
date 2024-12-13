package com.example.email.controller;

import java.io.IOException;
import java.lang.System.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.email.service.EmailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/email")
public class EmailController {

	@Autowired
	private EmailService emailService;
	
	@PostMapping("/sendEmailWithAttachment")
    public void sendEmail(@RequestParam("to") String to,
            @RequestParam("subject") String subject,
            @RequestParam("body") String body,
            @RequestParam("attachment") MultipartFile attachment) {
		System.out.println("Email coming to controller");
		byte[] attachmentBytes = null;
		try {
			attachmentBytes = attachment.getBytes();
		} catch (IOException e) {
			log.error("Exception occured "+e);
		}
        emailService.sendEmailWithAttachment(to,subject,body,attachmentBytes);
    }
}
