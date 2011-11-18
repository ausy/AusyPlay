package controllers;


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
		return true;
	}

	/**
	 * Url to redirect after autentication.
	 */
	static void onAuthenticated() {
		Application.index();
	}
}
