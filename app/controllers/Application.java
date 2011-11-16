package controllers;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import play.libs.Mail;


public class Application extends LoggedApplication {

	public static void email() throws EmailException {
		Email email = new SimpleEmail();
		email.setFrom("collectionmanager.app@gmail.com");
		email.setSubject("TestMail");
		email.setMsg("This is a test mail ... :-)");
		email.addTo("christophe.tardella@gmail.com");
		Mail.send(email);
		
		renderText("Mail envoyé");
	}

}