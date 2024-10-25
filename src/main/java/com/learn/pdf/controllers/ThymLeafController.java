package com.learn.pdf.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("th/api/")
public class ThymLeafController {

	@GetMapping("first")
	public String firstThym(Model model) {
		String message = "Hello There, How are you";
		model.addAttribute("message", message);
		return "first-thy";
	}
	
}
