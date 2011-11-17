package controllers;

import java.util.List;

import models.Book;
import models.OwnedBook;
import models.Serie;

import com.kebuu.JPQLHelper;

/**
 * Controler for action related to series
 */
public class SerieCtrl extends LoggedApplication {

	/**
	 * Returns the list of series for the connected user.
	 */
	public static void userSeries() {
		List<Serie> userSeries = Serie.find("select distinct s " +
				"from OwnedBook ob " +
				"join ob.baseBook bb " +
				"join bb.serie s " +
				"where ob.owner.id = (:userId) order by s.name")
			.bind("userId", getConnectedUser().id).fetch();
		render(userSeries);
	}

	/**
	 * Returns a list with all books of a given the connected owns and 
	 * a list with all books of a given the connected doesn't own
	 * @param serieId the id of the serie concerned
	 */
	public static void booksInSerie(final Long serieId) {
		// Get the serie
		Serie serie = Serie.findById(serieId);
		
		// Gets books owned by the connected user in the serie 
		List<Book> booksInSerieForUser = Serie.find("select bb from OwnedBook ob join ob.baseBook bb join bb.serie s " +
		"where s = (:serie) and ob.owner.id = (:userId) order by bb.number")
		.bind("serie", serie)
		.bind("userId", getConnectedUser().id).fetch();
		
		// Gets books not owned by the connected user in the serie 
		@SuppressWarnings("unchecked")
		List<Book> booksInSerie = (List<Book>) new JPQLHelper("select b " +
				"from Book b " +
				"join b.serie s ")
		.addCondition("s", "serie", serie)
		.addNotInCondition("b", "booksInSerieForUser", booksInSerieForUser)
		.addOrderByCondition("b.number").fetch();
		
		render(booksInSerie, booksInSerieForUser, serie);
	}

	/**
	 * Deletes a book in the collection of the connected user.
	 * @param serieId the serie of the book  (used to redirect)
	 * @param bookId the book to delete
	 */
	public static void deleteBookInSerie(final Long serieId, final Long bookId) {
		OwnedBook.delete("delete from OwnedBook ob " +
				"where ob.owner.id = ? " +
				"and ob.baseBook.id = ? ", 
				getConnectedUser().id, bookId);
		
		booksInSerie(serieId);
	}

	/**
	 * Adds a book in the collection of the connected user.
	 * @param serieId the serie of the book  (used to redirect)
	 * @param bookId the book to add
	 */
	public static void addBookInSerie(final Long serieId, final Long bookId) {
		OwnedBook ownedBook = new OwnedBook((Book) Book.findById(bookId), getConnectedUser());
		ownedBook.create();
		booksInSerie(serieId);
	}
}
