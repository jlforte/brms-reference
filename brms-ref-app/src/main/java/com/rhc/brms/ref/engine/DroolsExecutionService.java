package com.rhc.brms.ref.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.drools.command.Command;
import org.drools.command.CommandFactory;
import org.drools.command.runtime.rule.AgendaGroupSetFocusCommand;
import org.drools.runtime.ExecutionResults;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

import com.rhc.brms.ref.core.RulesApplicationInterface;
import com.rhc.brms.ref.core.ExecutionResultsTransformer;
import com.rhc.brms.ref.core.StatelessDroolsService;
import com.rhc.brms.ref.util.CommandBuilderUtil;

/**
 * 
 * A simple service that will return a RulesServiceResponse object which reflects
 * 
 */
public class DroolsExecutionService implements RulesApplicationInterface<RulesServiceRequest, RulesServiceResponse> {

	private static final Logger logger = Logger.getLogger(DroolsExecutionService.class);

	private StatelessDroolsService droolsService = new StatelessDroolsService();
	private ExecutionResultsTransformer<RulesServiceResponse> transformer = new RulesServiceResultsTransformer();

	/*
	 * (non-Javadoc)
	 * 
	 * 
	 */
	public RulesServiceResponse executeAllRules(RulesServiceRequest request) {

		List<Command> commands = new ArrayList<Command>();

		// Add list of commands to insert corresponding objects in request
		commands.addAll(CommandBuilderUtil.buildInsertObjectCommands(request));

		// Set the Response Object
		RulesServiceResponse response = new RulesServiceResponse();
		commands.add(CommandBuilderUtil.buildInsertResponseCommand(response));

		// Set the Agenda groups
		commands.addAll(CommandBuilderUtil.buildAgendaGroupFocusCommands());

		// Build and add fireAllRules command for execution
		commands.add(CommandBuilderUtil.buildFireAllRulesCommand());

		// Execute commands
		ExecutionResults results = droolsService.executeCommandList(commands);
		response = transformer.transform(results);
		
		//response.setFiredRules(droolsService.getFiredActivations());

		return response;
	}

}
