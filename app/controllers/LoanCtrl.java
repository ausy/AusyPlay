package controllers;

import java.util.List;

import javax.inject.Inject;

import models.OwnedBook;
import service.ILoanService;

/**
 * Controllers for Loan.
 */
public class LoanCtrl extends LoggedApplication {
	
	/**
	 * Here is the loanService injected by Guice.
	 */
	@Inject
	public static ILoanService loanService;
	
	public static void input() {
		List<OwnedBook> inputs = loanService.getInput(getConnectedUser());
		render(inputs);
	}

	public static void output() {
		List<OwnedBook> outputs = loanService.getOuput(getConnectedUser());
		render(outputs);
	}

	public static void all() {
		List<OwnedBook> inputs = loanService.getInput(getConnectedUser());
		List<OwnedBook> outputs = loanService.getOuput(getConnectedUser());
		render(inputs, outputs);
	}
}
