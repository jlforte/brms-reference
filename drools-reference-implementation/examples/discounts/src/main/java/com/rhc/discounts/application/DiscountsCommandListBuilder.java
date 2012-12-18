package com.rhc.discounts.application;

import java.util.ArrayList;
import java.util.List;

import org.drools.command.Command;
import org.drools.command.CommandFactory;

import com.rhc.discounts.domain.Customer;
import com.rhc.discounts.domain.IDiscount;
import com.rhc.discounts.domain.Product;
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
		
		for ( Product p : customer.getProducts()) {
			commands.add( CommandFactory.newInsert( p ) );
		}
		
		for (IDiscount d : customer.getEligbleDiscounts()){
			commands.add( CommandFactory.newInsert( d ) );
		}
		
		commands.add( CommandFactory.newInsert( customer ) );
		
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
