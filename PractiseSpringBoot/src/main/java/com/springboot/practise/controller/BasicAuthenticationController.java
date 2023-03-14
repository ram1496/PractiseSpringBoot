package com.springboot.practise.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.practise.datamodel.AuthenticationBean;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class BasicAuthenticationController {
	
	@RequestMapping("/basicauth")
	public AuthenticationBean getHelloBean() {
		System.out.println("SayHelloController.getHelloBean()");
		return new AuthenticationBean("You are authenticated");
	}
}
