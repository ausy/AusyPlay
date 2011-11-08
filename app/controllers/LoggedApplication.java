package controllers;

import models.User;
import play.cache.Cache;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class LoggedApplication extends Controller {

	@Before
	static void setConnectedUser() {
		if (Security.isConnected()) {
			User user = User.find("byEmail", Security.connected()).first();
			Cache.set(session.getId() + User.KEY, user, "15mn");
			flash.put("loggedUserId", user.id.toString());
		}
	}

	static User getConnectedUser() {
		if (Security.isConnected()) {
			return (User) Cache.get(session.getId() + User.KEY);
		} else {
			return null;
		}
	}
}
