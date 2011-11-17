package functional;
import org.junit.Test;

import play.mvc.Http.Request;
import play.mvc.Http.Response;

public class ApplicationTest extends BaseFunctionalTest {
	
    @Test
    public void testSecurityShouldNotPass() {
    	// Try without login
    	Response response = GET("/");
        assertStatus(302, response);
        assertHeaderEquals("Location", "/login", response);
    }
    
    @Test
    public void testSecurityShouldPass() {
    	Request request = this.getLoggedRequest();
        request.url = "/collection/1"; 
        request.method = "GET"; 
        //request.params.put("someparam", "somevalue"); 
        Response response = makeRequest(request); 
        assertIsOk(response); 
    }
    
}