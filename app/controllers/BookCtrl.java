package controllers;

import java.util.List;

import models.Book;
import models.Serie;

@Check("book-manager")
public class BookCtrl extends LoggedApplication {

	public static void prepareAdd() {
		List<Serie> listeSeries = Serie.find("order by name").fetch();
		render(listeSeries);
	}

	public static void addBook(final Book book) {
		book.create();
	}
}
