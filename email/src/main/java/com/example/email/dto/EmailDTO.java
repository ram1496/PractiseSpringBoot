package com.example.email.dto;


import lombok.Data;

@Data
public class EmailDTO {
    private String to;
    private String subject;
    private String body;
    private byte[] pdfAttachment;
    
	public EmailDTO(String to, String subject, String body, byte[] pdfAttachment) {
		super();
		this.to = to;
		this.subject = subject;
		this.body = body;
		this.pdfAttachment = pdfAttachment;
	}
    
    
}

