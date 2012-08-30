package com.rhc.brms.ref.core;

import java.util.List;

import org.drools.command.Command;
import org.drools.runtime.ExecutionResults;

/**
 * This interface defines the ability to transform a Drools Batch
 * ExecutionResults into a generically typed response. This class will handle
 * everything related to returning information from the knowledge session
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
	 * @return the queries to be run in order to obtain the proper objects in
	 *         the ExecutionResults
	 */
	@SuppressWarnings("rawtypes")
	public List<Command> getQueryCommands();
}