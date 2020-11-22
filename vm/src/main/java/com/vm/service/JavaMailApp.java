package com.vm.service;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMailApp {

	private String userName = "joaohora84@gmail.com";
	private String senha = "Halynne1204%%**";

	private String listaDestinastarios = "";
	private String nomeRemetente = "";
	private String assuntoEmail = "";
	private String textoEmail = "";

public void ObjetoEnviaEmail(String listaDestinastarios, String nomeRemetente, String assuntoEmail, String textoEmail) {
	
	this.listaDestinastarios = listaDestinastarios;
	this.nomeRemetente = nomeRemetente;
	this.assuntoEmail = assuntoEmail;
	this.textoEmail = textoEmail;
}

	public void enviarEmail() {

		Properties properties = new Properties();

		properties.put("mail.smtp.ssl.trust", "*");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, senha);
			}
		});

		
		session.setDebug(true);

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("joaohora84@gmail.com"));
			

			Address[] toUser = InternetAddress.parse("joaohora84@gmail.com");

			message.setRecipients(Message.RecipientType.TO, toUser);

			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject(assuntoEmail);
			message.setText(textoEmail);
			/** Método para enviar a mensagem criada */
			Transport.send(message);

			System.out.println("Feito!!!");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
