package controllers;

import java.text.MessageFormat;
import java.util.List;

import models.Book;
import models.OwnedBook;
import models.Serie;

import org.apache.commons.lang.ArrayUtils;

import play.Logger;
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
		// Serie's name is required if it's a new serie that has to be created
		if (book.serie.id == null) {
			Validation.required("book.serie.name", book.serie.name);

			if (book.serie.name != null) {
				Serie serie = Serie.find("byName", book.serie.name).first();
				if (serie != null) {
					Validation.addError("book.serie.name",
							"La série existe déjà",
							ArrayUtils.EMPTY_STRING_ARRAY);
				}
			}
		}

		// Validation errror treatment
		if (Validation.hasErrors()) {

			if (Logger.isDebugEnabled()) {
				for (play.data.validation.Error error : Validation.errors()) {
					Logger.debug(error.message() + "  " + error.getKey());
				}
			}

			// Specific treatment for isbn, just to provide example
			if (!Validation.errors("book.isbn").isEmpty()) {
				flash.put("error_isbn", Messages.get("error.book.isbn.msg"));
			}

			params.flash(); // add http parameters to the flash scope
			Validation.keep(); // keep the errors for the next request
		} else {

			// Create serie is needed
			if (book.serie.id == null) {
				book.serie.create();
			}

			book.create();

			// Send WebSocket message
			WebSocket.liveStream.publish(MessageFormat.format(
					"La BD ''{0}'' a été ajoutée dans la série ''{1}''",
					book.title, book.serie.name));

			flash.put("message",
					"La BD a été ajoutée, vous pouvez créer à nouveau.");
		}

		BookCtrl.prepareAdd(); // Redirection toward input form
	}

	@SuppressWarnings("unchecked")
	public static void prepareCollect() {
		List<Book> connectedUserBooks = Book
				.find("select book from OwnedBook ownedBook "
						+ "join ownedBook.baseBook book "
						+ "where ownedBook.owner.id = ?", getConnectedUser().id)
				.fetch();

		List<Book> availableBooks = (List<Book>) new JPQLHelper(
				"select book from Book book")
				.addNotInCondition("book", "connectedUserBooks",
						connectedUserBooks).addOrderByCondition("book.number")
				.fetch();

		render(availableBooks);
	}

	public static void collectBd(final Book book) {
		OwnedBook ownedBook = new OwnedBook(book, getConnectedUser());
		ownedBook.create();

		prepareCollect();
	}
}
