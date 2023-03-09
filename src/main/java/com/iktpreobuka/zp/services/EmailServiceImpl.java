package com.iktpreobuka.zp.services;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.iktpreobuka.zp.dtos.EmailDTO;

@Service
public class EmailServiceImpl implements EmailService {

	
	@Autowired
	public JavaMailSender emailSender;
	
	@Override
	public void sendMessageWithAttachment(EmailDTO emailDTO, String pathToAttachment) {
		MimeMessage message = emailSender.createMimeMessage();
		
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			
			helper.setTo(emailDTO.getTo());
			helper.setSubject(emailDTO.getSubject());
			helper.setText(emailDTO.getText(), false);
			FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
			helper.addAttachment(file.getFilename(), file);
			emailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

}
