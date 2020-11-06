package com.ateam.paw_pals.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ateam.paw_pals.model.api.FileUploadResponse;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.servlet.http.HttpServletRequest;

@Service
public class FileService {
	
	@Value("${file.upload-dir}")
	private String fileUploadDir;


	public FileUploadResponse uploadFile(MultipartFile file, String newFileName) throws Exception {
		try {
			String cleanedfileName = org.springframework.util.StringUtils.cleanPath(newFileName);
			Path targetLocation = getPath(cleanedfileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			
			FileUploadResponse res = new FileUploadResponse();
			res.setFileName(cleanedfileName);
			res.setContentType(file.getContentType());
			res.setSize(file.getSize());
			
			return res;
		} catch (Exception ex) {
			throw new IllegalStateException("Could not store file. Please try again!", ex);
		}
	}

	public ResponseEntity<Resource> downloadFile(String fileName, HttpServletRequest request) throws Exception {
		try {
			Path filePath = getPath(fileName);
			Resource resource = new UrlResource(filePath.toUri());
			
			if (!resource.exists()) {
				throw new FileNotFoundException("File not found " + fileName);
			}
			
			String contentType = null;
	        contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

	        if(contentType == null) {
	            contentType = "application/octet-stream";
	        }

	        return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(contentType))
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
	                .body(resource);
		} catch (MalformedURLException ex) {
			throw new FileNotFoundException("File not found " + fileName);
		}
	}
	
	// TODO find better name
	private Path getPath(String fileName) {
		Path fileStorageLocation = Paths.get(fileUploadDir).toAbsolutePath().normalize();
		return fileStorageLocation.resolve(fileName);
	}
}
