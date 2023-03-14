package com.springboot.practise.service;

import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
	public boolean authenticate(String username,String password) {
		boolean isValidUserName = username.equalsIgnoreCase("ram");
		boolean isValidPassword = username.equalsIgnoreCase("ram");
		return isValidPassword&&isValidUserName;
		
	}
}
