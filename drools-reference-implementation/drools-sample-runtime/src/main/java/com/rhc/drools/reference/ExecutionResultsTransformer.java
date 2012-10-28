package com.rhc.drools.reference;

import java.util.HashSet;
import java.util.Set;

import org.drools.command.Command;
import org.drools.runtime.ExecutionResults;
import org.drools.runtime.rule.QueryResults;
import org.drools.runtime.rule.QueryResultsRow;

/**
 * <p>
 * Drools Concern #3: Extracting objects from the Drools Knowledge Session and transforming them into a response the
 * calling code can use. Since we use the Drools Batch Command API in this design pattern, all logic to extract objects
 * from the Knowledge Session will be contained in Drools queries and the objects themselves will be returned in a
 * <code>ExecutionResults</code>, which we transform into a response object.
 * </p>
 * 
 */
public abstract class ExecutionResultsTransformer<Response> {

	@SuppressWarnings("rawtypes")
	protected Set<QueryDeclaration> queryDeclarations;
	@SuppressWarnings("rawtypes")
	private Set<Command> queryCommands;

	@SuppressWarnings("rawtypes")
	public ExecutionResultsTransformer( Set<QueryDeclaration> queryDeclarations ) {
		this.queryDeclarations = queryDeclarations;
	}

	public ExecutionResultsTransformer() {
	}

	/**
	 * Defines transformation to a response the application can understand
	 * 
	 * @param results
	 *            from a Drools Batch Execution
	 */
	public abstract Response transform( ExecutionResults results );

	@SuppressWarnings("rawtypes")
	public Set<Command> getQueryCommands() {
		if ( queryCommands == null ) {
			queryCommands = new HashSet<Command>();
			if ( queryDeclarations != null ) {
				for ( QueryDeclaration<?> qi : queryDeclarations ) {
					queryCommands.add( qi.buildQueryCommand() );
				}
			}
		}
		return queryCommands;
	}

	@SuppressWarnings("unchecked")
	protected <T> Set<T> extractSetFromExecutionResults( ExecutionResults exectionResults,
			QueryDeclaration<T> queryDeclaration ) {
		Set<T> set = new HashSet<T>();
		if ( exectionResults != null ) {
			QueryResults queryResult = (QueryResults) exectionResults.getValue( queryDeclaration.getQueryName() );
			if ( queryResult != null ) {
				for ( QueryResultsRow row : queryResult ) {
					set.add( (T) row.get( queryDeclaration.getVariableDeclaration() ) );
				}
			}
		}
		return set;
	}

	@SuppressWarnings("rawtypes")
	public void setQueryDeclarations( Set<QueryDeclaration> queryDeclarations ) {
		this.queryDeclarations = queryDeclarations;
	}

}