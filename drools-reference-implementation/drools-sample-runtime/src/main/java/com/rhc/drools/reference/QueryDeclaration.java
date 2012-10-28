package com.rhc.drools.reference;

import org.drools.command.Command;
import org.drools.command.CommandFactory;

public class QueryDeclaration<T> {

	private String queryName;
	private String variableDeclaration;

	public QueryDeclaration( String queryName, String variableDeclaration ) {
		this.queryName = queryName;
		this.variableDeclaration = variableDeclaration;
	}

	public QueryDeclaration() {
	}

	@SuppressWarnings("rawtypes")
	public Command buildQueryCommand() {
		// We reuse the queryName for the query outIdentifier to simplify the API
		return CommandFactory.newQuery( queryName, queryName );
	}

	public String getQueryName() {
		return queryName;
	}

	public void setQueryName( String queryName ) {
		this.queryName = queryName;
	}

	public String getVariableDeclaration() {
		return variableDeclaration;
	}

	public void setVariableDeclaration( String variableDeclaration ) {
		this.variableDeclaration = variableDeclaration;
	}

}
