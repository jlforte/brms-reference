package com.rhc.mortgage.application;

import java.util.HashSet;
import java.util.Set;

import org.drools.runtime.ExecutionResults;

import com.rhc.drools.reference.ExecutionResultsTransformer;
import com.rhc.drools.reference.QueryDeclaration;
import com.rhc.mortgage.domain.Application;
import com.rhc.mortgage.domain.Mortgage;

public class MortgageApplicationResultsTransformer extends ExecutionResultsTransformer<MortgageApplicationResponse> {

	@SuppressWarnings("rawtypes")
	public MortgageApplicationResultsTransformer( Set<QueryDeclaration> queryDeclarations ) {
		super( queryDeclarations );
	}

	public MortgageApplicationResultsTransformer() {
		super();
	}

	@SuppressWarnings("unchecked")
	public MortgageApplicationResponse transform( ExecutionResults results ) {
		MortgageApplicationResponse response = new MortgageApplicationResponse();

		// switch on string would be really nice...
		for ( QueryDeclaration<?> q : queryDeclarations ) {
			Set<?> set = extractSetFromExecutionResults( results, q );
			if ( q.getQueryName().equals( "Get All Approved Applications" ) ) {
				response.setApprovedApplications( (Set<Application>) set );
			} else if ( q.getQueryName().equals( "Get All Denied Applications" ) ) {
				response.setDeniedApplications( (Set<Application>) set );
			} else if ( q.getQueryName().equals( "Get All New Mortgages" ) ) {
				response.setNewMortgagesCreated( (Set<Mortgage>) set );
			}
		}

		return response;
	}

	/**
	 * This is a bit of a hack here just to make the not DI test classes easier.
	 */
	@SuppressWarnings("rawtypes")
	public static Set<QueryDeclaration> buildQueryDeclarations() {
		Set<QueryDeclaration> queryDeclarations = new HashSet<QueryDeclaration>();
		queryDeclarations.add( new QueryDeclaration<Application>( "Get All Approved Applications", "$application" ) );
		queryDeclarations.add( new QueryDeclaration<Application>( "Get All Denied Applications", "$application" ) );
		queryDeclarations.add( new QueryDeclaration<Application>( "Get All New Mortgages", "$mortgage" ) );
		return queryDeclarations;
	}

}
