package com.rhc.drools.reference;

import java.util.Set;

import org.drools.runtime.ExecutionResults;

/**
 * <p>
 * Drools Concern #4: Transforming a the <code>ExecutionResults</code> into a response the calling code can use. 
 * </p>
 * 
 */
public interface ExecutionResultsTransformer<Response> {

	/**
	 * Defines transformation to a response the application can understand
	 * 
	 * @param results
	 *            from a Drools Batch Execution
	 */
	@SuppressWarnings("rawtypes")
	public Response transform( ExecutionResults results, Set<QueryDeclaration> queryDeclarations );

}