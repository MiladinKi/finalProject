package com.iktpreobuka.zp.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.iktpreobuka.zp.controllers.UploadController;

@Service
public class UploadServiceImpl implements UploadService {

	private static final String UPLOAD_FOLDER = "C:\\temp\\";
	private static final Logger log = LoggerFactory.getLogger(UploadController.class);

	@Override
	public String uploadFile(MultipartFile file) {
		log.info("UploadFile method invoked " + file.getOriginalFilename());
		String fileName = file.getOriginalFilename();
		if (file.isEmpty()) {
			return "File not exists or is empty!";
		}

		try {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOAD_FOLDER, file.getOriginalFilename());
			log.info("File uploaded and stored succesfully");
			Files.write(path, bytes);
		} catch (IOException e) {
			log.error("An exception occured while uploading a file");
			e.printStackTrace();
		}
		return "Success, file is uploaded";
	}

}
