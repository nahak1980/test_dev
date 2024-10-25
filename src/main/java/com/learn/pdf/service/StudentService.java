package com.learn.pdf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learn.pdf.entity.Address;
import com.learn.pdf.entity.CreateStudentRequest;
import com.learn.pdf.entity.Student;
import com.learn.pdf.repositories.AddressRepository;
import com.learn.pdf.repositories.StudentRepository;

@Service
public class StudentService {
	
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	AddressRepository addressRepository;

	public Student addNewStudent(CreateStudentRequest createStudentRequest) {
		Student student = new Student(createStudentRequest);
		
		Address address = new Address();
		address.setStreet(createStudentRequest.getStreet());
		address.setCity(createStudentRequest.getCity());
		address = addressRepository.save(address);
		
		student.setAddress(address);
		student = studentRepository.save(student);
		return student;
	}
	
}
