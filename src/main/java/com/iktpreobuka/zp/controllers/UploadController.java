package com.iktpreobuka.zp.controllers;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.iktpreobuka.zp.services.UploadService;

@RestController
@RequestMapping(path = "/finalProject")
public class UploadController {
	private static final Logger log = LoggerFactory.getLogger(UploadController.class);
	@Autowired
	private UploadService uploadService;
	
	@RequestMapping(method = RequestMethod.POST, path = "/upload")
	public String uploadFile1(@RequestParam("file") MultipartFile file) throws IOException {
		String result = null;
		result = uploadService.uploadFile(file);
		log.error("An exception occured while posting a upload file!");
		return result;
	}
}
