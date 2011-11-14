package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import play.data.validation.CheckWith;
import play.data.validation.Required;
import play.db.jpa.Model;
import check.OwnedBookBorrowerCheck;

@Entity
public class OwnedBook extends Model {

	public OwnedBook() {
		super();
	}

	public OwnedBook(final Book baseBook, final User owner) {
		super();
		this.baseBook = baseBook;
		this.owner = owner;
	}

	@Required
	@ManyToOne
	public Book baseBook;

	@Required
	@ManyToOne
	public User owner;

	@OneToOne
	@CheckWith(OwnedBookBorrowerCheck.class)
	public User borrower;

	@Override
	public String toString() {
		return this.baseBook.toString() + " (" + this.owner.pseudo + ")";
	}
}
