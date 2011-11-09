package controllers;

import java.util.List;

import models.Book;
import models.OwnedBook;

public class Application extends LoggedApplication {

	public static void index() {
		List<Book> books = Book.find("order by number asc").fetch();
		List<OwnedBook> ownedBooks = OwnedBook.findAll();
		render(books, ownedBooks);
	}

}