package com.casestudy.pharma.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailDTO {
    private String to;
    private String subject;
    private String body;
    private byte[] pdfAttachment;
}
