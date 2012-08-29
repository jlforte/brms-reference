package com.rhc.brms.ref.util;

import java.util.ArrayList;
import java.util.Collection;

import org.drools.command.Command;
import org.drools.command.CommandFactory;
import org.drools.command.runtime.rule.AgendaGroupSetFocusCommand;
import org.drools.command.runtime.rule.FireAllRulesCommand;
import org.drools.command.runtime.rule.InsertObjectCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rhc.brms.ref.domain.Application;
import com.rhc.brms.ref.domain.Customer;
import com.rhc.brms.ref.engine.RulesServiceRequest;
import com.rhc.brms.ref.engine.RulesServiceResponse;

public class CommandBuilderUtil {

	private static final Logger logger = LoggerFactory.getLogger(CommandBuilderUtil.class);
	
	/**
	 * Utility class to create an AgendaGroupSetFocusCommand for the incoming agendaGroup
	 * parameter.
	 * 
	 * @param agendaGroup
	 * @return agendaGroupCommand
	 * 
	 */
	public static AgendaGroupSetFocusCommand buildAgendaGroupSetFocusCommand(String agendaGroup) {
		AgendaGroupSetFocusCommand agendaGroupCommand = new AgendaGroupSetFocusCommand();
		agendaGroupCommand.setName(agendaGroup);
		return agendaGroupCommand;
	}

	public static InsertObjectCommand buildInsertResponseCommand(RulesServiceResponse response) {
		InsertObjectCommand insertResponseCommand = new InsertObjectCommand();
		insertResponseCommand.setObject(response);
		insertResponseCommand.setOutIdentifier("response");
		return insertResponseCommand;
	}

	public static FireAllRulesCommand buildFireAllRulesCommand() {
		FireAllRulesCommand fireAll = new FireAllRulesCommand();
		return fireAll;
	}

	public static Collection<Command> buildInsertObjectCommands(RulesServiceRequest request) {

		// Create ArrayList of commands to return
		ArrayList<Command> commands = new ArrayList<Command>();

		// Retrieve objects from request
		Collection<Customer> customers  = request.getCustomers();
		Collection<Application> applications = request.getApplications();
		
		// Create insert commands to add values from request
		
		for(Customer c : customers){
			logger.info( "Adding Customer " + c );
			commands.add( CommandFactory.newInsert( c ) );
		}
		
		for(Application a: applications){
			logger.info( "Adding application " + a );
			commands.add( CommandFactory.newInsert( a ) );
		}
		
		return commands;
	}

	public static Collection<Command> buildAgendaGroupFocusCommands() {
		ArrayList<Command> commands = new ArrayList<Command>();

		commands.add(buildAgendaGroupSetFocusCommand("approve"));
		commands.add(buildAgendaGroupSetFocusCommand("eligible"));
		commands.add(buildAgendaGroupSetFocusCommand("validate-data"));
		
		return commands;
	}

}
