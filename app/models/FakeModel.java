package models;

import javax.persistence.Entity;

import play.data.validation.Required;

@Entity
public class FakeModel extends BaseModel {

	private static final long serialVersionUID = 1L;

	@Required
	public String name;
}
