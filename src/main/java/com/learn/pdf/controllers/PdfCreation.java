package com.learn.pdf.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/check/api/")
public class PdfCreation {
	
	@GetMapping("demo")
	public String getSomeOutput() {
		return "hello world in java";
	}
	
}
