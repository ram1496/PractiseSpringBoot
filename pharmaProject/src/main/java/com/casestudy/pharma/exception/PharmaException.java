package com.casestudy.pharma.exception;

public class PharmaException extends RuntimeException{
private static final long serialVersionUID = 1l;
private final String errorCode;

public PharmaException(String errorCode,String message) {
	super(message);
	this.errorCode = errorCode;
}

public String getErrorCode() {
	return this.errorCode;
}
}
