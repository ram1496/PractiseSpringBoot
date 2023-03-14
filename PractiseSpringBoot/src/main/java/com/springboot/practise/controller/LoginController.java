package com.springboot.practise.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springboot.practise.service.AuthenticationService;

@Controller

public class LoginController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	//http://localhost:8080/login?name=ram 
	//Model
	
	
	private AuthenticationService authenticationService;
	
	
	public LoginController(AuthenticationService authenticationService) {
		super();
		this.authenticationService = authenticationService;
	}

	@GetMapping(value="/login")
	public String goToLoginPage() {
		return "login";
	}
	
	@PostMapping(value="/login")
	public String goToWelcomePage(@RequestParam String name,@RequestParam String password,ModelMap map) {
		//if(authenticationService.authenticate(name, password)) {
		map.put("name",name);
		map.put("password",password);
		return "welcome";
//		}else {
//			map.put("error", "Authentication Incorrect");
//			return "login";
//		}
	}

}
