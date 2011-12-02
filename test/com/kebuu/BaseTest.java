package com.kebuu;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;

import play.test.Fixtures;
import play.test.UnitTest;

@Ignore
public class BaseTest extends UnitTest {

	@Before
	public void setUp() {
	    Fixtures.deleteAllModels();
	    Fixtures.loadModels("initial-data/all.yml");
	}

	/**
	 * Returns the first element of a list (useful when you execute a request returning
	 * one record maximum)
	 * @param list the input list
	 * @return the first element of the list if it exists
	 * @throws RuntimeException if the list contains more than one element
	 */
	protected <T> T getFirst(final List<T> list) {
		if(list != null && !list.isEmpty()) {
			return list.get(0);
		} else if(list.size() > 1) {
			throw new RuntimeException("This list shouldn't contain more than on element");
		} else {
			return null;
		}
	}
}
