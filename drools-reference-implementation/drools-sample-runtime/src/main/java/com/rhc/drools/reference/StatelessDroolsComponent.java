package com.rhc.drools.reference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.command.Command;
import org.drools.command.CommandFactory;
import org.drools.event.rule.AfterActivationFiredEvent;
import org.drools.event.rule.DefaultAgendaEventListener;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.ExecutionResults;
import org.drools.runtime.StatelessKnowledgeSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A generic, reusable component that identifies all concerns related to a Stateless Drools interaction in a Java
 * Application. We use a simple request/response API.
 * 
 */

public class StatelessDroolsComponent<Request, Response> {

	private static Logger logger = LoggerFactory.getLogger( StatelessDroolsComponent.class );

	// User Concern #1
	private KnowledgeBaseBuilder kBaseBuilder;
	// User Concern #2
	private CommandListBuilder<Request> commandListBuilder;
	// User Concern #3
	private ExecutionResultsTransformer<Response> resultsTransformer;

	// User Concern #4 Am I testing or not?
	private String fullyQualifiedLogFileName;

	private HashMap<String, List<AfterActivationFiredEvent>> firedActivations;

	/**
	 * Standard Constructor.
	 * 
	 * @param kBaseBuilder
	 * @param commandListBuilder
	 * @param resultsTransformer
	 */
	public StatelessDroolsComponent( KnowledgeBaseBuilder kBaseBuilder, CommandListBuilder<Request> commandListBuilder,
			ExecutionResultsTransformer<Response> resultsTransformer ) {

		this.kBaseBuilder = kBaseBuilder;
		this.commandListBuilder = commandListBuilder;
		this.resultsTransformer = resultsTransformer;
	}

	/**
	 * Constructor for testing. Setting the log name will activate the audit log and trigger the capturing of fired rule
	 * events. Note: this will slow performance
	 * 
	 * @param kBaseBuilder
	 * @param commandListBuilder
	 * @param resultsTransformer
	 * @param fullyQualifiedLogFileName
	 */
	public StatelessDroolsComponent( KnowledgeBaseBuilder kBaseBuilder, CommandListBuilder<Request> commandListBuilder,
			ExecutionResultsTransformer<Response> resultsTransformer, String fullyQualifiedLogFileName ) {

		this.kBaseBuilder = kBaseBuilder;
		this.commandListBuilder = commandListBuilder;
		this.resultsTransformer = resultsTransformer;
		this.fullyQualifiedLogFileName = fullyQualifiedLogFileName;
	}

	/**
	 * Nullary Constructor for use with Dependency Injection
	 */
	public StatelessDroolsComponent() {
	}

	@SuppressWarnings("rawtypes")
	public Response execute( Request request ) {

		// logging is optional and should be done when testing, as it slows down the engine
		KnowledgeRuntimeLogger droolsAuditLogger = null;

		List<Command> commandList = commandListBuilder.buildBusinessLogicCommandList( request );

		// append the queries to the end of the list so they are executed after the business logic
		commandList.addAll( resultsTransformer.getQueryCommands() );

		StatelessKnowledgeSession kSession = kBaseBuilder.getKnowledgeBase().newStatelessKnowledgeSession();

		// setting the audit log file name will cause the component to log and capture fired rule events
		if ( fullyQualifiedLogFileName != null ) {
			droolsAuditLogger = KnowledgeRuntimeLoggerFactory.newFileLogger( kSession, fullyQualifiedLogFileName );
			addFiredRulesEventListener( kSession );
		}

		long startTime = System.currentTimeMillis();
		logger.debug( "Executing Drools Application..." );

		ExecutionResults results = kSession.execute( CommandFactory.newBatchExecution( commandList ) );

		logger.debug( "Executing Drools Application took " + ( System.currentTimeMillis() - startTime ) + " ms" );

		if ( fullyQualifiedLogFileName != null ) {
			droolsAuditLogger.close();
		}

		Response response = resultsTransformer.transform( results );

		return response;
	}

	/**
	 * This is a slick way to capture all activations fired in the session so they can be interrogated by tests. This
	 * feature is only active when the audit log name is set because it slows the engine down.
	 * 
	 * @param kSession
	 */
	private void addFiredRulesEventListener( StatelessKnowledgeSession kSession ) {

		firedActivations = new HashMap<String, List<AfterActivationFiredEvent>>();
		kSession.addEventListener( new DefaultAgendaEventListener() {
			@Override
			public void afterActivationFired( AfterActivationFiredEvent event ) {
				String name = event.getActivation().getRule().getName();
				List<AfterActivationFiredEvent> tempList = null;
				if ( firedActivations.containsKey( name ) ) {
					tempList = firedActivations.get( name );
				} else {
					tempList = new ArrayList<AfterActivationFiredEvent>();
				}
				tempList.add( event );
				firedActivations.put( name, tempList );
			}
		} );

	}

	/**
	 * Convenience method for testing to return activations from previous the previous execution.
	 * 
	 * @return Map<Rule name, List<Activations of that rule fired>>
	 */
	public Map<String, List<AfterActivationFiredEvent>> getPreviouslyFiredActivations() {
		return firedActivations;
	}

	public void setKnowledgeBaseBuilder( KnowledgeBaseBuilder kBaseBuilder ) {
		this.kBaseBuilder = kBaseBuilder;
	}

	public void setCommandListBuilder( CommandListBuilder<Request> commandListBuilder ) {
		this.commandListBuilder = commandListBuilder;
	}

	public void setResultsTransformer( ExecutionResultsTransformer<Response> resultsTransformer ) {
		this.resultsTransformer = resultsTransformer;
	}

}
