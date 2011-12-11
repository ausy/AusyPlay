package service;

import java.util.List;

import models.OwnedBook;
import models.User;

public interface ILoanService {

	/**
	 * Gets the book the connected user have borrowed.
	 * @param connectedUser the connected used
	 * @return a list of Loan
	 */
	public abstract List<OwnedBook> getInput(final User connectedUser);

	/**
	 * Gets the book the connected user have lent.
	 * @param connectedUser the connected used
	 * @return a list of Loan
	 */
	public abstract List<OwnedBook> getOuput(final User connectedUser);

}