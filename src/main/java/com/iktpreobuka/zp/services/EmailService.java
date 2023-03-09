package com.iktpreobuka.zp.services;

import com.iktpreobuka.zp.dtos.EmailDTO;

public interface EmailService {
	public void sendMessageWithAttachment(EmailDTO emailDTO, String pathToAttachment);
}
