package com.learn.pdf.controllers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.learn.pdf.entity.User;
import com.learn.pdf.helper.FileUploadHelper;
import com.learn.pdf.service.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user/api/")
public class UsersController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	@Autowired
	private FileUploadHelper fileUploadHelper;
	
	@GetMapping("all-users")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}
	
	@PostMapping("add-user")
	public void addUser(@RequestBody User user) {
		User theUser = new User();
		theUser.setId(user.getId());
		theUser.setFirstName(user.getFirstName());
		theUser.setLastName(user.getLastName());
		theUser.setEmail(user.getEmail());
		
		userService.addUser(user);
	}
	
	@PutMapping("update-user")
	public User UpdateUser(@RequestBody User user) {
		User theUser = new User();
		theUser.setId(user.getId());
		theUser.setFirstName(user.getFirstName());
		theUser.setLastName(user.getLastName());
		theUser.setEmail(user.getEmail());
		
		User addesUser = userService.addUser(user);
		return addesUser;
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteStudent(@PathVariable long id) {
		return userService.deleteStudent(id);
	}
	
	@GetMapping("/pdf")
	public void generatePdf() throws Exception {
	    // Create a Spring model with the data to insert into the Thymeleaf template
	    Map<String, Object> model = new HashMap<>();
	    model.put("title", "My PDF Document");
	    model.put("content", "This is the content of my PDF document. Update again....");

	    // Use the Thymeleaf template engine to render the template as an HTML string
	    String html = templateEngine.process("example-template", new Context(Locale.getDefault(), model));

	    // Use Flying Saucer to convert the HTML string to a PDF document
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    ITextRenderer renderer = new ITextRenderer();
	    renderer.setDocumentFromString(html);
	    renderer.layout();
	    renderer.createPDF(out);
	    
	    Resource resource = resourceLoader.getResource("classpath:static/");
	    String staticPath = resource.getURL().getPath();
	    
	    System.out.println(staticPath);
	    
	    byte[] pdfContent = out.toByteArray();
	    String filePath = staticPath + "document2.pdf";
	    File file = new File(filePath);
	    FileOutputStream fos = new FileOutputStream(file);
	    fos.write(pdfContent);
	    fos.close();
	    
// 		Set the response headers and return the PDF document as a byte array
//	    org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
//	    headers.setContentType(MediaType.APPLICATION_PDF);
//	    headers.setContentDispositionFormData("filename", "my-pdf-document.pdf");
//	    return new ResponseEntity<>(out.toByteArray(), headers, HttpStatus.OK);
	}		  
	
	@PostMapping("/upload-file")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
		try {
			if(file.isEmpty()) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Request Must Contain file.");
			}
			
			// check file content type
			if(!file.getContentType().equals("image/jpeg")) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Only JPEG Supported");
			}
			
			boolean f = fileUploadHelper.uploadFile(file);
			if(f) {
				return ResponseEntity.ok("File Uploaded Successfully");
			}
		}
		catch (Exception e) {

		}
	    
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong ! Plese try again");
	}

	@PostMapping("/upload")
    public String uploadFileOldway(@RequestParam("file") MultipartFile file) {
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

            return "File uploaded successfully.";
        } catch (IOException e) {
            e.printStackTrace();
            return "File upload failed.";
        }
    }

	@PostMapping("update-by-id")
	public ResponseEntity<User> updateById(@RequestParam Long id, @RequestParam String email){
		Optional<User> user = userService.findUser(id);
		User theUser = new User();
		if(user.isPresent()) {
			theUser = user.get();
			theUser.setEmail(email);
			if(theUser != null) {
				userService.addUser(theUser);
			}
		}
		return new ResponseEntity<>(theUser, HttpStatus.OK);
	}
	
}
