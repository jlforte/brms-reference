package com.rhc.brms.ref.mortageApplication;

import java.util.ArrayList;
import java.util.List;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.command.Command;
import org.drools.command.CommandFactory;
import org.drools.io.ResourceFactory;
import org.slf4j.LoggerFactory;

import com.rhc.brms.ref.domain.Application;
import com.rhc.brms.ref.domain.Customer;
import com.rhc.brms.ref.drools.CommandBuilderUtil;
import com.rhc.brms.ref.drools.DroolsRuntimeConfiguration;
import com.rhc.brms.ref.drools.StatelessDroolsComponent;
import com.rhc.brms.ref.drools.StatelessDroolsRuntime;

/**
 * 
 * A simple service that determines if a Customer is eligible for a Mortgage based off their application.
 * 
 */
public class MortageApplicationService extends
		StatelessDroolsComponent<MortageApplicationRequest, MortageApplicationResponse> {

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

		// Insert all of the customers from the request
		for ( Customer customer : request.getCustomers() ) {
			logger.info( "Adding Customer " + customer );
			commands.add( CommandFactory.newInsert( customer ) );
		}
		// Insert all of the applications from the request
		for ( Application application : request.getApplications() ) {
			logger.info( "Adding application " + application );
			commands.add( CommandFactory.newInsert( application ) );
		}
		
		// The agenda is a stack, so agenda groups are First In, Last Out
		commands.add( CommandBuilderUtil.buildAgendaGroupSetFocusCommand( "approve" ) );
		commands.add( CommandBuilderUtil.buildAgendaGroupSetFocusCommand( "eligible" ) );
		commands.add( CommandBuilderUtil.buildAgendaGroupSetFocusCommand( "validate-data" ) );

		// Then fire all the rules
		commands.add( CommandFactory.newFireAllRules() );

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
		kbuilder.add( ResourceFactory.newClassPathResource( "Queries.drl", getClass() ), ResourceType.DRL );

		if ( kbuilder.hasErrors() ) {
			logger.error( kbuilder.getErrors().toString() );
		}

		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages( kbuilder.getKnowledgePackages() );

		logger.debug( "Building Knowledge Base took " + ( System.currentTimeMillis() - startTime ) + " ms" );

		return kbase;
	}

}
