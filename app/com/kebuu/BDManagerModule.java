package com.kebuu;

import service.ILoanService;
import service.LoanService;

import com.google.inject.AbstractModule;

/**
 * Contains Guice configuration.
 */
public class BDManagerModule extends AbstractModule{

	@Override
	protected void configure() {
		this.bind(ILoanService.class).to(LoanService.class);
	}
}
