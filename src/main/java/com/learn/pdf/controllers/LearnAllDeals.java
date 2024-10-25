package com.learn.pdf.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.learn.pdf.entity.DemoFileupload;
import com.learn.pdf.entity.Person;
import com.learn.pdf.entity.PersonProductWrapper;
import com.learn.pdf.entity.Product;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/learn/")
public class LearnAllDeals {
	
	@Autowired
	private ResourceLoader resourceLoader;

	@PostMapping("mix-test")
	public void getFileWithOtherData(@RequestPart("file") MultipartFile file, @RequestPart("name") String name) {
		System.out.println(file.getOriginalFilename());
		System.out.println(name);
	}
	
	@PostMapping("mix-test-1")
	public String getFileWithOtherData(@RequestParam("file") MultipartFile file,
            @RequestParam MultiValueMap<String, String> formData) {
		
		System.out.println(file.getOriginalFilename());
//		System.out.println(demoFileupload.getFile());
		System.out.println(formData.getFirst("name"));
		System.out.println(formData.getFirst("age"));
		if (file.isEmpty()) {
            return "No file uploaded.";
        }
        try {
        	
            // Get the file content as an input stream
            InputStream inputStream = file.getInputStream();
            Resource resource = resourceLoader.getResource("classpath:static/");
    	    String staticPath = resource.getURL().getPath() + "images/";
    	    String fileName = file.getOriginalFilename();
    	    staticPath = staticPath + fileName;
    	    
            // Create a file on the server with the same name as the uploaded file
            File serverFile = new File(staticPath);
            OutputStream outputStream = new FileOutputStream(serverFile);

            // Copy the file content from the input stream to the server file
            StreamUtils.copy(inputStream, outputStream);

            // Close the streams
            inputStream.close();
            outputStream.close();
            System.out.println(staticPath);
            return "File uploaded successfully.";
        } catch (IOException e) {
            e.printStackTrace();
            return "File upload failed.";
        }
	}
	
	@PostMapping("mix-test-2")
	public void getFileWithOtherData1(@RequestPart("file") MultipartFile file, @RequestParam("data") DemoFileupload demoFileupload) {
		
//		System.out.println(file.getOriginalFilename());
////	System.out.println(demoFileupload.getFile());
//		System.out.println(formData.getFirst("name"));
//		System.out.println(formData.getFirst("age"));
	}
	
	@GetMapping("get-xl")
	public void showSomething() {
		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("Sheet1");
			
			// Create header row
			Row headerRow = sheet.createRow(0);
			Cell headerCell1 = headerRow.createCell(0);
			headerCell1.setCellValue("Name");
			Cell headerCell2 = headerRow.createCell(1);
			headerCell2.setCellValue("Email");

			// Create data rows
			Row dataRow1 = sheet.createRow(1);
			Cell dataCell1 = dataRow1.createCell(0);
			dataCell1.setCellValue("John Doe");
			Cell dataCell2 = dataRow1.createCell(1);
			dataCell2.setCellValue("john.doe@example.com");

			Row dataRow2 = sheet.createRow(2);
			Cell dataCell3 = dataRow2.createCell(0);
			dataCell3.setCellValue("Jane Smith");
			Cell dataCell4 = dataRow2.createCell(1);
			dataCell4.setCellValue("jane.smith@example.com");

			// Auto-size columns for better visibility of data
			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);

			// Write the workbook to a file
			try (FileOutputStream fileOut = new FileOutputStream("demo.xlsx")) {
			    workbook.write(fileOut);
			    System.out.println("Excel file generated successfully!");
			} catch (IOException e) {
			    e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@GetMapping("get-xml")
	public void generateXml() throws JsonProcessingException {
		Person person = new Person();
        person.setName("John Doe");
        person.setAge(30);
        Product products = new Product("Shampoo", 10);
        person.setProduct(products);
//		XmlMapper xmlMapper = new XmlMapper();
		try {
//            String xmlString = xmlMapper.writeValueAsString(person);

            FileWriter fileWriter = new FileWriter("person.xml");
//            fileWriter.write(xmlString);
            fileWriter.close();

            System.out.println("XML file generated successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}
	
	@PostMapping("get-multiple-data")
	public String getMultipleObjectInput(@RequestBody PersonProductWrapper wrappedData) {
		Person person = wrappedData.getPerson();
		Product product = wrappedData.getProduct();
		System.out.println(person.toString());
		System.out.println(product.toString());
		return "hello";
	}
}
