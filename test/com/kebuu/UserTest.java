package com.kebuu;
import java.util.List;

import models.Book;
import models.User;

import org.junit.Test;

public class UserTest extends BaseTest {

    @Test
    public void testHasBook() {
        List<User> users = User.find("byEmail", "christophe.tardella@gmail.com").fetch();
        assertEquals(1, users.size());
        
        Book book = (Book) this.getFirst(Book.find("byTitle", "Sin City").fetch());
		assertTrue(users.get(0).hasBook(book));
    }

}
