package controllers;

import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

/**
 * Uses the Secure class to manage authentication.
 * It is related to the Secure module
 */
@With(Secure.class)
public class LoggedApplication extends Controller {

	/**
	 * Stores the connected user in the cache for 15mn.
	 */
	@Before
	static void setConnectedUser() {
	}

	/**
	 * Retrieves the connected user.
	 * @return the connected user or null if it does not exist.
	 */
	static Object getConnectedUser() {
		return null;
	}
}
