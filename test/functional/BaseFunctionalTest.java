package functional;
import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;

import play.mvc.Http.Request;
import play.mvc.Http.Response;
import play.test.FunctionalTest;

@Ignore
public class BaseFunctionalTest extends FunctionalTest {

	/**
	 * See getLoggedRequest(final String username, final String password)
	 */
	public Request getLoggedRequest() {
        return this.getLoggedRequest("christophe.tardella@gmail.com", "secret");
	}
	
	/**
	 * Returns a request containing information of logged user.
	 * This lets you to test secured url after being logged passing appropriate username and password.
	 * @param username username used for login
	 * @param password password used for login
	 * @return a "logged in" request
	 */
	public Request getLoggedRequest(final String username, final String password) {
		// Make the login request 
		Map<String, String> loginUserParams = new HashMap<String, String>(); 
		loginUserParams.put("username", username); 
		loginUserParams.put("password", password); 
		Response loginResponse = POST("/login", loginUserParams); 

		Request request = newRequest(); // helper method from FunctionalTest superclass 
		request.cookies = loginResponse.cookies; // this makes the request authenticated for secure module 
		assertHeaderEquals("Location", "/", loginResponse);
		
		return request;
	}
}