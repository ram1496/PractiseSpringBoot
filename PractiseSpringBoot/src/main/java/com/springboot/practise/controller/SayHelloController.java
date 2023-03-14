package com.springboot.practise.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.practise.datamodel.HelloBean;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class SayHelloController {

	@RequestMapping("/say-hello")
	@ResponseBody
	public String sayHello() {
		return "Hello! How are you";
	}
	
	@RequestMapping("/hello-bean/{name}")
	public HelloBean getHelloBean(@PathVariable String name) {
		System.out.println("SayHelloController.getHelloBean()");
		return new HelloBean(name);
	}
	
	//sayhello.jsp
}
