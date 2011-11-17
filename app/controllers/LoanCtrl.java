package controllers;

import java.util.List;

import models.OwnedBook;
import service.LoanService;

import com.google.inject.Inject;

/**
 * Controllers for Loan.
 */
public class LoanCtrl extends LoggedApplication {
	
	/**
	 * Here is the loanService injected by Guice.
	 */
	@Inject
	public static LoanService loanService;
	
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
