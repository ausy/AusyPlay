package controllers;

import models.User;

/**
 * Configuration of the Secure module.
 */
public class Security extends Secure.Security {
	
	/**
	 * This method defines how to authenticate users.
	 * @param username user name
	 * @param password user password
	 * @return true if the user is authenticate, false otherwise
	 */
	static boolean authenticate(final String username, final String password) {
		//Here is a use of play feature : byEmailAndPassword
		return User.count("byEmailAndPassword", username, password) != 0;
	}

	/**
	 * Url to redirect after autentication.
	 */
	static void onAuthenticated() {
		User user = User.find("byEmail", Security.connected()).first();
		Collection.display(user.id);
	}
}
