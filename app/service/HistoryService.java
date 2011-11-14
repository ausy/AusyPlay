package service;

import java.util.List;

import models.OwnedBook;
import play.db.jpa.GenericModel.JPAQuery;

import com.kebuu.JPQLHelper;

public class HistoryService {

	public List<OwnedBook> getAlreadyBorrowed(final Long borrowerId, final Long ownerId) {

		JPAQuery jpaQuery = new JPQLHelper("select lo.book from Loan lo")
		.addCondition("lo.borrower.id", "borrowerId", borrowerId)
		.addCondition("lo.book.owner.id", "ownerId", ownerId).getQuery();

		List<OwnedBook> alreadyBorrowed = jpaQuery.fetch();
		return alreadyBorrowed;
	}

	public List<OwnedBook> getNotAlreadyBorrowed(final Long borrowerId, final Long ownerId) {
		List<OwnedBook> alreadyBorrowed = this.getAlreadyBorrowed(borrowerId, ownerId);

		JPQLHelper jpqlHelper = new JPQLHelper("select ob from OwnedBook ob")
		.addNotInCondition("ob", "borrowedList", alreadyBorrowed)
		.addCondition("ob.owner.id", "ownerId", ownerId);

		List<OwnedBook> notAlreadyBorrowed = jpqlHelper.getQuery().fetch();

		return notAlreadyBorrowed;
	}
}
