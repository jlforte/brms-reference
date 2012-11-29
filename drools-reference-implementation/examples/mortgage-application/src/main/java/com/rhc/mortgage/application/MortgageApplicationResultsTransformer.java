package com.rhc.mortgage.application;

import java.util.HashSet;
import java.util.Set;

import org.drools.runtime.ExecutionResults;

import com.rhc.drools.reference.ExecutionResultsTransformer;
import com.rhc.drools.reference.QueryDeclaration;
import com.rhc.drools.reference.QueryUtils;
import com.rhc.mortgage.domain.Application;
import com.rhc.mortgage.domain.Mortgage;

public class MortgageApplicationResultsTransformer implements ExecutionResultsTransformer<MortgageApplicationResponse> {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public MortgageApplicationResponse transform( ExecutionResults results, Set<QueryDeclaration> queryDeclarations  ) {
		MortgageApplicationResponse response = new MortgageApplicationResponse();

		// switch on string would be really nice...
		for ( QueryDeclaration<?> q : queryDeclarations ) {
			Set<?> set = QueryUtils.extractSetFromExecutionResults( results, q );
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

}
