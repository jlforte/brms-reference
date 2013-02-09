package com.rhc.drools.reference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.drools.command.Command;
import org.drools.command.CommandFactory;

/**
 * 
 * Simple implementation of the CommandListBuilder Interface to use jBPM to control rule flow
 * 
 */
public class RuleFlowCommandListBuilder implements CommandListBuilder<Collection<Object>> {

	private String ruleFlowName;

	public RuleFlowCommandListBuilder() {
	}

	public RuleFlowCommandListBuilder( String ruleFlowName ) {
		this.ruleFlowName = ruleFlowName;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List<Command> buildBusinessLogicCommandList( Collection<Object> request ) {
		List<Command> commands = new ArrayList<Command>();
		commands.add( CommandFactory.newInsertElements( request ) );
		commands.add( CommandFactory.newStartProcess( ruleFlowName ) );
		commands.add( CommandFactory.newFireAllRules() );
		return commands;
	}

	public void setRuleFlowName( String ruleFlowName ) {
		this.ruleFlowName = ruleFlowName;
	}

}
