package controllers;

import java.util.List;

import models.Serie;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import play.libs.Mail;


public class Application extends LoggedApplication {

	/**
	 * Example of email sending
	 */
	public static void email() throws EmailException {
		Email email = new SimpleEmail();
		email.setFrom("collectionmanager.app@gmail.com");
		email.setSubject("TestMail");
		email.setMsg("This is a test mail ... :-)");
		email.addTo("christophe.tardella@gmail.com");
		Mail.send(email);
		
		renderText("Mail envoyé");
	}
	
	/**
	 * Just a method to test anything you want by calling /application/test
	 */
	public static void test() {
		List<Serie> series =  Serie.find("select serie from Serie serie join serie.books book where book.number = :tome")
		.bind("tome", new Integer(1)).fetch();
		renderText(series.toString());
	}
}