package controllers;

import models.User;
import play.cache.Cache;
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
	static void setConnectedUser() throws Throwable {
		if (Security.isConnected()) {
			User user = User.find("byEmail", Security.connected()).first();
			
			if(user == null) {
				user = User.find("byPseudo", Security.connected()).first();
			}
			
			if(user == null) {
				session.clear();
				Secure.login();
			} else {
				Cache.set(session.getId() + User.KEY, user, "15mn");
			}
		}
	}

	/**
	 * Retrieves the connected user.
	 * @return the connected user or null if it does not exist.
	 */
	static User getConnectedUser() {
		if (Security.isConnected()) {
			return (User) Cache.get(session.getId() + User.KEY);
		} else {
			return null;
		}
	}
}
