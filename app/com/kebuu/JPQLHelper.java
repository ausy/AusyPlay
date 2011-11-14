package com.kebuu;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.persistence.Parameter;
import javax.persistence.Query;

import play.db.jpa.GenericModel.JPAQuery;
import play.db.jpa.JPA;

/**
 * CAUTION: do not use JPQLHelper to manage nested Query !
 */
public class JPQLHelper {

	protected StringBuilder queryBuilder;
	protected JPAQuery jpaQuery;
	protected boolean whereAlreadyUsed;

	public JPQLHelper(final String queryString) {
		this.queryBuilder = new StringBuilder(queryString);
		this.jpaQuery = new JPAQuery(JPA.em().createQuery(queryString));
		this.whereAlreadyUsed = Pattern.matches("(?i).*\\swhere\\s.*", queryString);
	}

	public JPQLHelper addCondition(final String targetColumn, final String paramName, final Object paramValue, final ConditionType type) {
		// The following condition should mean : paramValue is not null AND if paramValue is a collection, is it not empty
		if (paramValue != null && (!(paramValue instanceof Collection) || !((Collection)paramValue).isEmpty())) {
			// Update queryBuilder
			this.queryBuilder.append(" " + this.andOrWhere() + targetColumn + type.getSymbol() +  ":" + paramName + " ");
			
			// Get already bound parameters.
			Map<String, Object> parametersValue = this.getParametersValue(this.jpaQuery.query);
			
			// Create a new jpaQuery based on the new queryString
			this.jpaQuery = new JPAQuery(JPA.em().createQuery(this.queryBuilder.toString()));
			
			// Bind new parameter and rebind the others
			this.jpaQuery.bind(paramName, paramValue);
			
			for (Entry<String, Object> parameterEntry : parametersValue.entrySet()) {
				this.jpaQuery.bind(parameterEntry.getKey(), parameterEntry.getValue());
			}
			
			// Flag the use of where
			this.whereAlreadyUsed = true;
		}
		return this;
	}
	
	public JPQLHelper addOrderByCondition(final String orderByCondition) {
		if (orderByCondition != null ) {
			this.queryBuilder.append(this.OrderByOrComma() + orderByCondition +  " ");
		}
		return this;
	}

	private Map<String, Object> getParametersValue(final Query query) {
		Map<String, Object> parametersValue = new HashMap<String, Object>();
		
		for (Parameter<?> parameter : query.getParameters()) {
			parametersValue.put(parameter.getName(), query.getParameterValue(parameter.getName()));
		}
		return parametersValue;
	}

	public JPQLHelper addCondition(final String targetColumn, final String paramName, final Object paramValue) {
		this.addCondition(targetColumn, paramName, paramValue, ConditionType.EQ);
		return this;
	}

	public JPQLHelper addGtCondition(final String targetColumn, final String paramName, final Object paramValue) {
		this.addCondition(targetColumn, paramName, paramValue, ConditionType.GT);
		return this;
	}

	public JPQLHelper addLtCondition(final String targetColumn, final String paramName, final Object paramValue) {
		this.addCondition(targetColumn, paramName, paramValue, ConditionType.LT);
		return this;
	}

	public JPQLHelper addGetCondition(final String targetColumn, final String paramName, final Object paramValue) {
		this.addCondition(targetColumn, paramName, paramValue, ConditionType.GET);
		return this;
	}

	public JPQLHelper addLetCondition(final String targetColumn, final String paramName, final Object paramValue) {
		this.addCondition(targetColumn, paramName, paramValue, ConditionType.LET);
		return this;
	}

	public JPQLHelper addInCondition(final String targetColumn, final String paramName, final Object paramValue) {
		this.addCondition(targetColumn, paramName, paramValue, ConditionType.IN);
		return this;
	}

	public JPQLHelper addNotInCondition(final String targetColumn, final String paramName, final Object paramValue) {
		this.addCondition(targetColumn, paramName, paramValue, ConditionType.NOT_IN);
		return this;
	}

	private String andOrWhere() {
		if (this.whereAlreadyUsed) {
			return " and ";
		} else {
			return " where ";
		}
	}
	
	private String OrderByOrComma() {
		if (this.queryBuilder.toString().matches("(?i).*order\\s+by.*")) {
			return " , ";
		} else {
			return " order by ";
		}
	}

	public JPAQuery getQuery() {
		return this.jpaQuery;
	}
	
	public String getQueryString() {
		return this.queryBuilder.toString();
	}
}
