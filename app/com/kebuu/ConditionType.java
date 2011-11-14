package com.kebuu;

/**
 * List of operators for JPQL request.
 */
public enum ConditionType {
	
	EQ(" = "),
	GT(" > "),
	LT(" < "),
	GET(" <= "),
	LET(" <= "),
	IN(" in "),
	NOT_IN(" not in ");
	
	protected String symbol;
	
	private ConditionType(final String op) {
		this.symbol = op;
	}

	public String getSymbol() {
		return this.symbol;
	}
}

