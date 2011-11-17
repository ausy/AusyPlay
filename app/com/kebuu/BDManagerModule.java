package com.kebuu;

import service.FakeService;

import com.google.inject.AbstractModule;

import controllers.Application;

/**
 * Contains Guice configuration.
 */
public class BDManagerModule extends AbstractModule{

	@Override
	protected void configure() {
		this.requestStaticInjection(Application.class);
		this.bind(FakeService.class);
	}
}
