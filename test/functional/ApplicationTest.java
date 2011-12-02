package functional;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import models.Book;
import models.Serie;

import org.junit.Test;

import play.mvc.Http.Request;
import play.mvc.Http.Response;

public class ApplicationTest extends BaseFunctionalTest {
	
    @Test
    public void testSecurityShouldNotPass() {
    	// Try without login
    	Response response = GET("/");
        assertHeaderEquals("Location", "/login", response);
    }
    
    @Test
    public void testSecurityShouldPass() {
    	Request request = this.getLoggedRequest();
        request.url = "/collection/1"; 
        request.method = "GET"; 
        Response response = makeRequest(request); 
        assertIsOk(response); 
    }
    
    @Test
    public void testAddBook() {
    	// Test book does not exist
    	String title = "The title of the testedBook";
		Book testedBook = Book.find("byTitle", title).first();
		assertNull(testedBook);
    	
		// Create the book
		// Make the login request 
		Request request = this.getLoggedRequest(); // helper method from FunctionalTest superclass 
    	
    	Map<String, String> params = new HashMap<String, String>();
    	String serieTitle = "Cuervos";
		params.put("book.serie.id", ((Serie)Serie.find("byName", serieTitle).first()).id.toString()); 
    	params.put("book.number", "1"); 
    	params.put("book.title", title); 
    	
		POST(request, "/book/addBook", params, new HashMap<String, File>()); 
    	
    	// Test the book exists
    	testedBook = Book.find("byTitle", title).first();
		assertNotNull(testedBook);
		assertEquals(serieTitle, testedBook.serie.name);
    }
    
}