package com.kebuu;
import play.modules.guice.GuiceSupport;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Guicy extends GuiceSupport {
	
	@Override
	protected Injector configure() {
		return Guice.createInjector(
				new BDManagerModule()
		);
	}
}