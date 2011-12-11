package service;

import java.util.List;

import models.OwnedBook;
import models.User;

/**
 * A service related to Loan, used to centralize business logic.
 */
public class LoanService implements ILoanService {

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public List<OwnedBook> getInput(final User connectedUser) {
		List<OwnedBook> inputs = OwnedBook.find("select ownedBook from OwnedBook ownedBook " +
				"where ownedBook.borrower = :user")
			.bind("user", connectedUser)
			.fetch();
		
		return inputs;
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public List<OwnedBook> getOuput(final User connectedUser) {
		List<OwnedBook> outputs = OwnedBook.find("select ownedBook from OwnedBook ownedBook " +
				"where ownedBook.borrower is not null " +
				"and ownedBook.owner = :user")
		.bind("user", connectedUser)
		.fetch();
		
		return outputs;
	}
}
