package service;

import java.util.List;

import models.Loan;
import models.User;

public class LoanService {

	public List<Loan> getInput(final User connectedUser) {
		List<Loan> inputLoans = Loan.find("select loan from Loan loan " +
				"join loan.book book " +
				"where book.owner = :user")
			.bind("user", connectedUser)
			.fetch();
		
		return inputLoans;
	}

	public List<Loan> getOuput(final User connectedUser) {
		List<Loan> outputLoans = Loan.find("select loan from Loan loan " +
		"where loan.book.owner = :user")
		.bind("user", connectedUser)
		.fetch();
		
		return outputLoans;
	}
}
