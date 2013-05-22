package com.rhc.mortgage.application;

import java.util.Collection;

import com.rhc.drools.reference.DroolsQueryInfo;
import com.rhc.mortgage.domain.Application;
import com.rhc.mortgage.domain.Mortgage;

/**
 * Holds all objects that tell us the result of our Mortgage Applications.
 * 
 * 
 * We don't want this class to reference Drools or rules. This class is simply a container for the domain objects needed
 * to solve the business problem at hand, which this case is applying for a mortgage. We don't need to introduce
 * Drools/Rules naming as this an implementation detail, and this class should be reusable for alternate implentations
 * that do not use Drools.
 * 
 */
public class MortgageApplicationResponse {

	@DroolsQueryInfo(queryName = "Get All Approved Applications", binding = "$application")
	private Collection<Application> approvedApplications;
	@DroolsQueryInfo(queryName = "Get All Denied Applications", binding = "$application")
	private Collection<Application> deniedApplications;
	@DroolsQueryInfo(queryName = "Get All New Mortgages", binding = "$mortgage")
	private Collection<Mortgage> newMortgagesCreated;

	public void setApprovedApplications( Collection<Application> approvedApplications ) {
		this.approvedApplications = approvedApplications;
	}

	public Collection<Application> getApprovedApplications() {
		return approvedApplications;
	}

	public void setDeniedApplications( Collection<Application> deniedApplications ) {
		this.deniedApplications = deniedApplications;
	}

	public Collection<Application> getDeniedApplications() {
		return deniedApplications;
	}

	public Collection<Mortgage> getNewMortgagesCreated() {
		return newMortgagesCreated;
	}

	public void setNewMortgagesCreated( Collection<Mortgage> newMortgagesCreated ) {
		this.newMortgagesCreated = newMortgagesCreated;
	}
}
