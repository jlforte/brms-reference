package com.rhc.discounts.application;

import java.util.Set;

import org.drools.runtime.ExecutionResults;

import com.rhc.discounts.domain.Customer;
import com.rhc.drools.reference.ExecutionResultsTransformer;
import com.rhc.drools.reference.QueryDeclaration;

/**
 * 
 */
public class DiscountsExecutionResultsTransformer implements ExecutionResultsTransformer<Customer> {

	/* (non-Javadoc)
	 * @see com.rhc.drools.reference.ExecutionResultsTransformer#transform(org.drools.runtime.ExecutionResults, java.util.Set)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Customer transform( ExecutionResults results,  Set<QueryDeclaration> queryDeclarations ) {
		// TODO Auto-generated method stub
		return null;
	}

}
