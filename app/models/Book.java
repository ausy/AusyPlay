package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.data.validation.Match;
import play.data.validation.Min;
import play.data.validation.Required;

@Entity
public class Book extends BaseModel {

	private static final long serialVersionUID = 1L;

	@Required
	public String title;

	@Required
	@Min(1)
	public Long number;

	@ManyToOne
	public Serie serie;

	@Match("[0-9]{10,13}")
	public String isbn;

	@Override
	public String toString() {
		return this.number + " - " + this.title;
	}

	public static List<Serie> getAllBySerie() {
		List<Serie> allSeries = Serie.findAll();

		List<Book> unSerizedBooks = Book.find(
				"select b from Book b where b.serie is null").fetch();

		Serie unbindedSerie = Serie.getUnbindedSerie();
		for (Book book : unSerizedBooks) {
			unbindedSerie.books.add(book);
		}

		List<Serie> result = new ArrayList<Serie>(allSeries);
		result.add(unbindedSerie);

		return result;
	}
}
