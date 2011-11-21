package controllers;

import java.util.List;

import models.Book;
import models.Serie;
import play.data.validation.Valid;

public class BookCtrl extends LoggedApplication {

	public static void prepareAdd() {
		List<Serie> listeSeries = Serie.find("order by name").fetch();
		render(listeSeries);
	}

	public static void addBook(final @Valid Book book) {
		if (validation.hasErrors()) {
			params.flash(); // add http parameters to the flash scope
			validation.keep(); // keep the errors for the next request
			prepareAdd();
		}
		if (book.serie.id <= 0) {
			book.serie = null;
		}
		book.create();
		flash.put("message",
				"La BD a été ajoutée, vous pouvez créer à nouveau.");
		BookCtrl.prepareAdd();
	}

}
