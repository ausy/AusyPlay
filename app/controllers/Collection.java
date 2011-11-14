package controllers;

import java.util.List;

import models.User;

public class Collection extends LoggedApplication {

	public static void display() {
		User user = User.findById(getConnectedUser().id);
		List<User> users = User.find("order by email").fetch();

		render(user, users);
	}
}
