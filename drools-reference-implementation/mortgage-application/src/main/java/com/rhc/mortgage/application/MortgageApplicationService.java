package com.rhc.mortgage.application;

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

import com.rhc.drools.reference.CommandBuilderUtil;
import com.rhc.drools.reference.DroolsRuntimeConfiguration;
import com.rhc.drools.reference.ExecutionResultsTransformer;
import com.rhc.drools.reference.StatelessDroolsComponent;
import com.rhc.drools.reference.StatelessDroolsRuntime;
import com.rhc.mortgage.domain.Application;
import com.rhc.mortgage.domain.Customer;
import com.rhc.mortgage.drools.MortgageApplicationRequest;
import com.rhc.mortgage.drools.MortgageApplicationResponse;
import com.rhc.mortgage.drools.MortgageApplicationResultsTransformer;

/**
 * 
 * A simple service that determines if a Customer is eligible for a Mortgage based off their application.
 * 
 */
public class MortgageApplicationService extends
		StatelessDroolsComponent<MortgageApplicationRequest, MortgageApplicationResponse> {

	private static KnowledgeBase kbase;

	@Override
	@SuppressWarnings("rawtypes")
	protected List<Command> buildBusinessLogicCommandList( MortgageApplicationRequest request ) {
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

	@Override
	protected StatelessDroolsRuntime getRuntime() {
		
		DroolsRuntimeConfiguration conf = new DroolsRuntimeConfiguration();
		conf.setFullyQualifiedLogFileName( "MortgageApplicationAuditLog" );

		return new StatelessDroolsRuntime( conf );
	}

	@Override
	protected ExecutionResultsTransformer<MortgageApplicationResponse> getTransformer() {
		return new MortgageApplicationResultsTransformer();
	}

}
