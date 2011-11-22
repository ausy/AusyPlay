package controllers;

import java.util.List;

import models.Book;
import models.OwnedBook;
import models.Serie;
import play.data.validation.Valid;
import play.data.validation.Validation;
import play.i18n.Messages;

import com.kebuu.JPQLHelper;

@Check("book-manager")
public class BookCtrl extends LoggedApplication {

	public static void prepareAdd() {
		List<Serie> listeSeries = Serie.find("order by name").fetch();
		render(listeSeries);
	}

	public static void addBook(final @Valid Book book) {
		if (Validation.hasErrors()) {
			
			// Specific treatment for isbn, just to provide example
			if(!Validation.errors("book.isbn").isEmpty()) {
				flash.put("error_isbn", Messages.get("error.book.isbn.msg"));
			}
			
			params.flash(); // add http parameters to the flash scope
			Validation.keep(); // keep the errors for the next request
			prepareAdd();
		}
		
		if (book.serie.id <= 0) {
			book.serie = null;
		}
		book.create();
		flash.put("message", "La BD a été ajoutée, vous pouvez créer à nouveau.");
		
		BookCtrl.prepareAdd();
	}

	@SuppressWarnings("unchecked")
	public static void prepareCollect() {
		List<Book> connectedUserBooks = 
			Book.find("select book from OwnedBook ownedBook " +
					"join ownedBook.baseBook book " +
					"where ownedBook.owner.id = ?", getConnectedUser().id).fetch();
		
		List<Book> availableBooks = (List<Book>) new JPQLHelper("select book from Book book")
		.addNotInCondition("book", "connectedUserBooks", connectedUserBooks)
		.addOrderByCondition("book.number").fetch();
		
		render(availableBooks);
	}
	
	public static void collectBd(final Book book) {
		OwnedBook ownedBook = new OwnedBook(book, getConnectedUser());
		ownedBook.create();
		
		prepareCollect();
	}
}
