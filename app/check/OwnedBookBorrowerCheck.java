package check;

import models.OwnedBook;
import play.data.validation.Check;

/**
 * Checks that the borrower is not the owner (no sense).
 * @author Kebuu
 *
 */
public class OwnedBookBorrowerCheck extends Check {

	@Override
	public boolean isSatisfied(final Object ownedBook, final Object borrower) {
		return !((OwnedBook) ownedBook).owner.equals(borrower);
	}

}
