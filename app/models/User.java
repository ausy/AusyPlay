package models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.data.validation.Email;
import play.data.validation.Required;
import check.Unique;

@Entity
public class User extends BaseModel {

	private static final long serialVersionUID = 1L;
	
	public static final String KEY = "_user";

	@Required
	@Email
	@Unique
	public String email;

	@Required
	public String pseudo;

	@Required
	public String password;

	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
	public Set<OwnedBook> books;

	@OneToMany(mappedBy = "borrower", cascade = CascadeType.ALL)
	public Set<OwnedBook> currentlyBorrowedBooks;

	@Override
	public String toString() {
		return this.id + " - " + this.pseudo;
	}

	/**
	 * Checks if the list of owned books contains the given book.
	 * @param book the book to check
	 * @return true or false.
	 */
	public boolean hasBook(final Book book) {
		for (OwnedBook ownedBook : this.books) {
			if (ownedBook.baseBook.equals(book)) {
				return true;
			}
		}
		return false;
	}
}
