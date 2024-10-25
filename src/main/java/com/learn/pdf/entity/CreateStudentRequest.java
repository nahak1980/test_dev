package com.learn.pdf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateStudentRequest {
	
	private String firstName;
	private String lastName;
	private String email;
	private String street;
	private String city;
	
}
