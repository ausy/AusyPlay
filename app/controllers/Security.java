package controllers;

import models.User;

public class Security extends Secure.Security {

	static boolean authenticate(final String username, final String password) {
		return User.count("byEmailAndPassword", username, password) != 0;
	}

	static void onAuthenticated() {
		User user = User.find("byEmail", Security.connected()).first();
		Collection.display(user.id);
	}
}
