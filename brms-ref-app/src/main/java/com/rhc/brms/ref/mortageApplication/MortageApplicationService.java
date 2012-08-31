package com.rhc.brms.ref.mortageApplication;

import java.util.ArrayList;
import java.util.List;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.command.Command;
import org.drools.io.ResourceFactory;
import org.slf4j.LoggerFactory;

import com.rhc.brms.ref.drools.DroolsApplication;
import com.rhc.brms.ref.drools.DroolsRuntimeConfiguration;
import com.rhc.brms.ref.drools.StatelessDroolsRuntime;
import com.rhc.brms.ref.util.CommandBuilderUtil;

/**
 * 
 * A simple service that determines if a Customer is eligible for a Mortgage based off their application.
 * 
 */
public class MortageApplicationService extends DroolsApplication<MortageApplicationRequest, MortageApplicationResponse> {

	private static KnowledgeBase kbase;

	public MortageApplicationService() {
		// overwrite the logger. Is this the right way to that?
		logger = LoggerFactory.getLogger( MortageApplicationService.class );

		// set up the configuration for the runtime
		DroolsRuntimeConfiguration conf = new DroolsRuntimeConfiguration();
		conf.setFullyQualifiedLogFileName( "MortageApplicationAuditLog" );

		this.droolsRuntime = new StatelessDroolsRuntime( conf );
		this.resultsTransformer = new MortageApplicationResultsTransformer();

		// build the knowledgeBase upon object creation. We don't want the hit of compilation the first time we run the
		// run rules
		kbase = buildKnowledgeBase();
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected List<Command> buildBusinessLogicCommandList( MortageApplicationRequest request ) {
		List<Command> commands = new ArrayList<Command>();

		// Add list of commands to insert corresponding objects in request
		commands.addAll( CommandBuilderUtil.buildInsertObjectCommands( request ) );

		// Set the Response Object
		MortageApplicationResponse response = new MortageApplicationResponse();
		commands.add( CommandBuilderUtil.buildInsertResponseCommand( response ) );

		// Set the Agenda groups
		commands.addAll( CommandBuilderUtil.buildAgendaGroupFocusCommands() );

		// Build and add fireAllRules command for execution
		commands.add( CommandBuilderUtil.buildFireAllRulesCommand() );

		return commands;
	}

	@Override
	protected KnowledgeBase getKnowledgeBase() {
		if ( kbase == null ) {
			kbase = buildKnowledgeBase();
		}

		return kbase;
	}

	private KnowledgeBase buildKnowledgeBase() {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

		long startTime = System.currentTimeMillis();
		logger.debug( "Building Knowledge Base..." );

		// add any new rules here
		kbuilder.add( ResourceFactory.newClassPathResource( "ApplicationValidation.drl", getClass() ), ResourceType.DRL );
		kbuilder.add( ResourceFactory.newClassPathResource( "CustomerValidation.drl", getClass() ), ResourceType.DRL );
		kbuilder.add( ResourceFactory.newClassPathResource( "MortgageRules.drl", getClass() ), ResourceType.DRL );

		if ( kbuilder.hasErrors() ) {
			logger.error( kbuilder.getErrors().toString() );
		}

		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages( kbuilder.getKnowledgePackages() );

		logger.debug( "Building Knowledge Base took " + ( System.currentTimeMillis() - startTime ) + " ms" );

		return kbase;
	}

}
