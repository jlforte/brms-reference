package com.rhc.mortgage.application;

import java.util.Set;

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

	private Set<Application> approvedApplications;
	private Set<Application> deniedApplications;
	private Set<Mortgage> newMortgagesCreated;

	public void setApprovedApplications( Set<Application> approvedApplications ) {
		this.approvedApplications = approvedApplications;
	}

	public Set<Application> getApprovedApplications() {
		return approvedApplications;
	}

	public void setDeniedApplications( Set<Application> deniedApplications ) {
		this.deniedApplications = deniedApplications;
	}

	public Set<Application> getDeniedApplications() {
		return deniedApplications;
	}

	public Set<Mortgage> getNewMortgagesCreated() {
		return newMortgagesCreated;
	}

	public void setNewMortgagesCreated( Set<Mortgage> newMortgagesCreated ) {
		this.newMortgagesCreated = newMortgagesCreated;
	}
}
