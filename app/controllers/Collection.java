package controllers;

import java.util.Calendar;
import java.util.List;

import models.Book;
import models.Loan;
import models.OwnedBook;
import models.Serie;
import models.User;
import service.HistoryService;

import com.google.inject.Inject;

public class Collection extends LoggedApplication {

	@Inject
	public static HistoryService historyService;
	
	public static void display(final Long userId) {
		User user = User.findById(userId);
		List<User> users = User.find("order by email").fetch();

		render(user, users);
	}

	public static void lentBooks() {

		List<OwnedBook> lentBooks = OwnedBook
				.find("select ob from OwnedBook ob join ob.owner ow where ow.id = (:userId) and ob.borrower is not null")
				.bind("userId", getConnectedUser().id).fetch();

		render(lentBooks);
	}

	public static void borrowedBook(final Long ownedBookId, final Long userId) {
		User user = User.findById(userId);
		OwnedBook ownedBook = OwnedBook.findById(ownedBookId);

		// validation.required(userId);
		// validation.equals(ownedBook.owner, user);
		// validation.isTrue(ownedBook.isAvailable);

		if (validation.hasErrors()) {

		} else {
			ownedBook.borrower = user;
			ownedBook.isAvailable = false;
			ownedBook.save();

			Loan loan = new Loan();
			loan.book = ownedBook;
			loan.borrower = user;
			loan.save();
		}

		display(getConnectedUser().id);
	}

	public static void returnedBook(final Long ownedBookId) {
		OwnedBook ownedBook = OwnedBook.findById(ownedBookId);

		ownedBook.borrower = null;
		ownedBook.isAvailable = true;
		ownedBook.save();

		List<Loan> loans = Loan.find("select l from Loan l where l.book = (:ownedBook) and l.end is null").bind("ownedBook", ownedBook)
				.fetch();

		for (Loan loan : loans) {
			loan.end = Calendar.getInstance().getTime();
			loan.save();
		}

		display(getConnectedUser().id);
	}

	public static void manage() {
		User user = getConnectedUser();

		List<Serie> allBooksBySerie = Book.getAllBySerie();
		render(allBooksBySerie, user);
	}

	public static void selectUnselectBook(final Long bookId, final boolean selected) {
		User user = getConnectedUser();
		if (selected) {
			Book book = Book.findById(bookId);
			OwnedBook ownedBook = new OwnedBook(book, user);
			ownedBook.save();
		} else {
			List<OwnedBook> unselectedBooks = OwnedBook.find("select ob from OwnedBook ob join ob.baseBook bb where bb.id = (:id)")
					.bind("id", bookId).fetch();

			for (OwnedBook ownedBook : unselectedBooks) {
				ownedBook.delete();
			}
		}
	}

	public static void history(final Long borrowerId, final Long ownerId) {
		Long usedDorrowerId = borrowerId == null ? getConnectedUser().id : borrowerId;
		
		List<OwnedBook> alreadyBorrowed = historyService.getAlreadyBorrowed(usedDorrowerId, ownerId);
		List<OwnedBook> notAlreadyBorrowed = historyService.getNotAlreadyBorrowed(usedDorrowerId, ownerId);
		
		render(alreadyBorrowed,notAlreadyBorrowed);
	}
	
	public static void helloNorbert() {
		renderText("helloNorbert");
	}
	
	
}
