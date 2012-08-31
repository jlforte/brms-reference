package com.rhc.brms.ref.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.KnowledgeBase;
import org.drools.command.Command;
import org.drools.command.CommandFactory;
import org.drools.event.rule.AfterActivationFiredEvent;
import org.drools.event.rule.DefaultAgendaEventListener;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.ExecutionResults;
import org.drools.runtime.StatefulKnowledgeSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Drools Concern #3: Lifecycle of the Knowledge Session (e.g. the runtime framework). The implementation presented
 * here is completely reusable component that manages the lifecycle of a <code>StatefulKnowledgeSession</code>
 * behind a Stateless API. You should not need to write any code interacting with a <code>StatefulKnowledgeSession</code>.
 * We've also provide the <code>DroolsRuntimeConfiguration</code> class which allows you to rename log files and the
 * sort.
 */
public class StatelessDroolsRuntime {

	private static final Logger logger = LoggerFactory.getLogger( StatelessDroolsRuntime.class );
	
	private HashMap<String, List<AfterActivationFiredEvent>> firedActivations;
	private DroolsRuntimeConfiguration configuration;

	public StatelessDroolsRuntime() {
		configuration = new DroolsRuntimeConfiguration();
	}

	public StatelessDroolsRuntime( DroolsRuntimeConfiguration configuration ) {
		this.configuration = configuration;
	}

	@SuppressWarnings("rawtypes")
	public ExecutionResults executeCommandList( KnowledgeBase kbase, List<Command> commands ) {

		StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();

		// Audit Log can be turned on and off
		KnowledgeRuntimeLogger droolsAuditLogger = null;
		if ( configuration.isRecordingAuditLog() ) {
			droolsAuditLogger = KnowledgeRuntimeLoggerFactory.newFileLogger( ksession,
					configuration.getFullyQualifiedLogFileName() );
		}

		// Capture all fired rules if desired
		if ( configuration.isRecordingFiredRuleActivations() ) {
			addFiredRulesEventListener( ksession );
		}

		// keep track of how long the rule firing is taking
		long startTime = System.currentTimeMillis();
		logger.debug( "Executing Drools commands..." );
		ExecutionResults results = ksession.execute( CommandFactory.newBatchExecution( commands ) );
		logger.debug( "Executing Drools commands took " + ( System.currentTimeMillis() - startTime ) + " ms" );

		// tear down the session and the log
		if ( configuration.isRecordingAuditLog() ) {
			droolsAuditLogger.close();
		}
		ksession.dispose();

		return results;
	}

	public Map<String, List<AfterActivationFiredEvent>> getFiredActivations() {
		return firedActivations;
	}

	/**
	 * This is a slick way to capture all activations fired in the session so they can be interrogated by tests
	 * 
	 * @param kSession
	 */
	private void addFiredRulesEventListener( StatefulKnowledgeSession kSession ) {

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

}
