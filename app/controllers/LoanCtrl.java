package controllers;

import java.util.List;

import models.Loan;
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
		List<Loan> inputLoans = loanService.getInput(getConnectedUser());
		render(inputLoans);
	}

	public static void output() {
		List<Loan> outputLoans = loanService.getOuput(getConnectedUser());
		render(outputLoans);
	}

	public static void all() {
		List<Loan> inputLoans = loanService.getInput(getConnectedUser());
		List<Loan> outputLoans = loanService.getOuput(getConnectedUser());
		render(inputLoans, outputLoans);
	}
}
