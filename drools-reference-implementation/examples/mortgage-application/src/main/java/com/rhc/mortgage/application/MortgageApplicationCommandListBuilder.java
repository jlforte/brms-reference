package com.rhc.mortgage.application;

import java.util.ArrayList;
import java.util.List;

import org.drools.command.Command;
import org.drools.command.CommandFactory;

import com.rhc.drools.reference.CommandBuilderUtil;
import com.rhc.drools.reference.CommandListBuilder;

public class MortgageApplicationCommandListBuilder implements CommandListBuilder<MortgageApplicationRequest> {

	@SuppressWarnings("rawtypes")
	@Override
	public List<Command> buildBusinessLogicCommandList( MortgageApplicationRequest request ) {
		List<Command> commands = new ArrayList<Command>();

		// Insert all of the customers from the request
		commands.add( CommandFactory.newInsertElements( request.getCustomers() ) );

		// Insert all of the applications from the request
		commands.add( CommandFactory.newInsertElements( request.getApplications() ) );

		// The agenda is a stack, so agenda groups are First In, Last Out
		commands.add( CommandBuilderUtil.buildAgendaGroupSetFocusCommand( "approve" ) );
		commands.add( CommandBuilderUtil.buildAgendaGroupSetFocusCommand( "eligible" ) );
		commands.add( CommandBuilderUtil.buildAgendaGroupSetFocusCommand( "validate-data" ) );

		// Then fire all the rules
		commands.add( CommandFactory.newFireAllRules() );

		return commands;
	}

}
