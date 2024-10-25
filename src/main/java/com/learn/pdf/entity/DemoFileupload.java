package com.learn.pdf.entity;



import lombok.AllArgsConstructor;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Entity;
//import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
//@Table(name="demos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemoFileupload {
//	private MultipartFile file;
	private String name;
	private String age;
}
