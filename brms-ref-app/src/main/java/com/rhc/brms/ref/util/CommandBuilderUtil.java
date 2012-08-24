package com.rhc.brms.ref.util;

import java.util.ArrayList;
import java.util.Collection;

import org.drools.command.Command;
import org.drools.command.CommandFactory;
import org.drools.command.runtime.rule.AgendaGroupSetFocusCommand;
import org.drools.command.runtime.rule.FireAllRulesCommand;
import org.drools.command.runtime.rule.InsertObjectCommand;

import com.rhc.brms.ref.engine.RulesServiceRequest;
import com.rhc.brms.ref.engine.RulesServiceResponse;

public class CommandBuilderUtil {

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

		// Create insert commands to add values from request

//		 commands.add( CommandFactory.newInsert( request.getRequestDate()));
//		
//		 MemberOccurrenceProfileAndIdentiferType[] memlist =
//		 request.getIndividualList().getIndividualArray(0).getMemberOccurrences().getMemberOccurrenceArray();
//		 for (MemberOccurrenceProfileAndIdentiferType mem : memlist){
//		 commands.add(
//		 CommandFactory.newInsert((MemberOccurrenceProfileAndIdentiferType)mem));
//		 System.out.println("Inserting Member");
//		 for (CoverageSummaryType cov :
//		 mem.getCoverageSummaries().getCoverageSummaryArray()){
//		 commands.add( CommandFactory.newInsert((CoverageSummaryType)cov));
//		 System.out.println("Inserting Coverage");
//		 for (BenefitOptionType benefit :
//		 cov.getBenefitOptions().getBenefitOptionArray()) {
//		 commands.add( CommandFactory.newInsert((BenefitOptionType)benefit));
//		 System.out.println("Inserting Benefit");
//		 for (CoverageOwnerSummaryType covOwner :
//		 benefit.getCoverageOwners().getCoverageOwnerArray()) {
//		 System.out.println("Inserting CoverageOwner");
//		 commands.add( CommandFactory.newInsert((CoverageOwnerSummaryType)covOwner));
//		 }
//		 }
//		 }

		return commands;
	}

	public static Collection<Command> buildAgendaGroupFocusCommands() {
		ArrayList<Command> commands = new ArrayList<Command>();

		commands.add(buildAgendaGroupSetFocusCommand("validate"));
		commands.add(buildAgendaGroupSetFocusCommand("approve"));

		return commands;
	}

}
