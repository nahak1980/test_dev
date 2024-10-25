package com.learn.pdf.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learn.pdf.entity.Customer;
import com.learn.pdf.repositories.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;

	public Optional<Customer> getCustomerById(Long id) {
//		return customerRepository.findById(id);
		return customerRepository.checkFindById(id);
	}

}
