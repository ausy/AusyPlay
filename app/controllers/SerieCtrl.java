package controllers;

import java.util.List;

import models.Book;
import models.OwnedBook;
import models.Serie;
import play.db.jpa.GenericModel.JPAQuery;

import com.kebuu.JPQLHelper;


public class SerieCtrl extends LoggedApplication {

	public static void userSeries() {
		List<Serie> userSeries = Serie.find("select distinct s " +
				"from OwnedBook ob " +
				"join ob.baseBook bb " +
				"join bb.serie s " +
				"where ob.owner.id = (:userId) order by s.name")
			.bind("userId", getConnectedUser().id).fetch();
		render(userSeries);
	}

	public static void booksInSerie(final Long serieId) {
		List<Book> booksInSerieForUser = Serie.find("select bb from OwnedBook ob join ob.baseBook bb join bb.serie s " +
		"where s.id = (:serieId) and ob.owner.id = (:userId) order by bb.number")
		.bind("serieId", serieId)
		.bind("userId", getConnectedUser().id).fetch();
		
		JPAQuery jpaQuery = new JPQLHelper("select b " +
				"from Book b " +
				"join b.serie s ")
		.addCondition("s.id", "serieId", serieId)
		.addNotInCondition("b", "booksInSerieForUser", booksInSerieForUser)
		.addOrderByCondition("b.number").getQuery();
		
		List<Book> booksInSerie = jpaQuery.fetch();
		
		Serie serie = Serie.findById(serieId);
		
		render(booksInSerie, booksInSerieForUser, serie);
	}

	public static void deleteBookInSerie(final Long serieId, final Long bookId) {
		OwnedBook.delete("delete from OwnedBook ob " +
				"where ob.owner.id = ? " +
				"and ob.baseBook.id = ? ", 
				getConnectedUser().id, bookId);
		
		booksInSerie(serieId);
	}

	public static void addBookInSerie(final Long serieId, final Long bookId) {
		OwnedBook ownedBook = new OwnedBook((Book) Book.findById(bookId), getConnectedUser());
		ownedBook.create();
		booksInSerie(serieId);
	}
}
