package com.iktpreobuka.zp.services;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
	public String uploadFile(MultipartFile file);
}
