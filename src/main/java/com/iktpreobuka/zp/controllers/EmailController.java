package com.iktpreobuka.zp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.zp.dtos.EmailDTO;
import com.iktpreobuka.zp.services.EmailService;

@RestController
@RequestMapping(path = "/finalProject")
public class EmailController {

	@Autowired
	private EmailService emailService;
	
	private static String PATH_TO_ATTACHMENT = "G:\\zavrsniProjekat\\example_zavrsni_projekat\\logs";

	@RequestMapping(method = RequestMethod.POST, path = "/emailWithAttachment")
	public String sendMessageWithAttachment(@RequestBody EmailDTO emailDTO){
		if(emailDTO.getTo() == null || emailDTO.getTo().equals(" ")) {
			return "Please provide a value for TO";
		}
		if(emailDTO.getSubject() == null || emailDTO.getSubject().equals(" ")) {
			return "Please provide a value for SUBJECT";
		}
		if(emailDTO.getText() == null || emailDTO.getText().equals(" ")) {
			return "Please provide a value for TEXT";
		}
		emailService.sendMessageWithAttachment(emailDTO, PATH_TO_ATTACHMENT);
		
		return "Your email has been sent!";
	}
}
