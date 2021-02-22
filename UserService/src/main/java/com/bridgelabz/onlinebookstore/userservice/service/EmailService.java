package com.bridgelabz.onlinebookstore.userservice.service;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.bridgelabz.onlinebookstore.userservice.dto.EmailDTO;
import com.google.gson.Gson;

public class EmailService {

	@Autowired
	JavaMailSender mailSender;

	@Autowired
	Gson gson;

	@Autowired
	Queue queue;

	@RabbitListener(queues = "${rabbitmq.queue}")
	public void sendMail(String email) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		EmailDTO emailDTO = gson.fromJson(email, EmailDTO.class);
		mailMessage.setTo(emailDTO.getEmail());
		mailMessage.setSubject(emailDTO.getSubject());
		mailMessage.setText(emailDTO.getMessage());
		mailSender.send(mailMessage);
	}

}
