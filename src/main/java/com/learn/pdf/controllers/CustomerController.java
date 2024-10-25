package com.learn.pdf.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.learn.pdf.entity.Customer;
import com.learn.pdf.service.CustomerService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/customer/")
@AllArgsConstructor
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("{id}")
	public ResponseEntity<Optional> getCustomer(@PathVariable Long id) {
		Optional<Customer> customer = customerService.getCustomerById(id);
		return ResponseEntity.ok(customer);
	}

}
