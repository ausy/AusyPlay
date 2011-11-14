package controllers;

import java.util.List;

import models.User;

public class Collection extends LoggedApplication {

	public static void display(final Long userId) {
		User user = User.findById(userId);
		List<User> users = User.find("order by email").fetch();

		render(user, users);
	}
}
