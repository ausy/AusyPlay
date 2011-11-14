package service;

import java.util.List;

import models.Loan;
import models.User;

/**
 * A service related to Loan, used to centralize business logic.
 */
public class LoanService {

	/**
	 * Gets the book the connected user have borrowed.
	 * @param connectedUser the connected used
	 * @return a list of Loan
	 */
	public List<Loan> getInput(final User connectedUser) {
		List<Loan> inputLoans = Loan.find("select loan from Loan loan " +
				"join loan.book book " +
				"where book.owner = :user")
			.bind("user", connectedUser)
			.fetch();
		
		return inputLoans;
	}

	/**
	 * Gets the book the connected user have lent.
	 * @param connectedUser the connected used
	 * @return a list of Loan
	 */
	public List<Loan> getOuput(final User connectedUser) {
		List<Loan> outputLoans = Loan.find("select loan from Loan loan " +
		"where loan.book.owner = :user")
		.bind("user", connectedUser)
		.fetch();
		
		return outputLoans;
	}
}
