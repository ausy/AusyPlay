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
 * Helper class to create JPQL requests with NAMED parameters
 * CAUTION: do not use JPQLHelper to manage nested Query !
 */
public class JPQLHelper {

	/** The query string as StringBuilder */
	protected StringBuilder queryBuilder;
	
	/** The nested JPAQuery */
	protected JPAQuery jpaQuery;
	
	/** Flag used to know if the keyword "where" has already been used in the query */
	protected boolean whereAlreadyUsed;

	/** Default constructor with empty JPQL query */
	public JPQLHelper() {
		this("");
	}
	
	/** Constructor using an existing query string. This string MUST NOT initially contains named parameters
	 *  (i.e. should not contains ":paramName" )
	 */
	public JPQLHelper(final String queryString) {
		this.queryBuilder = new StringBuilder(queryString);
		this.jpaQuery = new JPAQuery(JPA.em().createQuery(queryString));
		this.whereAlreadyUsed = Pattern.matches("(?i).*\\swhere\\s.*", queryString);
	}

	/**
	 * Adds a condition to the JPQL query and bind the value. If the value is null or if it is an empty collection,
	 * the condition is discarded.
	 * @param targetProperty the name of the property on which the condition is
	 * @param paramName the name of the parameter to bind
	 * @param paramValue the value of the parameter to bind
	 * @param type the operator of the condition
	 * @return the JPQLHelper, so you can add others condition inline
	 */
	public JPQLHelper addCondition(final String targetProperty, final String paramName, final Object paramValue, final ConditionType type) {
		// The following condition should mean : paramValue is not null AND if paramValue is a collection, is it not empty
		if (paramValue != null && (!(paramValue instanceof Collection) || !((Collection)paramValue).isEmpty())) {
			// Update queryBuilder
			this.queryBuilder.append(" " + this.andOrWhere() + targetProperty + type.getSymbol() +  ":" + paramName + " ");
			
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
	
	/**
	 * Adds the order by condition. You don't need to specify "order by" in your statement, just set "user.name" for instance.
	 * @param orderByCondition the order by condition to add
	 * @return the JPQLHelper, so you can add others condition inline
	 */
	public JPQLHelper addOrderByCondition(final String orderByCondition) {
		if (orderByCondition != null ) {
			this.queryBuilder.append(this.OrderByOrComma() + orderByCondition.replaceAll("order\\s+by", "") +  " ");
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

	/**
	 * Add an "equals" condition.
	 * @param targetProperty the name of the property on which the condition is
	 * @param paramName the name of the parameter to bind
	 * @param paramValue the value of the parameter to bind
	 * @return the JPQLHelper, so you can add others condition inline
	 */
	public JPQLHelper addCondition(final String targetProperty, final String paramName, final Object paramValue) {
		this.addCondition(targetProperty, paramName, paramValue, ConditionType.EQ);
		return this;
	}

	/**
	 * Add a "greater or equals" condition.
	 * @param targetProperty the name of the property on which the condition is
	 * @param paramName the name of the parameter to bind
	 * @param paramValue the value of the parameter to bind
	 * @return the JPQLHelper, so you can add others condition inline
	 */
	public JPQLHelper addGtCondition(final String targetProperty, final String paramName, final Object paramValue) {
		this.addCondition(targetProperty, paramName, paramValue, ConditionType.GT);
		return this;
	}

	/**
	 * Add a "lower than" condition.
	 * @param targetProperty the name of the property on which the condition is
	 * @param paramName the name of the parameter to bind
	 * @param paramValue the value of the parameter to bind
	 * @return the JPQLHelper, so you can add others condition inline
	 */
	public JPQLHelper addLtCondition(final String targetProperty, final String paramName, final Object paramValue) {
		this.addCondition(targetProperty, paramName, paramValue, ConditionType.LT);
		return this;
	}

	/**
	 * Add a "greater or equals" condition.
	 * @param targetProperty the name of the property on which the condition is
	 * @param paramName the name of the parameter to bind
	 * @param paramValue the value of the parameter to bind
	 * @return the JPQLHelper, so you can add others condition inline
	 */
	public JPQLHelper addGetCondition(final String targetProperty, final String paramName, final Object paramValue) {
		this.addCondition(targetProperty, paramName, paramValue, ConditionType.GET);
		return this;
	}

	/**
	 * Add a "lower or equals" condition.
	 * @param targetProperty the name of the property on which the condition is
	 * @param paramName the name of the parameter to bind
	 * @param paramValue the value of the parameter to bind
	 * @return the JPQLHelper, so you can add others condition inline
	 */
	public JPQLHelper addLetCondition(final String targetProperty, final String paramName, final Object paramValue) {
		this.addCondition(targetProperty, paramName, paramValue, ConditionType.LET);
		return this;
	}

	/**
	 * Add an "in" condition.
	 * @param targetProperty the name of the property on which the condition is
	 * @param paramName the name of the parameter to bind
	 * @param paramValue the value of the parameter to bind
	 * @return the JPQLHelper, so you can add others condition inline
	 */
	public JPQLHelper addInCondition(final String targetProperty, final String paramName, final Object paramValue) {
		this.addCondition(targetProperty, paramName, paramValue, ConditionType.IN);
		return this;
	}

	/**
	 * Add a "not in" condition.
	 * @param targetProperty the name of the property on which the condition is
	 * @param paramName the name of the parameter to bind
	 * @param paramValue the value of the parameter to bind
	 * @return the JPQLHelper, so you can add others condition inline
	 */
	public JPQLHelper addNotInCondition(final String targetProperty, final String paramName, final Object paramValue) {
		this.addCondition(targetProperty, paramName, paramValue, ConditionType.NOT_IN);
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

	/**
	 * Gets the built JPAQuery ready to fetch.
	 * @return the nested JPAQuery
	 */
	public JPAQuery getQuery() {
		return this.jpaQuery;
	}
	
	/**
	 * Returns the JPQL query as String
	 * @return a JPQL query as String
	 */
	public String getQueryString() {
		return this.queryBuilder.toString();
	}
}
