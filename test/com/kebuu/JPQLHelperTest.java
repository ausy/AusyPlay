package com.kebuu;

import java.util.Arrays;

import org.junit.Test;

public class JPQLHelperTest extends BaseTest {

	@Test
    public void testAddConditions() {
        String startRequest = "select book from Serie serie join serie.books book";
		JPQLHelper helper = new JPQLHelper(startRequest);
        
        helper.addCondition("serie.name", "name", "Naja");
        assertEquals(startRequest + "  where serie.name = :name ", helper.getQueryString());
        assertEquals(5, helper.getQuery().fetch().size());

        helper.addCondition("book.number", "tome", 1);
        assertEquals(startRequest + "  where serie.name = :name   and book.number = :tome ", helper.getQueryString());
        assertEquals(1, helper.getQuery().fetch().size());
       
        helper.addCondition("book.isbn", "isbn", null);
        assertEquals(startRequest + "  where serie.name = :name   and book.number = :tome ", helper.getQueryString());
        assertEquals(1, helper.getQuery().fetch().size());
    }
	
	@Test
	public void testAddInNotInConditions() {
		String startRequest = "select book from Book book";
		JPQLHelper helper = new JPQLHelper(startRequest);
		
		helper.addInCondition("book.number", "tomein", Arrays.asList(new Long[] {3l, 4l, 5l}));
		assertEquals(startRequest + "  where book.number in :tomein ", helper.getQueryString());
		assertEquals(18, helper.getQuery().fetch().size());
		
		helper.addNotInCondition("book.number", "tomenotin", Arrays.asList(new Long[] {3l}));
		assertEquals(10, helper.getQuery().fetch().size());
	}
	
}
