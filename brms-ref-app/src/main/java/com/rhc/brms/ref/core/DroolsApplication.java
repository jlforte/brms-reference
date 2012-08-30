package com.rhc.brms.ref.core;

import java.util.List;

import org.drools.KnowledgeBase;
import org.drools.command.Command;
import org.drools.runtime.ExecutionResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Base class that defines the Reference  
 * 
 */
public abstract class DroolsApplication<Request, Response> {

	protected static Logger logger = LoggerFactory.getLogger( DroolsApplication.class );

	protected StatelessDroolsRuntime droolsRuntime; // responsible drools runtime execution
	protected ExecutionResultsTransformer<Response> resultsTransformer;// responsible for transforming results from runtime into something the applicaiton needs
	
	@SuppressWarnings("rawtypes")
	public Response executeAllRules( Request request ) {

		List<Command> commandList = buildCommandList( request );
		long startTime = System.currentTimeMillis();

		logger.debug( "Executing Drools Application..." );
		ExecutionResults results = droolsRuntime.executeCommandList( getKnowledgeBase(), commandList );
		logger.debug( "Executing Drools Application took " + ( System.currentTimeMillis() - startTime ) + " ms" );

		Response response = resultsTransformer.transform( results );

		return response;
	}

	/*
	 * This is business logic
	 */
	@SuppressWarnings("rawtypes")
	protected abstract List<Command> buildCommandList( Request request );

	/*
	 * Knowledge Resources
	 */
	protected abstract KnowledgeBase getKnowledgeBase();
}
