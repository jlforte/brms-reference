package com.rhc.drools.reference;

import java.util.List;

import org.drools.command.Command;

/**
 * <p>
 * Drools Concern #2: Business Logic needed to insert objects into the Drools Runtime and manipulate the execution of
 * the engine (e.g. setting agenda groups or firing rules). The Drools Batch Command API offers an excellent out of the
 * box solution for this problem. With the Batch Command API, our business logic is entirely separate from the Drools
 * runtime framework which makes for cleaner code.
 * </p>
 * 
 * NOTE: We've left the task of retrieving objects from the Knowledge Session to the ExecutionResultsTransformer
 * 
 * 
 * @param request
 * @return List of Drools Commands that encapsulate our business logic.
 */

public interface CommandListBuilder<Request> {

	@SuppressWarnings("rawtypes")
	public List<Command> buildBusinessLogicCommandList( Request request );
}
