package com.rhc.mortgage.application;

import java.util.ArrayList;
import java.util.List;

import org.drools.command.Command;
import org.drools.command.CommandFactory;

import com.rhc.drools.reference.CommandBuilderUtil;
import com.rhc.drools.reference.CommandListBuilder;
import com.rhc.drools.reference.Request;

public class MortgageApplicationCommandListBuilder implements CommandListBuilder{

	@SuppressWarnings("rawtypes")
	@Override
	public List<Command> buildBusinessLogicCommandList( Request request ) {
		List<Command> commands = new ArrayList<Command>();

		// Insert all of the Objects from the request
		commands.add( CommandFactory.newInsertElements( request.getAllObjects() ) );

	

		// The agenda is a stack, so agenda groups are First In, Last Out
		commands.add( CommandBuilderUtil.buildAgendaGroupSetFocusCommand( "approve" ) );
		commands.add( CommandBuilderUtil.buildAgendaGroupSetFocusCommand( "eligible" ) );
		commands.add( CommandBuilderUtil.buildAgendaGroupSetFocusCommand( "validate-data" ) );

		// Then fire all the rules
		commands.add( CommandFactory.newFireAllRules() );

		return commands;
	}

}
