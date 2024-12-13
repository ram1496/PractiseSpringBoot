package com.casestudy.pharma.exception;

public class PharmaBusinessException extends RuntimeException{
	
	
	private static final long serialVersionUID = 1l;
	private final String errorCode;

	public PharmaBusinessException(String errorCode,String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return this.errorCode;
	}
	
}
