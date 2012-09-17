package com.rhc.drools.reference;

import java.util.List;

import org.drools.KnowledgeBase;
import org.drools.command.Command;
import org.drools.runtime.ExecutionResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An abstract class that outlines a stateless Drools component within a Java Application. This design pattern
 * identifies 4 concerns related to a Drools Application and wraps them all in a web service style request/response API.
 * 
 */
public abstract class StatelessDroolsComponent<Request, Response> {

	protected static Logger logger = LoggerFactory.getLogger( StatelessDroolsComponent.class );
	// Drools Concern #3
	protected StatelessDroolsRuntime droolsRuntime;
	// Drools Concern #4
	protected ExecutionResultsTransformer<Response> resultsTransformer;

	@SuppressWarnings("rawtypes")
	public Response executeAllRules( Request request ) {

		List<Command> commandList = buildBusinessLogicCommandList( request );
		// append the queries to the end of the list so they are executed after the business logic
		commandList.addAll( resultsTransformer.getQueryCommands() );

		long startTime = System.currentTimeMillis();
		logger.debug( "Executing Drools Application..." );
		ExecutionResults results = droolsRuntime.executeCommandList( getKnowledgeBase(), commandList );
		logger.debug( "Executing Drools Application took " + ( System.currentTimeMillis() - startTime ) + " ms" );

		Response response = resultsTransformer.transform( results );

		return response;
	}

	/**
	 * Drools Concern #1: Accumulation of Knowledge Resources (i.e. rules, queries, workflows, templates, decision
	 * tables), compilation of those resources into Knowledge Packages, and creation of a Knowledge Base from the
	 * Knowledge Packages. This tends to be the element that varies most application to application, so we've left
	 * problem to be solved by a concrete class. All this solution needs is a KnowledgeBase to returned - it doesn't
	 * care how you create it. Here are just a few ways that you could tackle Knowledge Asset Management:
	 * 
	 * 1) One time compilation from the class path using the KnowledgeBuilder. This is the solution we present here. <br>
	 * 2) Compilation of remote resources using a KnowledgeAgent. This is useful when rules live in Guvnor or a remote
	 * file store. <br>
	 * 3) Application wide caching strategy that builds and stores KnowledgeBases for numerous business processes. <br>
	 * 4) Pre-compiled KnowledgePackages or KnowledgeBase. This can be done with the ant task or Guvnor
	 */
	protected abstract KnowledgeBase getKnowledgeBase();

	/**
	 * Drools Concern #2: Business Logic needed to interact with the Drools Runtime (i.e. insert domain objects, set
	 * agenda groups and fire all rules). The Drools Batch Command API offers an excellent out of the box solution for
	 * this problem. With the Batch Command API, our business logic is entirely separate from the Drools runtime
	 * framework which makes for cleaner code.
	 * 
	 * @param request
	 * @return List of Drools Commands that encapsulate our business logic.
	 */
	@SuppressWarnings("rawtypes")
	protected abstract List<Command> buildBusinessLogicCommandList( Request request );

}
