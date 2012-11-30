package com.rhc.discounts.application;

import java.util.ArrayList;
import java.util.List;

import org.drools.command.Command;
import org.drools.command.CommandFactory;

import com.rhc.discounts.domain.Customer;
import com.rhc.discounts.domain.Purchase;
import com.rhc.drools.reference.CommandListBuilder;

/**
 * 
 */
public class DiscountsCommandListBuilder implements CommandListBuilder<Customer> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.rhc.drools.reference.CommandListBuilder#buildBusinessLogicCommandList(java.lang.Object)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Command> buildBusinessLogicCommandList( Customer customer ) {

		List<Command> commands = new ArrayList<Command>();

		commands.add( CommandFactory.newInsert( customer) );
		
		for ( Purchase p : customer.getPurchases() ) {
			commands.add( CommandFactory.newInsert( p ) );
			commands.add( CommandFactory.newInsert( p.getProduct() ) );
		}
		
		// The agenda is a stack, so agenda groups are First In, Last Out
		/*
		commands.add( CommandBuilderUtil.buildAgendaGroupSetFocusCommand( "approve" ) );
		commands.add( CommandBuilderUtil.buildAgendaGroupSetFocusCommand( "eligible" ) );
		commands.add( CommandBuilderUtil.buildAgendaGroupSetFocusCommand( "validate-data" ) );
		*/

		// Then fire all the rules
		commands.add( CommandFactory.newFireAllRules() );
		return commands;
	}

}
