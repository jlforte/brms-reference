package com.rhc.brms.ref.core;

import java.util.List;

import org.drools.command.Command;
import org.drools.runtime.ExecutionResults;

/**
 * Drools Concern #4: Extracting objects from the Drools Knowledge Session and transforming them into a response the
 * calling code can use. Since we use the Drools Batch Command API in this design pattern, all logic to extract
 * objects from the Knowledge Session will be contained in Drools queries and the objects themselves will be
 * returned in a <code>ExecutionResults</code>, which we transform into a response object.
 * 
 */
public interface ExecutionResultsTransformer<Response> {

	/**
	 * 
	 * @param results
	 *            from a Drools Batch Execution
	 */
	public Response transform( ExecutionResults results );

	/**
	 * 
	 * @return the queries to be run in order to obtain the proper objects in the ExecutionResults
	 */
	@SuppressWarnings("rawtypes")
	public List<Command> getQueryCommands();
}