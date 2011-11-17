package controllers;

import service.FakeService;



public class Application extends LoggedApplication {

	public static FakeService fakeService;
	
	public static void index() {
		render();
	}
}