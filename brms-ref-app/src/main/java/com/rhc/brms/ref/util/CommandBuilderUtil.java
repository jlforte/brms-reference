package com.rhc.brms.ref.util;

import org.drools.command.runtime.rule.AgendaGroupSetFocusCommand;

public class CommandBuilderUtil {

	/**
	 * Utility class to create an AgendaGroupSetFocusCommand for the incoming agendaGroup parameter.
	 * 
	 * @param agendaGroup
	 * @return agendaGroupCommand
	 * 
	 */
	public static AgendaGroupSetFocusCommand buildAgendaGroupSetFocusCommand( String agendaGroup ) {
		AgendaGroupSetFocusCommand agendaGroupCommand = new AgendaGroupSetFocusCommand();
		agendaGroupCommand.setName( agendaGroup );
		return agendaGroupCommand;
	}

}
