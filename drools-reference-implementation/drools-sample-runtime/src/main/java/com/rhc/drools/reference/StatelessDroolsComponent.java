/* 
 * Written by Red Hat Consulting.	
 * 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rhc.drools.reference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.drools.KnowledgeBase;
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

public class StatelessDroolsComponent {

	private static Logger logger = LoggerFactory.getLogger( StatelessDroolsComponent.class );

	// User Concern #1
	private KnowledgeBaseBuilder kBaseBuilder;
	// User Concern #2 -- Use commands or JBPM
	private CommandListBuilder commandListBuilder;

	// User Concern #4
	private ExecutionResultsTransformer resultsTransformer;

	// User Concern #5 Am I testing or not? Setting this value means you are testing
	private String fullyQualifiedLogFileName;

	private ConcurrentHashMap<String, List<AfterActivationFiredEvent>> firedActivations;

	/**
	 * Standard Constructor when using CommandLists
	 * 
	 * @param kBaseBuilder
	 * @param commandListBuilder
	 * @param resultsTransformer
	 */
	public StatelessDroolsComponent( KnowledgeBaseBuilder kBaseBuilder, CommandListBuilder commandListBuilder,
			ExecutionResultsTransformer resultsTransformer ) {

		this.kBaseBuilder = kBaseBuilder;
		this.commandListBuilder = commandListBuilder;
		this.resultsTransformer = resultsTransformer;
	}

	/**
	 * Constructor for testing w/ CommandLists. Setting the log name will activate the audit log and trigger the
	 * capturing of fired rule events. Note: this will slow performance
	 * 
	 * @param kBaseBuilder
	 * @param commandListBuilder
	 * @param resultsTransformer
	 * @param fullyQualifiedLogFileName
	 */
	public StatelessDroolsComponent( KnowledgeBaseBuilder kBaseBuilder, CommandListBuilder commandListBuilder,
			ExecutionResultsTransformer resultsTransformer, String fullyQualifiedLogFileName ) {

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

	@SuppressWarnings({ "rawtypes" })
	public <Response> Response execute( DroolsRequest request, Class<Response> responseClazz ) {
		// logging is optional and should only be done when testing, as it slows down the engine
		KnowledgeRuntimeLogger droolsAuditLogger = null;

		List<Command> commandList = commandListBuilder.buildBusinessLogicCommandList( request );

		KnowledgeBase kbase = kBaseBuilder.getKnowledgeBase();

		// append the queries to the end of the list so they are executed after the business logic

		Set<Command> queryCommands = QueryUtils.buildQueryCommands( responseClazz );

		commandList.addAll( queryCommands );

		StatelessKnowledgeSession kSession = kbase.newStatelessKnowledgeSession();
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

		Response response = ( resultsTransformer != null ) ? resultsTransformer.transform( results, responseClazz )
				: null;

		return response;
	}

	/**
	 * This is a slick way to capture all activations fired in the session so they can be interrogated by tests. This
	 * feature is only active when the audit log name is set because it slows the engine down.
	 * 
	 * @param kSession
	 */
	private void addFiredRulesEventListener( StatelessKnowledgeSession kSession ) {

		firedActivations = new ConcurrentHashMap<String, List<AfterActivationFiredEvent>>();
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

	public void setCommandListBuilder( CommandListBuilder commandListBuilder ) {
		this.commandListBuilder = commandListBuilder;
	}

	public void setResultsTransformer( ExecutionResultsTransformer resultsTransformer ) {
		this.resultsTransformer = resultsTransformer;
	}

	public void setFullyQualifiedLogFileName( String fullyQualifiedLogFileName ) {
		this.fullyQualifiedLogFileName = fullyQualifiedLogFileName;
	}

}
