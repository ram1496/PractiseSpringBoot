package com.springboot.practise.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.practise.datamodel.Course;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

// course
//Course:id,name,author


@RestController
@Slf4j
public class CourseController {
	
	//to map with a particular URL
	@RequestMapping("/courses")
	public List<Course> retrieveAllCourses(){
		return Arrays.asList(
				new Course(1,"Learn AWS","in28minutes"),
				new Course (2,"Learn DevOps","in28minutes"),
				new Course (3,"Learn JAVA","in28minutes"));
	}
}
