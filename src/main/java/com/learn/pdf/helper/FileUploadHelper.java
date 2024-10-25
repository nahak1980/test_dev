package com.learn.pdf.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


@Component
public class FileUploadHelper {
	
	@Autowired
	private ResourceLoader resourceLoader;
    
	public final String UPLOAD_DIR="D:\\Mines\\Spring-boot\\pdf\\src\\main\\resources\\static\\images\\";
	
	public boolean uploadFile(MultipartFile file) {
		boolean fi = false;
		try {
			
//			Resource resource = resourceLoader.getResource("classpath:static/");
//		    String staticPath = resource.getURL().getPath() + "images/";
		    
		    String newPathString = UPLOAD_DIR + file.getOriginalFilename();
		    
		    Path destinationPath = Paths.get(newPathString);
		    
//			InputStream is = file.getInputStream();
//			byte data[] = new byte[is.available()];
//			is.read(data);
			
			// Write
			//  old way to write files in stream
//			File newFile = new File(staticPath + file.getOriginalFilename());
//			FileOutputStream fos = new FileOutputStream(newFile);
//			fos.write(data);
//			fos.close();
//			fos.flush();
			
			
			// Write 
			// new way
			Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
			
			fi = true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return fi;
	}
	
}
