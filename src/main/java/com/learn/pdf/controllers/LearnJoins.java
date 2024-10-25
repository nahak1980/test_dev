package com.learn.pdf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.pdf.entity.CreateStudentRequest;
import com.learn.pdf.entity.Student;
import com.learn.pdf.service.StudentService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/joins/")
public class LearnJoins {
	
	@Autowired
	StudentService studentService;
	
	@GetMapping("/test")
	public String testController() {
		return "hello spring world";
	}
	
	@PostMapping("/create-student")
	public Student addNewStudent(@RequestBody CreateStudentRequest createStudentRequest) {
		Student student = studentService.addNewStudent(createStudentRequest);
		return student;
	}

}
