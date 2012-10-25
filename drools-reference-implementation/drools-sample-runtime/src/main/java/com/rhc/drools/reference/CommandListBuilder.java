package com.rhc.drools.reference;

import java.util.List;

import org.drools.command.Command;

/**
 * Drools Concern #2: Business Logic needed to interact with the Drools Runtime (i.e. insert domain objects, set agenda
 * groups and fire all rules). The Drools Batch Command API offers an excellent out of the box solution for this
 * problem. With the Batch Command API, our business logic is entirely separate from the Drools runtime framework which
 * makes for cleaner code.
 * 
 * @param request
 * @return List of Drools Commands that encapsulate our business logic.
 */

public interface CommandListBuilder<Request> {

	@SuppressWarnings("rawtypes")
	public List<Command> buildBusinessLogicCommandList( Request request );
}
