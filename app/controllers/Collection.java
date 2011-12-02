package controllers;

import models.User;

public class Collection extends LoggedApplication {

	public static void display() {
		User user = User.findById(getConnectedUser().id);

		render(user);
	}
}
