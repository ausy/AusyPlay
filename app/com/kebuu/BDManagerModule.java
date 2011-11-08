package com.kebuu;

import service.HistoryService;

import com.google.inject.AbstractModule;

import controllers.Collection;

public class BDManagerModule extends AbstractModule{

	@Override
	protected void configure() {
		this.requestStaticInjection(Collection.class);
		this.bind(HistoryService.class);
	}
}
