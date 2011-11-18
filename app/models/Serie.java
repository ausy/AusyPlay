package models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.data.validation.Required;

@Entity
public class Serie extends BaseModel {
	
	private static final long serialVersionUID = 1L;

	protected static Serie UNBINDED_SERIE;

	@Required
	public String name;

	@OneToMany(mappedBy = "serie", cascade = CascadeType.ALL)
	public Set<Book> books = new HashSet<Book>();

	@Override
	public String toString() {
		return this.name;
	}

	/**
	 * Returns a serie used to wrap all books for which no serie has been
	 * declared.
	 * @return the constant serie
	 */
	public synchronized static Serie getUnbindedSerie() {
		if (UNBINDED_SERIE == null) {
			UNBINDED_SERIE = new Serie();
			UNBINDED_SERIE.name = "UNBINDED_SERIE";
		}
		return UNBINDED_SERIE;
	}
}
