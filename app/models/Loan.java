package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Loan extends Model {

	@Required
	@ManyToOne
	public OwnedBook book;

	@Required
	@ManyToOne
	public User borrower;

	public Date begin;

	public Date end;

}
