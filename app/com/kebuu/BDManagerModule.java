package com.kebuu;

import service.LoanService;

import com.google.inject.AbstractModule;

import controllers.LoanCtrl;

/**
 * Contains Guice configuration.
 */
public class BDManagerModule extends AbstractModule{

	@Override
	protected void configure() {
		this.requestStaticInjection(LoanCtrl.class);
		this.bind(LoanService.class);
	}
}
