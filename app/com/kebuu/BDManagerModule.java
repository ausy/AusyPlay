package com.kebuu;

import service.HistoryService;
import service.LoanService;

import com.google.inject.AbstractModule;

import controllers.Collection;
import controllers.LoanCtrl;

public class BDManagerModule extends AbstractModule{

	@Override
	protected void configure() {
		this.requestStaticInjection(Collection.class, LoanCtrl.class);
		this.bind(HistoryService.class);
		this.bind(LoanService.class);
	}
}
